/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is Responsible for Database Connection
 *
 * @author Sumit Roy
 */
public class DatabaseConnectivity {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectivity.class);
    private static Connection dbConnection = null;
    private static String errorMessage;
    private static Map<String, String> userCredentials;

    /**
     * This method creates a database connection with given credentials and
     * returns the connection Note : if connection to database already exist,
     * then the existing connection is returned instead of new connection
     *
     * @param userCredential a key value pair which holds the following keys
     * username : user name of the database user where the connection will be
     * established password : password of the user sid : system id host : IP
     * address or system name of the host port : port where the database is
     * running
     * @return database connection which is just connected
     */
    public static Connection createDatabaseConnection(Map<String, String> userCredential) {
        if (userCredential != null) { // For internal creation to DB if conn is lost with existing user credentials
            userCredentials = userCredential;
        }
        errorMessage = "";
        if (dbConnection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@" + userCredentials.get("host") + ":" + userCredentials.get("port") + ":" + userCredentials.get("sid");
                dbConnection = DriverManager.getConnection(url, userCredentials.get("username"), userCredentials.get("password"));
                logger.info("Database Connection Created Succesfully , URL: {}", url);
                dbConnection.setAutoCommit(false);
            } catch (Exception ex) {
                errorMessage = ex.getLocalizedMessage();
                
                logger.error("Exception Occured {}", ex);
            }
        }
        return dbConnection;
    }

    /**
     *
     * @return a error message (if exist while the connection is
     * established),else an empty text is returned
     */
    public static String getErrorMessage() {
        return errorMessage;
    }

    /**
     * This method closes an active database connection
     */
    public static void closeDatabaseConnection() {
        try {
            dbConnection.close();
            dbConnection = null;
        } catch (SQLException ex) {
            logger.error("SQL Exception Occured {}", ex);
        }
    }

    /**
     * This method returns an active database connection ,which is already
     * created at the time of login
     *
     * @return a database connection if it exist else null is return
     */
    public static Connection getAnActiveDatabaseConnection() {
        try {
            if(dbConnection.isClosed()||dbConnection==null)
                createDatabaseConnection(null);
        } catch (SQLException ex) {
            logger.error("SQLException Occured ! {}",ex);
        }
        return dbConnection;
    }

    /**
     * this method returns name of all queues available within the given schema
     * @param schemaName name of schema where requested queues resides
     * @return list of all available queues  
     */
    public static List<String> getQueueNames(String schemaName) {
        schemaName = schemaName.toUpperCase();
        List<String> tableList = new ArrayList<String>();
        if (dbConnection != null) {
            try {
                ResultSet rs = null;
                DatabaseMetaData ds = dbConnection.getMetaData();
                String type[] = {"QUEUE"};
                rs = ds.getTables(null, schemaName, null, type);
                while (rs.next()) {
                    String queueName = rs.getString(3);
                    if (!(queueName.startsWith("AQ$") && queueName.endsWith("_E"))) {
                        // Oracle Internal Queues are Prefixed and Suffixed with AQ$ and _E
                        tableList.add(queueName);
                    }
                }
            } catch (SQLException ex) {
                logger.error("SQLException Occured : {}",ex);
            }
        }


        return tableList;

    }

    
}
