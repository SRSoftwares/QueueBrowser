/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.datatypes;

/**
 * Bean class for holding a Queue Message Data like Queue Name,Depth,Last Access
 * Time,status
 *
 * @author Sumit Roy
 */
public class QueueData {

    private String queueName;
    private long queueLastAccessTime;
    private int queueDepth;
    private MessageStatusEnum queueStatus;
    private static long allQueuesLastAccessTime = -1;
    private Object tableData[][];
    private int msgsRcvCount;
    private int msgsDeleteCount;

    /**
     * Default Constructor to initialize different property of Queue Data
     */
    public QueueData() {
        queueName = "";
        queueLastAccessTime = -1;
        queueDepth = -1;
        queueStatus = MessageStatusEnum.DEFAULT;
        msgsRcvCount=0;
        msgsDeleteCount=0;
        tableData=null;
    }

    /**
     * Getter method for Last Queue Access Time
     *
     * @return timestamp when all queues are accessed last time
     */
    public static long getAllQueuesLastAccessTime() {
        return allQueuesLastAccessTime;
    }

    /**
     * Setter method Last Queue Access Time
     *
     * @param allQueuesLastAccessTime Timestamp when all queues are accessed
     * last time
     */
    public static void setAllQueuesLastAccessTime(long allQueuesLastAccessTime) {
        QueueData.allQueuesLastAccessTime = allQueuesLastAccessTime;
    }

    /**
     * Getter Method for Queue Status
     *
     * @return status of queue
     */
    public MessageStatusEnum getQueueStatus() {

        return queueStatus;
    }

    /**
     * Setter Method for Queue Status
     *
     * @param queueStatus Status of queue
     */
    public void setQueueStatus(MessageStatusEnum queueStatus) {
        this.queueStatus = queueStatus;
    }

    /**
     * Getter method for Depth of Queue
     *
     * @return depth of a queue
     */
    public int getQueueDepth() {
        return queueDepth;
    }

    /**
     * Setter method for Depth of Queue
     *
     * @param queueDepth Sets depth of a queue
     */
    public void setQueueDepth(int queueDepth) {
        this.queueDepth = queueDepth;
    }

    

    /**
     * Getter method for Name of Queue
     *
     * @return Name of Queue
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Setter method for Name of Queue
     * @param Name of Queue
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    
    /**
     * Getter method for time stamp when the queue has been accessed last
     * @return timestamp when the queue has been accessed
     */
    public long getQueueLastAccessTime() {
        return queueLastAccessTime;
    }
   
    /**
     * Setter method for time stamp when the queue has been accessed last
     * @param queueLastAccessTime  timestamp when the queue has been accessed
     */ 
    public void setQueueLastAccessTime(long queueLastAccessTime) {
        this.queueLastAccessTime = queueLastAccessTime;
    }

    /**
     * Get the queue message table data in form of a 2D array
     * @return queue message table data
     */
    public Object[][] getTableData() {
        return tableData;
    }

    /**
     * set table data of queue message
     * @param tableData queue message table data
     */
    public void setTableData(Object[][] tableData) {
        this.tableData = tableData;
    }

    /**
     * Getter method that tells no of messages deleted for a queue in a active session
     * @return no of messages deleted in a session
     */
    public int getMsgsDeleteCount() {
        return msgsDeleteCount;
    }
    
    /**
     * Setter method that sets no of messages deleted from a queue in a active session
     * @param msgsDeleteCount no. of messages deleted from a queue
     */
    public void setMsgsDeleteCount(int msgsDeleteCount) {
        this.msgsDeleteCount = msgsDeleteCount;
    }

    /**
     * Getter method that tells no of messages received for a queue in a active session
     * @return no of messages received in a session
     */
    
    public int getMsgsRcvCount() {
        return msgsRcvCount;
    }
    
    /**
     * Setter method that sets no of messages received from a queue in a active session
     * @param msgsRcvCount no. of messages received from a queue
     */
    public void setMsgsRcvCount(int msgsRcvCount) {
        this.msgsRcvCount = msgsRcvCount;
    }
    
}