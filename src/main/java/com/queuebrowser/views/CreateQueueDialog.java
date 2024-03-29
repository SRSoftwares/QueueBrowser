package com.queuebrowser.views;

import com.queuebrowser.utility.DatabaseConnectivity;
import com.queuebrowser.utility.MiscUtility;
import com.queuebrowser.utility.QueueConnectivity;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View that creates a new Queue Creation Dialog
 *
 * @author Sumit Roy
 */
public class CreateQueueDialog extends javax.swing.JDialog {

    private List<Character> exceptionalCharList;
    private String schema;
    private static final Logger logger = LoggerFactory.getLogger(CreateQueueDialog.class);
    private QueueConnectivity queueConnectivity;


    /**
     * Constructor that create a Queue Creation dialog and displays
     * over a JFrame
     *
     * @param schema Scheme name where the new queue will be created
     * @param parent parent frame over which this dialog will be open
     */
    
    public CreateQueueDialog(JFrame parent,String schema) {
        super(parent, true);
        initComponents();
        generateInvalidCharSet();
        this.schema = schema;
        queueConnectivity = new QueueConnectivity();
        this.setLocationRelativeTo(parent);
        try {
            Image image = ImageIO.read(this.getClass().getResource("/images/createNewQueueBig.png"));
            if (image != null) {
                this.setIconImage(image);
            }
        } catch (Exception e) {
            logger.error("Exception Occured {}", e);
        }
        addFilterToTextField();
        MiscUtility.addEnterKeyAction(queueNameTextField, createQueueButton);
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
        queueNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        createQueueButton = new javax.swing.JButton();
        namingRulesRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create A Queue");
        setModal(true);
        setName("createQueueDialog"); // NOI18N
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Create Queue"));
        mainPanel.setToolTipText("Create Queue");

        queueNameTextField.setToolTipText("Queue Name");
        queueNameTextField.setName("queueNameTextField"); // NOI18N

        jLabel1.setText("Queue Name");

        createQueueButton.setText("Create Queue");
        createQueueButton.setToolTipText("Create Queue");
        createQueueButton.setEnabled(false);
        createQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createQueueButtonActionPerformed(evt);
            }
        });

        namingRulesRadioButton.setBackground(new java.awt.Color(255, 240, 240));
        namingRulesRadioButton.setText("See Queue Naming Rules");
        namingRulesRadioButton.setFocusPainted(false);
        namingRulesRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                namingRulesRadioButtonItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(queueNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(createQueueButton))
                    .addComponent(namingRulesRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(queueNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createQueueButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(namingRulesRadioButton)
                .addGap(15, 15, 15))
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

    private void createQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createQueueButtonActionPerformed
        createQueue();
    }//GEN-LAST:event_createQueueButtonActionPerformed

    private void createQueue() {
        String queueName = queueNameTextField.getText();
        // Checking whether selected queue is still available
        List<String> queueNodes = DatabaseConnectivity.getQueueNames(schema);
        if (true) {  //!queueNodes.contains(queueName)
            String queueTableName = String.format("Q_%s_AQ", queueName);
            try {
                queueConnectivity.createQueue(queueName, queueTableName);
                String msgToDisplay = String.format("Queue : %s is created Succesfully !\n\tWould you like to create another queue ?", queueName);
                int option = JOptionPane.showConfirmDialog(this, msgToDisplay, "Queue Created", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) { // Send Anothe Message
                    queueNameTextField.setText(""); //Resetting the Queue Name Text Field
                } else { //Close this SendMesageDialog
                    this.dispose();
                }
            } catch (SQLException ex) {
                String msgToDisplay = String.format("Sorry ! Unable to create Queue : %s\n\tWould you like to check Error Details ?", queueName);
                int option = JOptionPane.showConfirmDialog(this, msgToDisplay, "Queue Not Created", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    ExceptionDetailsDialog.showExceptionsDetailsDialog(this,"SQL Exception", String.format("Queue Creation for Queue : %s", queueName), ex);
                    logger.error("SQLException Occured - ", ex);
                }
            }
        } else { // Queue Already Exist 
            String msgToDisplay = String.format("Sorry this Queue : %s is already in Database ! Enter Some other Queue Name", queueName);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue Already Exist", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void namingRulesRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_namingRulesRadioButtonItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
             
            QueueCreationNameRulesDialog.showQueueCreationRulesDialog(CreateQueueDialog.this,
                    exceptionalCharList);   
          
            
        }
    }//GEN-LAST:event_namingRulesRadioButtonItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createQueueButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton namingRulesRadioButton;
    private javax.swing.JTextField queueNameTextField;
    // End of variables declaration//GEN-END:variables

    private void addFilterToTextField() {
        queueNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                    int caretPos = queueNameTextField.getCaretPosition();
                    char typeChar = e.getKeyChar();
                    if (isWrongCharTyped(typeChar) || (caretPos == 0 && isInvalidStartingChar(typeChar))) {
                        e.consume();
                        Toolkit.getDefaultToolkit().beep();
                        createQueueButton.setEnabled(false);
                    } else {
                        createQueueButton.setEnabled(true);
                    }
                }

            }
        });
    }

    private boolean isWrongCharTyped(char ch) {
        String chr = String.valueOf(ch);
        return exceptionalCharList.contains(chr);
    }

    private boolean isInvalidStartingChar(char ch) {
        boolean status;
        if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    private void generateInvalidCharSet() {
        exceptionalCharList = new ArrayList<Character>();
        exceptionalCharList.add(' '); // Space
        exceptionalCharList.add('.'); // Dot
        exceptionalCharList.add(','); // Comma
        exceptionalCharList.add('/'); // Front Slash
        exceptionalCharList.add('\\');// Back Slash
        exceptionalCharList.add(':'); // Colon
        exceptionalCharList.add(';'); // Semi Colon
        exceptionalCharList.add('!'); // Exclamation
        exceptionalCharList.add('@'); // at
        exceptionalCharList.add('#'); // Hash
        exceptionalCharList.add('$'); // Dollar
        exceptionalCharList.add('%'); // Percentile
        exceptionalCharList.add('^'); // Exp
        exceptionalCharList.add('&'); // And
        exceptionalCharList.add('*'); // Asterik
        exceptionalCharList.add('('); // Opening Curve Brace
        exceptionalCharList.add(')'); // Closing Curve Brace
        exceptionalCharList.add('-'); // Minus
        exceptionalCharList.add('+'); // Plus
        exceptionalCharList.add('='); // Equal
        exceptionalCharList.add('{'); // Opening Curly Brace
        exceptionalCharList.add('}'); // Closing Curly Brace
        exceptionalCharList.add('['); // Opening Brace
        exceptionalCharList.add(']'); // Closing Brace
        exceptionalCharList.add('`'); // Tilt
        exceptionalCharList.add('~'); // Curve
        exceptionalCharList.add('?'); // Question
        exceptionalCharList.add('>'); // Greater
        exceptionalCharList.add('<'); // Lesser
        exceptionalCharList.add('|'); // Pipe
    }
}
