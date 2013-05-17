/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import com.queuebrowser.datatypes.LoginCredentials;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author - Sumit Roy
 * @Created On - Mar 13, 2013,1:19:24 PM
 * @Project - Queue Browser
 * 
*/
public class LoadSaveLoginCredentials {

    LoadSaveLoginCredentials loadSaveUserCredentials;
    String filePath;
    String fileName;
    String fileExtension;
    StringBuilder completeFileName;

    public LoadSaveLoginCredentials() {
        filePath = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "config"
                + System.getProperty("file.separator");
        fileName = "Queue Browser Login Credentials";
        fileExtension = ".obj";
        completeFileName = new StringBuilder();
        completeFileName.append(filePath).append(fileName).append(fileExtension);
    }

    public LoadSaveLoginCredentials(String filePath) {
        this.filePath = filePath;
        completeFileName.append(filePath).append(fileName).append(fileExtension);
    }

    public LoadSaveLoginCredentials(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        completeFileName.append(filePath).append(fileName).append(fileExtension);
    }

    public LoadSaveLoginCredentials(String filePath, String fileName, String fileExtension) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        completeFileName.append(filePath).append(fileName).append(fileExtension);
    }

    public LoginCredentials loadUserCredentials() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(completeFileName.toString());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        LoginCredentials loginCredentials = (LoginCredentials) objectInputStream.readObject();
        return loginCredentials;
    }

    public void saveUserCredentials(LoginCredentials loginCredentials) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(completeFileName.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(loginCredentials);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
