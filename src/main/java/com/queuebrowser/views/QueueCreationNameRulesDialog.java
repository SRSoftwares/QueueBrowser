/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.views;

import java.awt.Image;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author - Sumit Roy
 * @Created On - Feb 27, 2013,8:30:20 PM
 * @Project - JMSMessageViewGUI
 */
public class QueueCreationNameRulesDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(QueueCreationNameRulesDialog.class);
    private static QueueCreationNameRulesDialog queueCreationNameRulesDialog = null;

    /**
     * Constructor that initialize Queue Naming Rules and Restrictions View on a
     * JDialog
     *
     * @param exceptionalCharList sets of exceptional characters which can not
     * be accepted
     * @param parent parent dialog on which this view will be open
     */
    private QueueCreationNameRulesDialog(JDialog parent) {
        super(parent, true);
        initComponents();
        setDialogIcon();

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
        jLabel2 = new javax.swing.JLabel();
        invalidStartCharsLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        excepationalCharListTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Queue Name Rules");
        setName("queueCreationNameRulesDialog"); // NOI18N
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Queue Name Rules"));

        jLabel1.setText("Queue Name Cannot Contain Following Characters");

        jLabel2.setText("Queue Name Can Start with Only Following Characters");

        invalidStartCharsLabel.setText("A-Z and a-z");
        invalidStartCharsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        excepationalCharListTextArea.setEditable(false);
        excepationalCharListTextArea.setColumns(20);
        excepationalCharListTextArea.setLineWrap(true);
        excepationalCharListTextArea.setRows(5);
        excepationalCharListTextArea.setToolTipText("These Charactes cannot be used in Queue Name");
        excepationalCharListTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(excepationalCharListTextArea);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(invalidStartCharsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(okButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invalidStartCharsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(okButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea excepationalCharListTextArea;
    private javax.swing.JLabel invalidStartCharsLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private void setDialogIcon() {
        try {
            Image image = ImageIO.read(this.getClass().getResource("/images/informationBallonBlue2.png"));
            if (image != null) {
                this.setIconImage(image);
            }
        } catch (Exception e) {
            logger.error("Exception Occured {}", e);
        }
    }

    private void populateInformationFields(List<Character> exceptionalCharList) {
        StringBuilder wrongChars = new StringBuilder("");
        for (char eachChar : exceptionalCharList) {
            if (eachChar == ' ') {
                wrongChars.append("<Space>");
            } else {
                wrongChars.append(String.valueOf(eachChar));
            }
            wrongChars.append(" ");
        }
        excepationalCharListTextArea.setText(wrongChars.toString().trim());

    }

    public static void showQueueCreationRulesDialog(JDialog parent, List<Character> exceptionalCharList) {
        if (queueCreationNameRulesDialog == null) {
            queueCreationNameRulesDialog = new QueueCreationNameRulesDialog(parent);
        }
        queueCreationNameRulesDialog.populateInformationFields(exceptionalCharList);
        queueCreationNameRulesDialog.setLocationRelativeTo(parent);
        queueCreationNameRulesDialog.setVisible(true);
    }
}
