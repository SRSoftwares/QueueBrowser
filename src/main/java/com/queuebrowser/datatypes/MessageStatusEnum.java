/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.datatypes;

/**
 * ENUM for Various Message Status
 * @author - Sumit Roy
 * @Created On - Feb 27, 2013,5:43:44 PM
 * @Project - QueueBrowser
 */

public enum MessageStatusEnum {

    EMPTY("Empty"), ACTIVE("Active"), LOADED("Queues are Loaded"),DEFAULT("");
    private String status;

    
    private MessageStatusEnum(String status) {
        this.status = status;
    }

    
 /**
  * This method over rides toString() method and and returns the various queue status as set by the enum
  * @return the name of various queue status
  */
    @Override
    public String toString() {
        return status;
    }
    
}
