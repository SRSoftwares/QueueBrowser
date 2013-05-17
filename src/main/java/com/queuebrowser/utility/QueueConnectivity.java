/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import oracle.jms.AQjmsDestination;
import oracle.jms.AQjmsQueueConnectionFactory;
import oracle.jms.AQjmsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maintain a Queue Connection including Queue Session and starting of a queue
 *
 * @author Sumit Roy
 */
public class QueueConnectivity {

    private static final Logger logger = LoggerFactory.getLogger(QueueConnectivity.class);
    private Connection dbConn;
    private static QueueConnection queueConnection;
    private static QueueSession queueSession;

    /**
     * Default constructor for establishing a Queue Connection
     */
    public QueueConnectivity() {
        dbConn = DatabaseConnectivity.getAnActiveDatabaseConnection();
        queueSession = null;
        queueConnection = null;
    }

    private void createQueueConnection() {
        try {
            queueConnection = AQjmsQueueConnectionFactory.createQueueConnection(dbConn);
            queueConnection.start();
        } catch (JMSException ex) {
            logger.error("JMSException Occured {}", ex);

        }
    }

    private void createQueueSession() {
        if (queueConnection == null) {
            createQueueConnection();
        }
        try {
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            logger.error("JMS Exception Occured ", ex);
        }
    }

    /**
     * This method connects with a queue and return the queue object
     *
     * @param schemaName schema name where the queue resides
     * @param queueName name of the queue
     * @return queue we connected using given schema name and queue name
     */
    private Queue getQueue(String schemaName, String queueName) {
        if (queueSession == null) {
            createQueueSession();
        }
        Queue queue = null;
        try {
            queue = ((AQjmsSession) queueSession).getQueue(schemaName, queueName);
            ((AQjmsDestination) queue).start(queueSession, true, true);
        } catch (JMSException ex) {
            logger.error("JMSException Occured ", ex);
        }
        return queue;
    }

    /**
     * This method browse a queue , if the queue is not connected , it is
     * initially connected then messages are browse from the queue
     *
     * @param schemaName schema name where the queue resides
     * @param queueName name of the queue
     * @return Vector of browsed text message
     */
    public List<Message> browseQueue(String schemaName, String queueName) {
        List<Message> allMessages = new ArrayList<Message>();
        Queue queue = getQueue(schemaName, queueName);
        try {
            QueueBrowser queueBrowser = queueSession.createBrowser(queue);
            Enumeration e = queueBrowser.getEnumeration();
            int noMsgs = 0;
            while (e.hasMoreElements()) {
                Message message = (Message) e.nextElement();
                allMessages.add(noMsgs, message);
                noMsgs++;
            }
        } catch (JMSException ex) {
            logger.error("JMSException Occured {}", ex);
        }
        return allMessages;
    }

    /**
     * Return the depth of the queue, i.e. the no. of messages available in
     * queue
     *
     * @param schemaName name of the schema where queue resides
     * @param queueName name of the queue whose depth is being calculated
     * @return depth (no of messages) in the queue
     */
    public int getQueueDepth(String schemaName, String queueName) {
        return browseQueue(schemaName, queueName).size();
    }

    /**
     * Sends message to a queue
     *
     * @param schemaName Name of schema where the required queue (on which the
     * message will be send) resides
     * @param queueName name of the queue where the message is send
     * @param messageContent content of the message being sent
     * @param msgType type of the message text/xml
     * @throws JMSException a jms exception which occurs due to various reasons
     */
    public void sendTextMessage(String schemaName, String queueName, String messageContent, String msgType) throws JMSException {

        if (queueSession == null) {
            createQueueSession();
        }
        Queue queue = getQueue(schemaName, queueName);
        QueueSender queueSender = queueSession.createSender(queue);
        queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        long time = System.currentTimeMillis();
        TextMessage message = queueSession.createTextMessage(messageContent);
        message.setJMSType(msgType);
        message.setJMSTimestamp(time);
        queueSender.send(message);
        queueSender.close();
        queueSession.close();
        queueSession = null;
    }

    /**
     * Create a queue in the physical database
     *
     * @param queueName name of the queue as given by user
     * @param queueTableName table name of the queue that holds queue data
     * @throws SQLException SQL Exception which may occur while firing queue and
     * table creation query
     */
    public void createQueue(String queueName, String queueTableName) throws SQLException {


        if (dbConn.isClosed() || dbConn == null) {
            dbConn = DatabaseConnectivity.getAnActiveDatabaseConnection();
        }
        String queueTableSQL = String.format("BEGIN "
                + "SYS.DBMS_AQADM.create_queue_table( "
                + "queue_table => '%s', "
                + "queue_payload_type => 'SYS.AQ$_JMS_MESSAGE', "
                + "sort_list=>'PRIORITY,ENQ_TIME', "
                + "compatible => '10.0.0', "
                + "primary_instance =>0, "
                + "secondary_instance =>0, "
                + "storage_clause => \'tablespace MANUAL_MESSAGE pctfree 10 initrans 1 maxtrans 255 storage(initial 64K minextents 1 maxextents unlimited )\');"
                + "END;", queueTableName);
        String queueSQL = String.format("BEGIN "
                + "SYS.DBMS_AQADM.create_queue( "
                + "queue_name => '%s',"
                + "queue_table => '%s',"
                + "queue_type => sys.dbms_aqadm.normal_queue,"
                + "max_retries => 3,"
                + "retry_delay => 0,"
                + "retention_time => 0);"
                + "END;", queueName, queueTableName);
        logger.info("New Queue Creation Query,Create Queue Table : {}", queueTableSQL);
        logger.info("New Queue Creation Query,Create Queue : {}", queueSQL);
        Statement statement;
        statement = dbConn.createStatement();
        statement.executeUpdate(queueTableSQL);
        statement.executeUpdate(queueSQL);
        statement.close();
    }

    /**
     * Delete a queue from the physical database
     *
     * @param queueName name of the queue to delete
     * @param schemaName name of the schema where the queue resides
     * @throws SQLException SQL Exception may be thrown if problem appears in
     * queue deletion
     */
    public void deleteQueue(String queueName, String schemaName) throws SQLException {


        String queueTableName = String.format("Q_%s_AQ", queueName);
        String queueTableSchemaName = String.format("%s.%s", schemaName, queueTableName);
        String deleteQuery = String.format("BEGIN "
                + "SYS.DBMS_AQADM.DROP_QUEUE_TABLE("
                + "QUEUE_TABLE =>'%s',FORCE => TRUE);"
                + "END;", queueTableSchemaName);
        logger.info("Delete Queue Creation Query : {}", deleteQuery);
        if (dbConn.isClosed() || dbConn == null) {
            dbConn = DatabaseConnectivity.getAnActiveDatabaseConnection();
        }
        Statement statement;
        statement = dbConn.createStatement();
        statement.executeUpdate(deleteQuery);
        statement.close();

    }

    /**
     * Delete a List of Queues from physical database
     *
     * @param queueNames list of queue names which are to be deleted from the
     * database
     * @param schemaName name of the schema where these queue resides
     * @throws SQLException SQL Exception may be thrown if problem appears in
     * queue deletion
     */
    public void deleteQueues(List<String> queueNames, String schemaName) throws SQLException {
        for (String queueName : queueNames) {
            deleteQueue(queueName, schemaName); // checks whether all queries are fired properly
        }
    }

    /**
     * Empty a queue messages completely
     *
     * @param schemaName Name of the schema where the queue resides
     * @param queueName Name of the queue from where messages are deleted
     * @param queueTableName Name of the Queue Table associated with the Queue
     
     */
    public void emptyQueueMessages(String schemaName, String queueName, String queueTableName) throws SQLException {
        if (dbConn.isClosed() || dbConn == null) {
            dbConn = DatabaseConnectivity.getAnActiveDatabaseConnection();
        }
        String emptyQueueQuery= String.format("DECLARE "
                + "po dbms_aqadm.%s$_purge_options_t; "
                + "BEGIN "
                + "po.block := TRUE; "                
                + "DBMS_AQADM.PURGE_QUEUE_TABLE(queue_table => '%s', "
                + "purge_condition => 'qtview.queue = ''%s''', "
                + "purge_options => po); "
                + "COMMIT; "
                + "END; ",schemaName,queueTableName,queueName);
        logger.info("All Message Deletion Queury : {} ",emptyQueueQuery);
        Statement statement;
        statement = dbConn.createStatement();
        statement.executeUpdate(emptyQueueQuery);
        statement.close();       
    }

    /**
     * Receive a no of message(s) from the Queue , Note : After a message is
     * being received it will be no more in physical database, so if it is
     * required messages availability in the queue use Browse Queue method
     * instead of Receive Messages
     *
     * @param schemaName name of the schema where required queue is resides
     * @param queueName name of the queue from where the messages is going to be
     * received
     * @param noOfMsgsToRcv Number of messages to be received from the queue,
     * not exceeding the queue depth
     * @return list of received text messages from the queue
     * @throws JMSException JMS Exception may be thrown if messages are not
     * properly received
     */
    public List<Message> receiveMessages(String schemaName, String queueName, int noOfMsgsToRcv) throws JMSException {
        List<Message> messagesList = new ArrayList<Message>();
        QueueReceiver queueReceiver = queueSession.createReceiver(getQueue(schemaName, queueName));
        queueConnection.start();
        messagesList = new ArrayList<Message>();
        Message message;
        while (noOfMsgsToRcv > 0) {
            message = (Message) queueReceiver.receive();
            noOfMsgsToRcv--;

            messagesList.add(message);
        }

        return messagesList;
    }
}