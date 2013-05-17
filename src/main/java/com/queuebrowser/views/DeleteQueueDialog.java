package com.queuebrowser.views;

import com.queuebrowser.utility.CheckBoxListItem;
import com.queuebrowser.utility.CheckBoxListRenderer;
import com.queuebrowser.utility.DatabaseConnectivity;
import com.queuebrowser.utility.MiscUtility;
import com.queuebrowser.utility.QueueConnectivity;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This view deletes Selected Queues from the Physical database
 *
 * @author Sumit Roy
 */
public class DeleteQueueDialog extends javax.swing.JDialog {

    private DefaultListModel selectedQueueModel;
    private int selectedQueueCount;
    private List<String> allAvailableQueues;
    private String schema;
    private static final Logger logger = LoggerFactory.getLogger(DeleteQueueDialog.class);
    private QueueConnectivity queueConnectivity;

  

    /**
     * Constructor that initialize Delete Queue Dialog View
     *
     * @param allAvailableQueues list of available queues in physical database
     * @param schema schema where all these queues reside
     * @param parent Parent Frame on which this view will be open
     */
    public DeleteQueueDialog(JFrame parent, List<String> allAvailableQueues, String schema) {
        super(parent, true);
        initComponents();
        this.allAvailableQueues = allAvailableQueues;
        this.schema = schema;
        selectedQueueCount = 0;
        queueConnectivity = new QueueConnectivity();
        initialiseQueueList();
        selectAllCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAllQueueNameCheckBox(selectAllCheckBox.isSelected());
                updateSelectAllCheckBoxDeleteButtonStatus();
            }
        });
        updateSelectAllCheckBoxDeleteButtonStatus();
        this.setLocationRelativeTo(parent);
        try {
            Image image = ImageIO.read(this.getClass().getResource("/images/deleteQueueBig.png"));
            if (image != null) {
                this.setIconImage(image);
            }
        } catch (Exception e) {
            logger.error("Exception Occured {}", e);
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                queueConnectivity = null;
            }
        });
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
        selectAllCheckBox = new javax.swing.JCheckBox();
        selectQueuePanel = new javax.swing.JPanel();
        selectedQueueScrollPanel = new javax.swing.JScrollPane();
        selectedQueueList = new javax.swing.JList();
        deleteQueuesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delete Queue");
        setModal(true);
        setName("deleteQueueDialog"); // NOI18N
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Delete Queues"));
        mainPanel.setToolTipText("Delete Queue");
        mainPanel.setName("mainPanel"); // NOI18N

        jLabel1.setText("Delete Queues");

        selectAllCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        selectAllCheckBox.setText("Select All Queues");
        selectAllCheckBox.setToolTipText("Select All Queues");

        selectQueuePanel.setBackground(new java.awt.Color(255, 255, 255));
        selectQueuePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Select Queues"));

        selectedQueueScrollPanel.setBackground(new java.awt.Color(255, 255, 255));

        selectedQueueList.setToolTipText("Select Queues");
        selectedQueueScrollPanel.setViewportView(selectedQueueList);

        javax.swing.GroupLayout selectQueuePanelLayout = new javax.swing.GroupLayout(selectQueuePanel);
        selectQueuePanel.setLayout(selectQueuePanelLayout);
        selectQueuePanelLayout.setHorizontalGroup(
            selectQueuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(selectedQueueScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        );
        selectQueuePanelLayout.setVerticalGroup(
            selectQueuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(selectedQueueScrollPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );

        deleteQueuesButton.setBackground(new java.awt.Color(255, 255, 255));
        deleteQueuesButton.setText("Delete Queues");
        deleteQueuesButton.setToolTipText("Delete Queues");
        deleteQueuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteQueuesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(selectQueuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(selectAllCheckBox))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(deleteQueuesButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selectAllCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectQueuePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(deleteQueuesButton)
                .addContainerGap())
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

    private void deleteQueuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteQueuesButtonActionPerformed
        deleteQueue();
    }//GEN-LAST:event_deleteQueuesButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteQueuesButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JCheckBox selectAllCheckBox;
    private javax.swing.JPanel selectQueuePanel;
    private javax.swing.JList selectedQueueList;
    private javax.swing.JScrollPane selectedQueueScrollPanel;
    // End of variables declaration//GEN-END:variables

    private void initialiseQueueList() {
        selectedQueueModel = new DefaultListModel();
        for (String queueName : allAvailableQueues) {
            CheckBoxListItem item = new CheckBoxListItem(queueName);
            selectedQueueModel.addElement(item);
        }
        selectedQueueList.setModel(selectedQueueModel);
        selectedQueueList.setCellRenderer(new CheckBoxListRenderer());
        selectedQueueList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectedQueueList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {

                // Get index of item clicked
                int index = selectedQueueList.locationToIndex(event.getPoint());
                CheckBoxListItem item = (CheckBoxListItem) selectedQueueModel.getElementAt(index);
                // Toggle selected state
                boolean selectionStatus = item.isSelected();
                if (selectionStatus) {
                    selectedQueueCount--;
                } else {
                    selectedQueueCount++;
                }
                item.setSelected(!selectionStatus);
                // Repaint cell
                selectedQueueList.repaint(selectedQueueList.getCellBounds(index, index));
                updateSelectAllCheckBoxDeleteButtonStatus();
            }
        });
    }

    private boolean isAllQueueNamesAreSelected() {
        boolean status = true;
        for (int i = 0; i < selectedQueueModel.size(); i++) {
            CheckBoxListItem checkBoxListItem = (CheckBoxListItem) selectedQueueModel.get(i);
            if (!checkBoxListItem.isSelected()) {
                status = false;
                break;
            } else {
                continue;
            }
        }
        return status;
    }

    private void setAllQueueNameCheckBox(boolean selectionValue) {
        for (int i = 0; i < selectedQueueModel.size(); i++) {
            CheckBoxListItem checkBoxListItem = (CheckBoxListItem) selectedQueueModel.get(i);
            checkBoxListItem.setSelected(selectionValue);
            selectedQueueList.repaint();
        }
        if (selectionValue) {
            selectedQueueCount = selectedQueueModel.size();
        } else {
            selectedQueueCount = 0;
        }
    }

    private void updateSelectAllCheckBoxDeleteButtonStatus() {
        selectAllCheckBox.setSelected(isAllQueueNamesAreSelected());

        String msg;
        if (selectedQueueCount > 0) {
            msg = String.format("Selected Queues : %s", selectedQueueCount);
            deleteQueuesButton.setEnabled(true);
        } else {
            msg = String.format("Selected Queue  : %s", selectedQueueCount);
            deleteQueuesButton.setEnabled(false);
        }
        MiscUtility.addTitledBorderToPanel(selectQueuePanel, msg);
    }

    private List<String> getCheckedQueues() {
        List<String> selectedQueues = new ArrayList<String>();
        for (int i = 0; i < selectedQueueModel.size(); i++) {
            CheckBoxListItem checkBoxListItem = (CheckBoxListItem) selectedQueueModel.get(i);
            if (checkBoxListItem.isSelected()) {
                selectedQueues.add(checkBoxListItem.toString());
            }

        }
        return selectedQueues;
    }

    private void deleteQueue() {
        List<String> selectedQueues = new ArrayList<String>();
        List<String> rejectedQueues = new ArrayList<String>();
        List<String> existingQueues = DatabaseConnectivity.getQueueNames(schema);
        // Selecting only those queue from list of checked queues that are presently in the physical database
        for (String aQueue : getCheckedQueues()) {
            if (existingQueues.contains(aQueue)) {
                selectedQueues.add(aQueue);
            } else {
                rejectedQueues.add(aQueue);
            }
        }
        if (!selectedQueues.isEmpty()) {
            try {
                queueConnectivity.deleteQueues(selectedQueues, schema);
                String msgToDisplay = String.format("Selected Queues were deleted sucessfully !");
                JOptionPane.showMessageDialog(this, msgToDisplay, "Queue Deleted", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (SQLException ex) {
                String msgToDisplay = "Unable to Delete Selected Queue(s)\n\tWould you like to check Error Details ?";
                int option = JOptionPane.showConfirmDialog(this, msgToDisplay, "Queue Deletion Failure", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    ExceptionDetailsDialog.showExceptionsDetailsDialog(this, "SQL Exception", "Queue(s) Deletion", ex);
                    logger.error("SQLException Occured ", ex);
                }
            }
        } else { // No Selected Queues are in the Physical DB
            StringBuilder rejectedQueueNames = new StringBuilder();
            for (String rejectedQueue : rejectedQueues) {
                rejectedQueueNames.append(",");
                rejectedQueueNames.append(rejectedQueue);
            }
            rejectedQueueNames.deleteCharAt(0); // deleting the , from begining !
            String msgToDisplay = String.format("Sorry these Queue(s) are no more in physical Database !\n%s", rejectedQueueNames);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue(s) No more Exist", JOptionPane.ERROR_MESSAGE);
        }
    }
}
