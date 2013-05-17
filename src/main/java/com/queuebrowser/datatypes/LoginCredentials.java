/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queuebrowser.datatypes;

import java.io.Serializable;

/**
 * 
 * @author - Sumit Roy
 * @Created On - Mar 13, 2013,12:53:41 PM
 * @Project - Queue Browser
 * 
*/
public class LoginCredentials implements Serializable{
String userName;
String password;
String host;
String port;
String sid;

    public LoginCredentials() {
    userName="";
    password="";
    host="";
    port="";
    sid="";
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        String msg=String.format("Username : %s \nPassword : %s \nHost : %s \n"
                + "Port : %s \nSID : %s",getUserName(),getPassword(),getHost(),
                getPort(),getSid());
        return super.toString();
    }
    

}
