/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.views;

import com.queuebrowser.datatypes.QueueData;
import com.queuebrowser.utility.DatabaseConnectivity;
import com.queuebrowser.utility.MiscUtility;
import com.queuebrowser.utility.QueueConnectivity;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This view is Responsible for Receiving No. of Messages from a JMS Queue also
 * deleting No. of messages from a JMS Queue While Object creation of this class
 * user need to mention the operation of the dialog as it can be used for both
 * receiving messages from queue as well as deleting messages from queue
 *
 * @author Sumit Roy
 */
public class ReceiveDeleteMessageDialog extends javax.swing.JDialog {

    private String selectedQueue;
    private String schema;
    private static final Logger logger = LoggerFactory.getLogger(ReceiveDeleteMessageDialog.class);
    private QueueConnectivity queueConnectivity;
    private String[] columnNames;
    private int queueDepth;
    private int noOfMsgsReceived;
    private String operationType;
    private String dialogIconPathFileName;
    private QueueData queueData;
    private boolean forMsgRcvOperation;

    /**
     * Creates new form ReceiveDeleteMessageDialog
     */
    private ReceiveDeleteMessageDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * Overloaded constructor for showing Receive Delete Message from Queue
     * dialog
     *
     * @param selectedQueue Name of the queue from where the messages are
     * deleted/received
     * @param schema Name of the schema where the queue resides
     * @param forMsgRcvOperation flag , if set to true then this dialog will be
     * @param queueData QueueData object for the on which this receive/deletion
     * operation is being performed used for receiving messages from queue , if
     * not set then this dialog will be use for deleting messages for queue
     * @param parent parent view on which this dialog will be open
     */
    public ReceiveDeleteMessageDialog(JFrame parent, String selectedQueue,
            String schema, boolean forMsgRcvOperation, QueueData queueData) {
        this(parent, true);
        this.selectedQueue = selectedQueue;
        queueNameLabel.setText(selectedQueue);
        this.schema = schema;
        this.queueData = queueData;
        queueConnectivity = new QueueConnectivity();
        this.forMsgRcvOperation = forMsgRcvOperation;
        columnNames = new String[]{
            "Message ID", //0
            "Message Content", //1
            "Message Type", //2 
            "Timestamp"};      //3
        noOfMsgsReceived = 0;
        initializeComponents();
        updateCentralPanel();
        addMouseWheelListenerToSpinner();
        handleTableClickEvent();
        setDialogIcon();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                queueConnectivity = null;
            }
        });
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spinnerLabel = new javax.swing.JLabel();
        messagesCountSpinner = new javax.swing.JSpinner();
        operateMessagesButton = new javax.swing.JButton();
        centralPanel = new javax.swing.JPanel();
        tableScrollPane = new javax.swing.JScrollPane();
        receiveMessagesTable = new javax.swing.JTable();
        queueNameLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Receive Messages");
        setModal(true);
        setName("receiveMessageDialog"); // NOI18N
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Recieve Messages"));

        jLabel1.setText("Queue :");

        spinnerLabel.setText("Max No. Of Message(s) Can be ");

        operateMessagesButton.setText("Receive");
        operateMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operateMessagesButtonActionPerformed(evt);
            }
        });

        centralPanel.setBackground(new java.awt.Color(255, 240, 240));
        centralPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Queue : No. of Msgs Received :"));
        centralPanel.setLayout(new java.awt.BorderLayout());

        receiveMessagesTable.setAutoCreateRowSorter(true);
        receiveMessagesTable.setBackground(new java.awt.Color(255, 240, 240));
        receiveMessagesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        receiveMessagesTable.setToolTipText("Click on a Messages to Get its Details");
        receiveMessagesTable.setFillsViewportHeight(true);
        tableScrollPane.setViewportView(receiveMessagesTable);

        centralPanel.add(tableScrollPane, java.awt.BorderLayout.CENTER);

        queueNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        queueNameLabel.setText("Queue Name");
        queueNameLabel.setToolTipText("Queue Name");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queueNameLabel)
                .addGap(22, 22, 22)
                .addComponent(spinnerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(operateMessagesButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(centralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spinnerLabel)
                    .addComponent(messagesCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operateMessagesButton)
                    .addComponent(queueNameLabel))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(centralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void operateMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operateMessagesButtonActionPerformed
        performMsgRcvDelOperation();
    }//GEN-LAST:event_operateMessagesButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centralPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSpinner messagesCountSpinner;
    private javax.swing.JButton operateMessagesButton;
    private javax.swing.JLabel queueNameLabel;
    private javax.swing.JTable receiveMessagesTable;
    private javax.swing.JLabel spinnerLabel;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables

    private void performMsgRcvDelOperation() {
        List<String> queueNodes = DatabaseConnectivity.getQueueNames(schema);
        if (queueNodes.contains(selectedQueue)) {  // Queue is still in Physical DB
            try {
                updateReceiveMessagesTable();
            } catch (JMSException ex) {
                String msgToDisplay = String.format("Unable to %s Messages from Queue : %s\n\tWould you like to check Error Details ?", operationType, selectedQueue);
                int option = JOptionPane.showConfirmDialog(this, msgToDisplay, String.format("Message %s Failure", operationType), JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    ExceptionDetailsDialog.showExceptionsDetailsDialog(this, "JMS Exception", String.format("%s Messsage(s) for Queue : ", operationType, selectedQueue), ex);
                    logger.error("JMSException Occured ", ex);
                }
            }
        } else { // Queue No More exist in Physical DB
            String msgToDisplay = String.format("Sorry this Queue : %s is no more in physical Database !", selectedQueue);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue No more Exist", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateReceiveMessagesTable() throws JMSException {
        Object data[][] = getQueueTableRecords();
        TableModel model;
        if (((DefaultTableModel) receiveMessagesTable.getModel()).getDataVector().isEmpty()) {
            model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            receiveMessagesTable.removeAll();
            receiveMessagesTable.setModel(model);
            MiscUtility.addCellHeaderRender(receiveMessagesTable);
            receiveMessagesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            receiveMessagesTable.setRowSelectionAllowed(true);
            receiveMessagesTable.setCellSelectionEnabled(false);
            receiveMessagesTable.setColumnSelectionAllowed(false);
            receiveMessagesTable.getSelectionModel().clearSelection();
            receiveMessagesTable.getTableHeader().setReorderingAllowed(false);
            receiveMessagesTable.updateUI();
        } else { // Appending data to existing data vector
            for (Object[] eachRow : data) {
                ((DefaultTableModel) receiveMessagesTable.getModel()).addRow(eachRow);
            }
        }
        updateCentralPanel();
        // updating status of no. of message deleted / received in this session in queue data
        // no. of messages deleted/received so far = no . of messages deleted/received 
        // last time this dialog opens + msgs delete/receive count for this time

        if (forMsgRcvOperation) {  // RECEIVE
            queueData.setMsgsRcvCount(queueData.getMsgsRcvCount() + data.length);
        } else {  // DELETE
            queueData.setMsgsDeleteCount(queueData.getMsgsDeleteCount() + data.length);
        }

    }

    private void setDialogIcon() {
        try {
            Image image = ImageIO.read(this.getClass().getResource(dialogIconPathFileName));
            if (image != null) {
                this.setIconImage(image);
            }
        } catch (Exception e) {
            logger.error("Exception Occured {}", e);
        }
    }

    private void addMouseWheelListenerToSpinner() {
        messagesCountSpinner.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseScrollEvent(e);
            }
        });
    }

    private Object[][] getQueueTableRecords() throws JMSException {
        int msgsToRecv = (Integer) messagesCountSpinner.getModel().getValue();
        List<Message> receiveMessagesList = queueConnectivity.receiveMessages(schema, selectedQueue, msgsToRecv);
        noOfMsgsReceived = noOfMsgsReceived + receiveMessagesList.size();
        Object[][] allMessageDetails = new Object[receiveMessagesList.size()][];
        int i = 0;
        for (Message messege : receiveMessagesList) {
            List<Object> aMsg = new ArrayList<Object>();
            try {
                aMsg.add(messege.getJMSMessageID());
                if (messege instanceof TextMessage) {
                    aMsg.add(((TextMessage) messege).getText());
                } else if (messege instanceof BytesMessage) {
                    aMsg.add("Binary Message.Cannot be displayed");
                } else if (messege instanceof StreamMessage) {
                    aMsg.add("Stream Message.Cannot be displayed");
                } else if (messege instanceof ObjectMessage) {
                    aMsg.add("Object Message.Cannot be displayed");
                } else if (messege instanceof MapMessage) {
                    aMsg.add("Map Message.Cannot be displayed");
                }
                
                aMsg.add(messege.getJMSType());
                aMsg.add(MiscUtility.parseTime(messege.getJMSTimestamp(), true));
                allMessageDetails[i] = aMsg.toArray();
                i++;
            } catch (JMSException ex) {
                logger.error("JMSException Occured {}", ex);
            }
        }

        return allMessageDetails;
    }

    private void updateCentralPanel() {
        if (((DefaultTableModel) receiveMessagesTable.getModel()).getDataVector().isEmpty()) {
            centralPanel.removeAll();
            centralPanel.add(displayInformationMessage(), BorderLayout.CENTER);
            centralPanel.updateUI();
        } else {
            centralPanel.removeAll();
            centralPanel.add(tableScrollPane, BorderLayout.CENTER);
            centralPanel.updateUI();
        }
        updateSpinnerRecvBtn();
    }

    private void updateSpinnerRecvBtn() {
        queueDepth = queueConnectivity.getQueueDepth(schema, selectedQueue);
        List maxMsgs = new ArrayList();
        maxMsgs.add(queueDepth);
        for (int i = 1; i < queueDepth; i++) {
            maxMsgs.add(i);
        }
        SpinnerListModel listModel = new SpinnerListModel(maxMsgs);
        messagesCountSpinner.setModel(listModel);
        listModel.setValue(queueDepth);
        String msg = String.format("Queue : %s , No. of Msg(s) %sd : %s",
                selectedQueue, operationType, noOfMsgsReceived);
        MiscUtility.addTitledBorderToPanel(centralPanel, msg);
        if (queueDepth > 0) {
            messagesCountSpinner.setEnabled(true);
            operateMessagesButton.setEnabled(true);
        } else {
            messagesCountSpinner.setEnabled(false);
            operateMessagesButton.setEnabled(false);
        }
    }

    private JPanel displayInformationMessage() {
        int count; // counts the no. of messgaes deleted/ received in this session when this dialog opens last time
        if (forMsgRcvOperation) {
            count = queueData.getMsgsRcvCount();
        } else {
            count = queueData.getMsgsDeleteCount();
        }
        JPanel informationMessagePanel = new JPanel(new BorderLayout());
        informationMessagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        informationMessagePanel.setBackground(Color.WHITE);
        String textToDisplay;
        if (count == 0) {
            textToDisplay = "<html><h2 style = \" text align:center ; "
                    + "color : #4E4848 ;font-size:100% \" > "
                    + "<b> No Message has been " + operationType + "d from this Queue in this Session ! </b></html>";
        } else {
            textToDisplay = "<html><h2 style = \" text align:center ; "
                    + "color : #4E4848 ;font-size:100% \" ><b>" + count
                    + " Message(s) have been " + operationType + "d from this Queue in this Session ! </b></html>";
        }

        JLabel label = new JLabel(textToDisplay, JLabel.CENTER);
        try {
            label.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/informationBallonBlue.png"))));
        } catch (IOException ex) {
            logger.error("IOException Occured {}", ex);
        }
        informationMessagePanel.add(label, BorderLayout.CENTER);
        informationMessagePanel.updateUI();
        return informationMessagePanel;

    }

    private void handleMouseScrollEvent(MouseWheelEvent e) {
        if (e.getSource() instanceof JSpinner) {
            JSpinner spinner = (JSpinner) e.getSource();
            int upVal = queueDepth;
            int val = Integer.parseInt(spinner.getValue().toString());
            if (e.getWheelRotation() == 1) {
                if (val > 1) {
                    spinner.setValue(spinner.getPreviousValue());
                } else {
                    spinner.setValue(upVal);
                }
            } else {
                if (val < upVal) {
                    spinner.setValue(spinner.getNextValue());
                } else {
                    spinner.setValue(1);
                }
            }
        }
    }

    private void initializeComponents() {
        if (forMsgRcvOperation) {
            operationType = "Receive";
            dialogIconPathFileName = "/images/rcvMsgBig.png";
        } else {
            operationType = "Delete";
            dialogIconPathFileName = "/images/deleteQueueBig.png";
        }
        operateMessagesButton.setText(operationType);
        spinnerLabel.setText(spinnerLabel.getText() + operationType);// Max No. of Messages Can Be Receive/Delete
        this.setTitle(operationType + " Messages");
    }

    private void handleTableClickEvent() {
        receiveMessagesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = receiveMessagesTable.getSelectedRow();
                    row = receiveMessagesTable.convertRowIndexToModel(row);
                    DefaultTableModel tableModel = ((DefaultTableModel) receiveMessagesTable.getModel());
                    String id = tableModel.getValueAt(row, 0).toString();
                    String msgContent = tableModel.getValueAt(row, 1).toString();
                    String msgType = tableModel.getValueAt(row, 2).toString();
                    String timestamp = tableModel.getValueAt(row, 3).toString();
                    Map<String, String> msgDetails = new HashMap<String, String>();
                    msgDetails.put("id", id);
                    msgDetails.put("timestamp", timestamp);
                    msgDetails.put("type", msgType);
                    msgDetails.put("content", msgContent);
                    MessageDetailsDialog.showQueueMessageDetailsDialog(ReceiveDeleteMessageDialog.this, msgDetails);
                }
            }
        });
    }
}
