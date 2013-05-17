package com.queuebrowser.views;

import com.queuebrowser.datatypes.MessageStatusEnum;
import com.queuebrowser.utility.DatabaseConnectivity;
import com.queuebrowser.utility.MiscUtility;
import com.queuebrowser.utility.QueueConnectivity;
import com.queuebrowser.datatypes.QueueData;
import com.queuebrowser.utility.TreeRenderer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main JMS Message Monitoring View Tool
 *
 * @author Sumit Roy
 */
public class MainMonitoringWindow extends javax.swing.JFrame
        implements TreeSelectionListener, ActionListener {

    private static final Logger logger = LoggerFactory.getLogger(MainMonitoringWindow.class);
    private String currentlySelectedQueue;
    private String connectedSchema;
    private Map<String, String> userCredentials;
    private Map<String, QueueData> queueRecords;
    private List<Message> allMessages;
    private String[] columnNames;
    private List<String> allAvailableQueueNodes;
    private SendMessageDialog sendMessageDialog;
    private CreateQueueDialog createQueueDialog;
    private DeleteQueueDialog deleteQueueDialog;
    private JMenuItem createQueuMenuItem;
    private JMenuItem deleteAllQueuesMenuItem;
    private JMenuItem refreshQueuMenuItem;
    private JMenuItem sendMsgItem;
    private JMenuItem rcvMsgMenuItem;
    private JMenuItem refreshQueueMsgMenuItem;
    private QueueConnectivity queueConnectivity;
    private ReceiveDeleteMessageDialog receiveDeleteMessageDialog;
    private JMenuItem deleteAllMessagesFromQueueItem;
    private JMenuItem deleteQueueMenuItem;
    private QueueData queueData;
    private Object[][] tableData;
    private ProgressPanel progressPanel;
    private PurgeQueueMessagesDialog purgeQueueMessagesDialog;

    /**
     * Creates new form MainMonitoringWindow with given schema name and queue
     * name
     *
     * @param connectedSchema name of the schema where the queue resides
     * @param currentlySelectedQueue name of the queue being accessed
     */
    private MainMonitoringWindow(Map<String, String> userCredentials) {
        initComponents();
        this.userCredentials = userCredentials;
        connectedSchema = userCredentials.get("schema");
        currentlySelectedQueue = "N/A";
        queueRecords = new HashMap<String, QueueData>();
        allMessages = new ArrayList<Message>();
        columnNames = new String[]{"Message ID", "Message Content", "Message Type", "Timestamp"};
        try {
            Image image = ImageIO.read(this.getClass().getResource("/images/appIcon.png"));
            if (image != null) {
                this.setIconImage(image);
            }
        } catch (IOException ex) {
            logger.error("IOException Occured {}", ex);
        }
        initTable();
        createQueueTree();
        addWidgetHoverActions();
        updateStatusMessagePanel(true);
        handleTableClickEvent(); // Binding action listener to table if it does not exist earlier
        createJMenuItems();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseConnectivity.closeDatabaseConnection();
                queueConnectivity = null;
                MainMonitoringWindow.this.dispose();
                System.exit(0);
            }
        });
    }

    private void initTable() {
        // Setting Property to Message Table and its model for the first time only
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        messageTable.setModel(model);
        messageTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageTable.setRowSelectionAllowed(true);
        messageTable.setCellSelectionEnabled(false);
        messageTable.setColumnSelectionAllowed(false);
        messageTable.getSelectionModel().clearSelection();
    }

    public static void showMainMonitoringWindow(Map<String, String> userCredentials) {
        MainMonitoringWindow mmw = new MainMonitoringWindow(userCredentials);
        mmw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mmw.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JSplitPane();
        centralPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        databaseNameLabel = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        queueLabel = new javax.swing.JLabel();
        schemaLabel = new javax.swing.JLabel();
        databaseValLabel = new javax.swing.JLabel();
        hostValLabel = new javax.swing.JLabel();
        schemaValLabel = new javax.swing.JLabel();
        portValLabel = new javax.swing.JLabel();
        loggedInLabel = new javax.swing.JLabel();
        queueStatusLabel = new javax.swing.JLabel();
        lastRefreshLabel = new javax.swing.JLabel();
        queueValLabel = new javax.swing.JLabel();
        loggedInValLabel = new javax.swing.JLabel();
        queueStatusValLabel = new javax.swing.JLabel();
        lastRefreshValLabel = new javax.swing.JLabel();
        centralMainPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        tableScrollPane = new javax.swing.JScrollPane();
        messageTable = new javax.swing.JTable();
        widgetCardPanel = new javax.swing.JPanel();
        messageWidgetSchemaToolbarPanel = new javax.swing.JPanel();
        deleteQueueButton = new javax.swing.JButton();
        refreshQueueButton = new javax.swing.JButton();
        createQueueButton = new javax.swing.JButton();
        purgeQueueBtn = new javax.swing.JButton();
        messageWidgetQueueToolbarPanel = new javax.swing.JPanel();
        deleteMessagesButton = new javax.swing.JButton();
        sendMessagesButton = new javax.swing.JButton();
        receiveMessagesButton = new javax.swing.JButton();
        refreshMessagesButton = new javax.swing.JButton();
        leftPanel = new javax.swing.JPanel();
        treeScrollPane = new javax.swing.JScrollPane();
        queueTree = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Main Queue Browsing Window - Queue Browser");
        setBackground(new java.awt.Color(255, 255, 255));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setFocusCycleRoot(false);
        setName("mainMonitoringWindow"); // NOI18N
        setState(JFrame.MAXIMIZED_BOTH);

        mainPane.setBackground(new java.awt.Color(255, 255, 255));
        mainPane.setDividerLocation(200);
        mainPane.setOneTouchExpandable(true);

        centralPanel.setBackground(new java.awt.Color(255, 255, 255));
        centralPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        centralPanel.setLayout(new java.awt.BorderLayout());

        statusPanel.setBackground(new java.awt.Color(255, 240, 240));
        statusPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Connection Status"));

        databaseNameLabel.setBackground(new java.awt.Color(255, 240, 240));
        databaseNameLabel.setText("Database : ");

        hostLabel.setBackground(new java.awt.Color(255, 240, 240));
        hostLabel.setText("Host : ");

        portLabel.setBackground(new java.awt.Color(255, 240, 240));
        portLabel.setText("Port : ");

        queueLabel.setBackground(new java.awt.Color(255, 240, 240));
        queueLabel.setText("Queue : ");

        schemaLabel.setBackground(new java.awt.Color(255, 240, 240));
        schemaLabel.setText("Schema : ");

        databaseValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        databaseValLabel.setText("Oracle");

        hostValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hostValLabel.setText("255.255.255.255");

        schemaValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        schemaValLabel.setText("SCHEMA");

        portValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        portValLabel.setText("0000");

        loggedInLabel.setText("Log In : ");

        queueStatusLabel.setText("Queue Status : ");

        lastRefreshLabel.setText("Last Refresh : ");

        queueValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        queueValLabel.setText("QUEUE");

        loggedInValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loggedInValLabel.setText("23:59:59.000 AM");

        queueStatusValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        queueStatusValLabel.setText("STATUS");

        lastRefreshValLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lastRefreshValLabel.setText("23:59:59.000 AM");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(databaseNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(databaseValLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(hostLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hostValLabel)))
                .addGap(51, 51, 51)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(portLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portValLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(schemaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(schemaValLabel)))
                .addGap(27, 27, 27)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(queueLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queueValLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(loggedInLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loggedInValLabel)))
                .addGap(49, 49, 49)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(lastRefreshLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(queueStatusLabel)
                        .addGap(1, 1, 1)))
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(queueStatusValLabel)
                    .addComponent(lastRefreshValLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(databaseNameLabel)
                    .addComponent(queueLabel)
                    .addComponent(schemaLabel)
                    .addComponent(databaseValLabel)
                    .addComponent(schemaValLabel)
                    .addComponent(queueStatusLabel)
                    .addComponent(queueValLabel)
                    .addComponent(queueStatusValLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostLabel)
                    .addComponent(portLabel)
                    .addComponent(hostValLabel)
                    .addComponent(portValLabel)
                    .addComponent(lastRefreshLabel)
                    .addComponent(lastRefreshValLabel)
                    .addComponent(loggedInLabel)
                    .addComponent(loggedInValLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        centralPanel.add(statusPanel, java.awt.BorderLayout.SOUTH);

        centralMainPanel.setBackground(new java.awt.Color(255, 255, 255));
        centralMainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        centralMainPanel.setLayout(new java.awt.BorderLayout());

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));
        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Message View"));

        tableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        messageTable.setAutoCreateRowSorter(true);
        messageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        messageTable.setToolTipText("Shows Details about a Table");
        messageTable.setFillsViewportHeight(true);
        messageTable.setName("messageTable"); // NOI18N
        messageTable.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(messageTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );

        centralMainPanel.add(tablePanel, java.awt.BorderLayout.CENTER);

        centralPanel.add(centralMainPanel, java.awt.BorderLayout.CENTER);

        widgetCardPanel.setBackground(new java.awt.Color(255, 240, 240));
        widgetCardPanel.setLayout(new java.awt.CardLayout());

        messageWidgetSchemaToolbarPanel.setBackground(new java.awt.Color(255, 240, 240));
        messageWidgetSchemaToolbarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Create/Delete/Refresh Queues"));
        messageWidgetSchemaToolbarPanel.setToolTipText("Using various items of this Widget send receive messages and delete all messages from queues");
        messageWidgetSchemaToolbarPanel.setName("messageWidgetQueueToolbarPanel"); // NOI18N

        deleteQueueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteQueueSmall.png"))); // NOI18N
        deleteQueueButton.setText("<html><h5 style = \"text-align :center\">Delete<br>Queues</html>");
        deleteQueueButton.setToolTipText("Delete Queues");
        deleteQueueButton.setBorderPainted(false);
        deleteQueueButton.setContentAreaFilled(false);
        deleteQueueButton.setFocusPainted(false);
        deleteQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteQueueButtonActionPerformed(evt);
            }
        });

        refreshQueueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refreshQueueSmall.png"))); // NOI18N
        refreshQueueButton.setText("<html><h5 style = \"text-align :center\">Refresh<br>Queues</html>");
        refreshQueueButton.setToolTipText("Refresh Queues");
        refreshQueueButton.setBorderPainted(false);
        refreshQueueButton.setContentAreaFilled(false);
        refreshQueueButton.setFocusPainted(false);
        refreshQueueButton.setName("Refresh Queue"); // NOI18N
        refreshQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshQueueButtonActionPerformed(evt);
            }
        });

        createQueueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/createNewQueueSmall.png"))); // NOI18N
        createQueueButton.setText("<html><h5 style = \"text-align :center\">Create<br>Queue</html>");
        createQueueButton.setToolTipText("Create New Queues");
        createQueueButton.setBorderPainted(false);
        createQueueButton.setContentAreaFilled(false);
        createQueueButton.setFocusPainted(false);
        createQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createQueueButtonActionPerformed(evt);
            }
        });

        purgeQueueBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emptyQueueSmall.png"))); // NOI18N
        purgeQueueBtn.setText("<html><h5 style = \"text-align :center\">Purge<br>Queue</html>");
        purgeQueueBtn.setBorderPainted(false);
        purgeQueueBtn.setContentAreaFilled(false);
        purgeQueueBtn.setFocusPainted(false);
        purgeQueueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purgeQueueBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout messageWidgetSchemaToolbarPanelLayout = new javax.swing.GroupLayout(messageWidgetSchemaToolbarPanel);
        messageWidgetSchemaToolbarPanel.setLayout(messageWidgetSchemaToolbarPanelLayout);
        messageWidgetSchemaToolbarPanelLayout.setHorizontalGroup(
            messageWidgetSchemaToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageWidgetSchemaToolbarPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(createQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(purgeQueueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        messageWidgetSchemaToolbarPanelLayout.setVerticalGroup(
            messageWidgetSchemaToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageWidgetSchemaToolbarPanelLayout.createSequentialGroup()
                .addGroup(messageWidgetSchemaToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(deleteQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purgeQueueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        widgetCardPanel.add(messageWidgetSchemaToolbarPanel, "schemaCard");

        messageWidgetQueueToolbarPanel.setBackground(new java.awt.Color(255, 240, 240));
        messageWidgetQueueToolbarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Send/Recieve/Delete/Refresh Messages"));
        messageWidgetQueueToolbarPanel.setToolTipText("Using various items of this Widget send receive messages and delete all messages from queues");
        messageWidgetQueueToolbarPanel.setName("messageWidgetQueueToolbarPanel"); // NOI18N

        deleteMessagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteQueueSmall.png"))); // NOI18N
        deleteMessagesButton.setText("<html><h5 style = \"text-align :center\">Delete<br>Messages</html>");
        deleteMessagesButton.setToolTipText("Delete Messages from Queue");
        deleteMessagesButton.setBorderPainted(false);
        deleteMessagesButton.setContentAreaFilled(false);
        deleteMessagesButton.setFocusPainted(false);
        deleteMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMessagesButtonActionPerformed(evt);
            }
        });

        sendMessagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sendMsgSmall.png"))); // NOI18N
        sendMessagesButton.setText("<html><h5 style = \"text-align :center\">Send   <br>Messages</html>");
        sendMessagesButton.setToolTipText("Send A Message");
        sendMessagesButton.setBorderPainted(false);
        sendMessagesButton.setContentAreaFilled(false);
        sendMessagesButton.setFocusPainted(false);
        sendMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessagesButtonActionPerformed(evt);
            }
        });

        receiveMessagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rcvMsgSmall.png"))); // NOI18N
        receiveMessagesButton.setText("<html><h5 style = \"text-align :center\">Receive<br>Messages</html>");
        receiveMessagesButton.setToolTipText("Receive a Message");
        receiveMessagesButton.setBorderPainted(false);
        receiveMessagesButton.setContentAreaFilled(false);
        receiveMessagesButton.setFocusPainted(false);
        receiveMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveMessagesButtonActionPerformed(evt);
            }
        });

        refreshMessagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refreshQueueSmall.png"))); // NOI18N
        refreshMessagesButton.setText("<html><h5 style = \"text-align :center\">Refresh<br>Meesages</html>");
        refreshMessagesButton.setToolTipText("Refresh Queue Table Messages");
        refreshMessagesButton.setBorderPainted(false);
        refreshMessagesButton.setContentAreaFilled(false);
        refreshMessagesButton.setFocusPainted(false);
        refreshMessagesButton.setName("Refresh Queue"); // NOI18N
        refreshMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMessagesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout messageWidgetQueueToolbarPanelLayout = new javax.swing.GroupLayout(messageWidgetQueueToolbarPanel);
        messageWidgetQueueToolbarPanel.setLayout(messageWidgetQueueToolbarPanelLayout);
        messageWidgetQueueToolbarPanelLayout.setHorizontalGroup(
            messageWidgetQueueToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageWidgetQueueToolbarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sendMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(receiveMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(161, Short.MAX_VALUE))
        );
        messageWidgetQueueToolbarPanelLayout.setVerticalGroup(
            messageWidgetQueueToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messageWidgetQueueToolbarPanelLayout.createSequentialGroup()
                .addGroup(messageWidgetQueueToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(receiveMessagesButton)
                    .addComponent(sendMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshMessagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        widgetCardPanel.add(messageWidgetQueueToolbarPanel, "queueCard");

        centralPanel.add(widgetCardPanel, java.awt.BorderLayout.NORTH);

        mainPane.setRightComponent(centralPanel);

        leftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "JMS Queues")));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        queueTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        queueTree.setToolTipText("JMS Available Queue");
        queueTree.setAutoscrolls(true);
        queueTree.setName("queueTree"); // NOI18N
        treeScrollPane.setViewportView(queueTree);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(treeScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(treeScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );

        mainPane.setLeftComponent(leftPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMessagesButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                refreshMessageTable();
            }
        });
    }//GEN-LAST:event_refreshMessagesButtonActionPerformed

    private void sendMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessagesButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                sendMessageDialog = new SendMessageDialog(MainMonitoringWindow.this, currentlySelectedQueue,
                        connectedSchema);
                refreshMessageTable();
            }
        });
    }//GEN-LAST:event_sendMessagesButtonActionPerformed

    private void refreshQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshQueueButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                refreshQueueTree(false);
            }
        });
    }//GEN-LAST:event_refreshQueueButtonActionPerformed

    private void createQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createQueueButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                createQueueDialog = new CreateQueueDialog(MainMonitoringWindow.this, connectedSchema);
                refreshQueueTree(false);
            }
        });
    }//GEN-LAST:event_createQueueButtonActionPerformed

    private void deleteQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteQueueButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                deleteQueueDialog = new DeleteQueueDialog(MainMonitoringWindow.this, allAvailableQueueNodes, connectedSchema);
                refreshQueueTree(false);
            }
        });

    }//GEN-LAST:event_deleteQueueButtonActionPerformed

    private void receiveMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveMessagesButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                receiveDeleteMessageDialog = new ReceiveDeleteMessageDialog(
                        MainMonitoringWindow.this, currentlySelectedQueue,
                        connectedSchema, true, queueRecords.get(currentlySelectedQueue));
                refreshMessageTable();
            }
        });

    }//GEN-LAST:event_receiveMessagesButtonActionPerformed

    private void deleteMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMessagesButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                receiveDeleteMessageDialog = new ReceiveDeleteMessageDialog(
                        MainMonitoringWindow.this, currentlySelectedQueue, connectedSchema,
                        false, queueRecords.get(currentlySelectedQueue));
                refreshMessageTable();
            }
        });

    }//GEN-LAST:event_deleteMessagesButtonActionPerformed

    private void purgeQueueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purgeQueueBtnActionPerformed
        purgeQueueMessagesDialog=new PurgeQueueMessagesDialog(MainMonitoringWindow.this,allAvailableQueueNodes,connectedSchema);
        
    }//GEN-LAST:event_purgeQueueBtnActionPerformed

    private void showPopUp(MouseEvent e) {
        queueTree.setSelectionPath(queueTree.getClosestPathForLocation(e.getX(), e.getY()));
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) queueTree.getLastSelectedPathComponent();
        int level = node.getLevel();
        currentlySelectedQueue = String.valueOf(node.getUserObject());
        if (e.isPopupTrigger()) {
            JPopupMenu popName;
            switch (level) {
                case 0: // @ SCHEMA LEVEL
                    popName = new JPopupMenu();
                    popName.add(createQueuMenuItem);
                    popName.add(deleteAllQueuesMenuItem);
                    popName.add(refreshQueuMenuItem);
                    popName.show(queueTree, e.getX(), e.getY());
                    break;
                case 1: // @ QUEUE LEVEL
                    popName = new JPopupMenu();
                    popName.add(sendMsgItem);
                    popName.add(rcvMsgMenuItem);
                    popName.add(deleteAllMessagesFromQueueItem);
                    popName.add(new JSeparator(JSeparator.HORIZONTAL));
                    popName.add(deleteQueueMenuItem);
                    popName.add(refreshQueueMsgMenuItem);
                    popName.show(queueTree, e.getX(), e.getY());
                    break;

            }
        }

    }

    private void createJMenuItems() {
        // SCHEMA LEVEL
        createQueuMenuItem = getMenuItem("Create Queue", "createQueueAction", "createQueueMenuItem.png");
        deleteAllQueuesMenuItem = getMenuItem("Delete All Queues", "deleteAllQueuesAction", "deleteQueueMenuItem.png");
        refreshQueuMenuItem = getMenuItem("Refresh Queue", "refreshQueueListAction", "refreshQueueMenuItem.png");

        // QUEUE LEVEL
        sendMsgItem = getMenuItem("Send Message", "sendMessageAction", "sendMsgMenuItem.png");
        rcvMsgMenuItem = getMenuItem("Receive Message", "receiveMessageAction", "rcvMsgMenuItem.png");
        refreshQueueMsgMenuItem = getMenuItem("Refresh Messages", "refreshQueueTableAction", "refreshQueueMenuItem.png");
        deleteQueueMenuItem = getMenuItem("Delete this Queue", "deleteQueueAction", "deleteQueueMenuItem.png");
        deleteAllMessagesFromQueueItem = getMenuItem("Delete All Messages",
                "deleteAllQueueMessagesAction", "deleteAllQueuesMenuItem.png");
    }

    private JMenuItem getMenuItem(String menuName, String actionCommand, String menuIcon) {
        JMenuItem menuItem = new JMenuItem("");
        try {
            menuItem = new JMenuItem(menuName, new ImageIcon(
                    ImageIO.read(this.getClass().getResource("/images/" + menuIcon))));
            menuItem.setActionCommand(actionCommand);
            menuItem.addActionListener(this);
        } catch (IOException ex) {
            logger.error("IOException Occured {}", ex);
        }
        return menuItem;
    }

    private void createQueueTree() {
        refreshQueueTree(true);
        centralMainPanel.removeAll();
        centralMainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        centralMainPanel.add(displayInformationBigMessage(), BorderLayout.CENTER);
        centralMainPanel.updateUI();
        queueTree.setCellRenderer(new TreeRenderer());
        queueTree.addTreeSelectionListener(this);
        queueTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopUp(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopUp(e);
            }
        });
    }

    private void createQueueMessageTablePanel() {
        if (queueRecords.containsKey(currentlySelectedQueue)) {
            centralMainPanel.removeAll();
            centralMainPanel.updateUI();
            queueData = queueRecords.get(currentlySelectedQueue);
            tableData = queueData.getTableData();
            progressPanel = new ProgressPanel();
            if (tableData == null) { // if the queue is accessed for the first time then datas are fetched from database
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        centralMainPanel.removeAll();
                        BoxLayout layout=new BoxLayout(centralMainPanel, BoxLayout.Y_AXIS);
                        centralMainPanel.setLayout(layout);
                        centralMainPanel.add(Box.createVerticalGlue());
                        centralMainPanel.add(progressPanel, BorderLayout.CENTER);
                        progressPanel.setVisible(true);
                        centralPanel.updateUI();
                        centralPanel.setVisible(true);

                    }
                });
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        tableData = getQueueTableRecords();  // live record fetching from database                          
                        queueData.setQueueLastAccessTime(System.currentTimeMillis()); // modifying time
                        queueData.setTableData(tableData); // updating queueData cache
                        createAndShowTableGUI();
                    }
                });
                t.start();

            }else{
            createAndShowTableGUI();
            }


        }
    }
    private  void createAndShowTableGUI(){
            ((DefaultTableModel) messageTable.getModel()).setDataVector(tableData, columnNames);
            int queueDepth = queueData.getQueueDepth();
            if (queueDepth > 0) { // When Queue has valid queue depth
                queueData.setQueueStatus(MessageStatusEnum.ACTIVE);
                String titleToSet = String.format("Message View , Queue Depth : %s", queueDepth);
                MiscUtility.addTitledBorderToPanel(tablePanel, titleToSet);
                messageTable.getSelectionModel().clearSelection();
                centralMainPanel.removeAll();
                centralMainPanel.setBorder(null);
                centralMainPanel.add(tablePanel, BorderLayout.CENTER);
                centralMainPanel.updateUI();
                enablePopUpMenuItemWidgetButton();
            } else { // If Selected Queue has No Messages
                queueData.setQueueStatus(MessageStatusEnum.EMPTY);
                centralMainPanel.removeAll();
                centralMainPanel.add(displayErrorMsg("Queue", currentlySelectedQueue), BorderLayout.CENTER);
                centralMainPanel.updateUI();
                disablePopUpMenuItemWidgetButton(true);
            }
    }
    
    private void handleTableClickEvent() {
        messageTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = messageTable.getSelectedRow();
                    row = messageTable.convertRowIndexToModel(row);
                    DefaultTableModel tableModel = ((DefaultTableModel) messageTable.getModel());
                    String id = tableModel.getValueAt(row, 0).toString();
                    String msgContent = tableModel.getValueAt(row, 1).toString();
                    String msgType = tableModel.getValueAt(row, 2).toString();
                    String timestamp = tableModel.getValueAt(row, 3).toString();
                    final Map<String, String> msgDetails = new HashMap<String, String>();
                    msgDetails.put("id", id);
                    msgDetails.put("timestamp", timestamp);
                    msgDetails.put("type", msgType);
                    msgDetails.put("content", msgContent);
                    MessageDetailsDialog.showQueueMessageDetailsDialog(MainMonitoringWindow.this, msgDetails);
                }
            }
        });
    }

    /**
     * Method that fires when a JTree node is being selected
     *
     * @param e Tree Selection Event object
     */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) queueTree.getLastSelectedPathComponent();
        String selectedNode = String.valueOf(node.getUserObject());
        int level = node.getLevel();
        if (level == 0) { // Schema Level
            ((CardLayout) widgetCardPanel.getLayout()).first(widgetCardPanel);
            centralMainPanel.removeAll();
            centralMainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            centralMainPanel.add(displayInformationBigMessage(), BorderLayout.CENTER);
            centralMainPanel.updateUI();
            currentlySelectedQueue = "N/A";
        } else if (level == 1) {
            ((CardLayout) widgetCardPanel.getLayout()).last(widgetCardPanel);
            centralMainPanel.setBorder(null);
            currentlySelectedQueue = selectedNode;
            createQueueMessageTablePanel();
            MiscUtility.addCellHeaderRender(messageTable);

        }
        updateStatusMessagePanel(false);
    }

    private JPanel displayInformationBigMessage() {
        JPanel informationMessagePanel = new JPanel(new BorderLayout());
        informationMessagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        informationMessagePanel.setBackground(Color.WHITE);
        JLabel label = new JLabel("<html><h1 style = \" text align:center ; "
                + "color : #4E4848 ;font-size:140% \" > "
                + "<b> Please select a Queue from the left side </b></html>", JLabel.CENTER);
        try {
            label.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource(
                    "/images/informationBallon.png")))); 
        } catch (IOException ex) {
            logger.error("IOException Occured {}", ex);
        }
        informationMessagePanel.add(label, BorderLayout.CENTER);
        informationMessagePanel.updateUI();
        return informationMessagePanel;

    }

    private JPanel displayErrorMsg(String missingItemType, String missingItemName) {
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JPanel mainErrorPanel = new JPanel();
        JPanel imagePanel = new JPanel();
        JPanel errorMsgPanel = new JPanel();

        mainErrorPanel.setLayout(new BoxLayout(mainErrorPanel, BoxLayout.Y_AXIS));
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
        errorMsgPanel.setLayout(new BoxLayout(errorMsgPanel, BoxLayout.X_AXIS));


        int midPanelHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
        int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int midPanelWidth = (screenWidth - mainPane.getDividerLocation()) / 2;


        // Setting Image Label & Panel
        ImageIcon imageIcon;
        JLabel imgLabel;
        imageIcon = new ImageIcon(MainMonitoringWindow.class.getResource("/images/noQueueFound.png"));
        imgLabel = new JLabel();
        imgLabel.setIcon(imageIcon);

        imagePanel.add(imgLabel, JLabel.CENTER);

        // Setting Message to Display
        String msgToDisplay = "<html><h1 style = \" text align:center ; "
                + "color : #FF0000 ;font-size:140% \" > "
                + "<b> Sorry ! No Message(s) Found for " + missingItemType + " : "
                + "<h1 style = \"text-align : center; "
                + "color : \t#800080;font-size:150%\">" + missingItemName + "</b></html>";
        JLabel errorLabel = new JLabel(msgToDisplay, JLabel.CENTER);
        errorMsgPanel.add(errorLabel, JLabel.CENTER);

        // Adding Components
        imagePanel.setPreferredSize(new Dimension(175, 150));
        imagePanel.setMaximumSize(new Dimension(175, 150));
        imagePanel.setMinimumSize(new Dimension(175, 150));
        errorMsgPanel.setPreferredSize(new Dimension(500, 150));
        errorMsgPanel.setMaximumSize(new Dimension(500, 150));
        errorMsgPanel.setMinimumSize(new Dimension(500, 150));


        imagePanel.setBackground(Color.WHITE);
        errorMsgPanel.setBackground(Color.WHITE);
        mainErrorPanel.add(Box.createVerticalStrut(midPanelHeight - 200));
        mainErrorPanel.add(imagePanel);
        mainErrorPanel.add(Box.createVerticalStrut(15));

        mainErrorPanel.add(errorMsgPanel);
        mainErrorPanel.setBackground(Color.WHITE);

        errorPanel.add(mainErrorPanel, BorderLayout.CENTER);
        errorPanel.setBackground(Color.WHITE);
        mainErrorPanel.updateUI();
        errorPanel.updateUI();
        return errorPanel;


    }

    private void disablePopUpMenuItemWidgetButton(boolean isEmptyQueue) {
        sendMessagesButton.setEnabled(isEmptyQueue);
        receiveMessagesButton.setEnabled(false);
        deleteMessagesButton.setEnabled(false);

        sendMsgItem.setEnabled(isEmptyQueue);
        rcvMsgMenuItem.setEnabled(false);
        deleteAllMessagesFromQueueItem.setEnabled(false);
    }

    private void enablePopUpMenuItemWidgetButton() {
        sendMessagesButton.setEnabled(true);
        receiveMessagesButton.setEnabled(true);
        deleteMessagesButton.setEnabled(true);
        refreshMessagesButton.setEnabled(true);
        sendMsgItem.setEnabled(true);
        rcvMsgMenuItem.setEnabled(true);
        refreshQueueMsgMenuItem.setEnabled(true);
        deleteAllMessagesFromQueueItem.setEnabled(true);
    }

    private void updateStatusMessagePanel(boolean isFirstTime) {
        if (isFirstTime) {
            databaseValLabel.setText("Oracle");
            schemaValLabel.setText(connectedSchema);
            hostValLabel.setText(userCredentials.get("host"));
            portValLabel.setText(userCredentials.get("port"));
            loggedInValLabel.setText(userCredentials.get("logintime"));
        }

        queueValLabel.setText(currentlySelectedQueue);
        if (currentlySelectedQueue.equals("N/A")) {
            lastRefreshValLabel.setText(MiscUtility.parseTime(QueueData.getAllQueuesLastAccessTime(), false));
            queueStatusValLabel.setText(MessageStatusEnum.LOADED.toString());
        } else {
            lastRefreshValLabel.setText(MiscUtility.parseTime(queueRecords.get(currentlySelectedQueue).getQueueLastAccessTime(), false));
            queueStatusValLabel.setText(queueRecords.get(currentlySelectedQueue).getQueueStatus().toString());
        }

    }

    private void addWidgetHoverActions() {
        MiscUtility.changeButtonLook(deleteMessagesButton, "deleteQueueBig.png", "deleteQueueSmall.png");
        MiscUtility.changeButtonLook(receiveMessagesButton, "rcvMsgBig.png", "rcvMsgSmall.png");
        MiscUtility.changeButtonLook(sendMessagesButton, "sendMsgBig.png", "sendMsgSmall.png");
        MiscUtility.changeButtonLook(refreshMessagesButton, "refreshQueueBig.png", "refreshQueueSmall.png");
        MiscUtility.changeButtonLook(deleteQueueButton, "deleteQueueBig.png", "deleteQueueSmall.png");
        MiscUtility.changeButtonLook(createQueueButton, "createNewQueueBig.png", "createNewQueueSmall.png");
        MiscUtility.changeButtonLook(refreshQueueButton, "refreshQueueBig.png", "refreshQueueSmall.png");
        MiscUtility.changeButtonLook(purgeQueueBtn, "emptyQueueBig.png", "emptyQueueSmall.png");
    }

    private List<TreeNode> getAllQueueNodes() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        allAvailableQueueNodes = DatabaseConnectivity.getQueueNames(connectedSchema);
        for (String queueNode : allAvailableQueueNodes) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(queueNode);
            QueueData queue = new QueueData();
            queue.setQueueName(queueNode);
            queue.setQueueStatus(MessageStatusEnum.LOADED);
            queueRecords.put(queueNode, queue);
            treeNodes.add(childNode);
        }
        QueueData.setAllQueuesLastAccessTime(System.currentTimeMillis());
        return treeNodes;
    }

    private void refreshQueueTree(boolean isFirstime) {
        queueRecords.clear();
        List<TreeNode> treeNodes = getAllQueueNodes();
        TreeModel model = queueTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        for (TreeNode eachNode : treeNodes) {
            root.add((DefaultMutableTreeNode) eachNode);
        }
        if (isFirstime) {
            root.setUserObject(connectedSchema);
            model = new DefaultTreeModel(root);
            queueTree.setModel(model);
        }
        queueTree.expandRow(0);
        queueTree.setSelectionPath(new TreePath(((DefaultMutableTreeNode) model.getRoot()).getPath()));

        queueTree.updateUI();
        currentlySelectedQueue = "N/A";
        updateStatusMessagePanel(false);
    }

    private void refreshMessageTable() {
        List<String> queueNodes = DatabaseConnectivity.getQueueNames(connectedSchema);
        // Checks whether the selected queue still exist in physical database.
        if (queueNodes.contains(currentlySelectedQueue)) {
            QueueData queueData = queueRecords.get(currentlySelectedQueue);
            Object[][] allMessageDetails = getQueueTableRecords();
            ((DefaultTableModel) messageTable.getModel()).setDataVector(allMessageDetails, columnNames);
            queueData.setQueueLastAccessTime(System.currentTimeMillis());
            queueData.setTableData(allMessageDetails);
            // Queue Depth is already set via getQueueTableRecords
            int queueDepth = queueData.getQueueDepth();
            if (queueDepth > 0) {
                queueData.setQueueStatus(MessageStatusEnum.ACTIVE);
                String titleToSet = String.format("Message View , Queue Depth : %s", queueDepth);
                MiscUtility.addTitledBorderToPanel(tablePanel, titleToSet);
                MiscUtility.addCellHeaderRender(messageTable);
                centralMainPanel.removeAll();
                centralMainPanel.add(tablePanel, BorderLayout.CENTER);
                centralMainPanel.updateUI();
                enablePopUpMenuItemWidgetButton();


            } else { // If Selected Queue has No Messages
                queueData.setQueueStatus(MessageStatusEnum.EMPTY);
                centralMainPanel.removeAll();
                centralMainPanel.add(displayErrorMsg("Queue", currentlySelectedQueue), BorderLayout.CENTER);
                centralMainPanel.updateUI();
                disablePopUpMenuItemWidgetButton(true);
            }
            updateStatusMessagePanel(false);
        } else { // When the queue is deleted from database, we also delete it from our view 
            String msgToDisplay = String.format("Sorry this Queue : %s is no more in physical Database !", currentlySelectedQueue);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue No more Exist", JOptionPane.ERROR_MESSAGE);
            refreshQueueTree(false);
        }

    }

    private Object[][] getQueueTableRecords() {
        queueConnectivity = new QueueConnectivity();
        allMessages = queueConnectivity.browseQueue(connectedSchema, currentlySelectedQueue);
        int queueDepth = allMessages.size();

        Object[][] allMessageDetails = new Object[allMessages.size()][];
        int i = 0;
        for (Message msg : allMessages) {
            List<Object> aMsg = new ArrayList<Object>();
            if (msg instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) msg;
                try {
                    aMsg.add(txtMsg.getJMSMessageID());
                    aMsg.add(txtMsg.getText());
                    aMsg.add(txtMsg.getJMSType());
                    aMsg.add(MiscUtility.parseTime(txtMsg.getJMSTimestamp(), true));
                    allMessageDetails[i++] = aMsg.toArray();
                } catch (JMSException ex) {
                    logger.error("JMSException Occured {}", ex);
                }
            } else if (msg instanceof BytesMessage) {
                BytesMessage byteMsg = (BytesMessage) msg;
                try {
                    aMsg.add(byteMsg.getJMSMessageID());

                    aMsg.add("Binary Message.Cannot be displayed");
                    aMsg.add(byteMsg.getJMSType());
                    aMsg.add(MiscUtility.parseTime(byteMsg.getJMSTimestamp(), true));
                    allMessageDetails[i++] = aMsg.toArray();
                } catch (JMSException ex) {
                    logger.error("JMSException Occured {}", ex);
                }
            } else if (msg instanceof StreamMessage) {
                StreamMessage streamMsg = (StreamMessage) msg;
                try {
                    aMsg.add(streamMsg.getJMSMessageID());
                    aMsg.add("Stream Message.Cannot be displayed");
                    aMsg.add(streamMsg.getJMSType());
                    aMsg.add(MiscUtility.parseTime(streamMsg.getJMSTimestamp(), true));
                    allMessageDetails[i++] = aMsg.toArray();
                } catch (JMSException ex) {
                    logger.error("JMSException Occured {}", ex);
                }
            } else if (msg instanceof ObjectMessage) {
                ObjectMessage objectMsg = (ObjectMessage) msg;
                try {
                    aMsg.add(objectMsg.getJMSMessageID());
                    aMsg.add("Object Message.Cannot be displayed");
                    aMsg.add(objectMsg.getJMSType());
                    aMsg.add(MiscUtility.parseTime(objectMsg.getJMSTimestamp(), true));
                    allMessageDetails[i++] = aMsg.toArray();
                } catch (JMSException ex) {
                    logger.error("JMSException Occured {}", ex);
                }
            } else if (msg instanceof MapMessage) {
                MapMessage mapMsg = (MapMessage) msg;
                try {
                    aMsg.add(mapMsg.getJMSMessageID());
                    aMsg.add("Map Message.Cannot be displayed");
                    aMsg.add(mapMsg.getJMSType());
                    aMsg.add(MiscUtility.parseTime(mapMsg.getJMSTimestamp(), true));
                    allMessageDetails[i++] = aMsg.toArray();
                } catch (JMSException ex) {
                    logger.error("JMSException Occured {}", ex);
                }
            }

        }
        if (queueRecords.containsKey(currentlySelectedQueue)) {
            queueRecords.get(currentlySelectedQueue).setQueueDepth(queueDepth);
        }
        return allMessageDetails;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centralMainPanel;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JButton createQueueButton;
    private javax.swing.JLabel databaseNameLabel;
    private javax.swing.JLabel databaseValLabel;
    private javax.swing.JButton deleteMessagesButton;
    private javax.swing.JButton deleteQueueButton;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JLabel hostValLabel;
    private javax.swing.JLabel lastRefreshLabel;
    private javax.swing.JLabel lastRefreshValLabel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel loggedInLabel;
    private javax.swing.JLabel loggedInValLabel;
    private javax.swing.JSplitPane mainPane;
    private javax.swing.JTable messageTable;
    private javax.swing.JPanel messageWidgetQueueToolbarPanel;
    private javax.swing.JPanel messageWidgetSchemaToolbarPanel;
    private javax.swing.JLabel portLabel;
    private javax.swing.JLabel portValLabel;
    private javax.swing.JButton purgeQueueBtn;
    private javax.swing.JLabel queueLabel;
    private javax.swing.JLabel queueStatusLabel;
    private javax.swing.JLabel queueStatusValLabel;
    private javax.swing.JTree queueTree;
    private javax.swing.JLabel queueValLabel;
    private javax.swing.JButton receiveMessagesButton;
    private javax.swing.JButton refreshMessagesButton;
    private javax.swing.JButton refreshQueueButton;
    private javax.swing.JLabel schemaLabel;
    private javax.swing.JLabel schemaValLabel;
    private javax.swing.JButton sendMessagesButton;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JPanel widgetCardPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * method that fires action event when menu item is clicked or a button is
     * clicked
     *
     * @param e action event object
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            if (e.getActionCommand().equals("createQueueAction")) { // Schema Level
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        createQueueDialog = new CreateQueueDialog(MainMonitoringWindow.this,
                                connectedSchema);
                        refreshQueueTree(false);
                    }
                });

            } else if (e.getActionCommand().equals("deleteAllQueuesAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        deleteQueueDialog = new DeleteQueueDialog(MainMonitoringWindow.this, allAvailableQueueNodes,
                                currentlySelectedQueue);
                        refreshQueueTree(false);
                    }
                });

            } else if (e.getActionCommand().equals("refreshQueueListAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        refreshQueueTree(false);
                    }
                });

            } else if (e.getActionCommand().equals("sendMessageAction")) { // Queue Node Level
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        sendMessageDialog = new SendMessageDialog(
                                MainMonitoringWindow.this, currentlySelectedQueue,
                                connectedSchema);
                        refreshMessageTable();
                    }
                });

            } else if (e.getActionCommand().equals("receiveMessageAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        receiveDeleteMessageDialog = new ReceiveDeleteMessageDialog(
                                MainMonitoringWindow.this, currentlySelectedQueue,
                                connectedSchema, true,
                                queueRecords.get(currentlySelectedQueue));
                        refreshMessageTable();
                    }
                });

            } else if (e.getActionCommand().equals("refreshQueueTableAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        refreshMessageTable();
                    }
                });
            } else if (e.getActionCommand().equals("deleteQueueAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        deleteSelectedQueue();
                        refreshQueueTree(false);
                    }
                });

            } else if (e.getActionCommand().equals("deleteAllQueueMessagesAction")) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        deleteAllMessages();
                        refreshMessageTable();
                    }
                });

            }
        }
    }

    private void deleteAllMessages() {
        // Checking of physical exsitence of Selected Queue
        List<String> queueNodes = DatabaseConnectivity.getQueueNames(connectedSchema);
        if (queueNodes.contains(currentlySelectedQueue)) { // Queue Still Exist
            String msgToDisplay = String.format("All messages from the Queue:"
                    + " %s are going to be Deleted Permanently and Queue will "
                    + "be Empty\n Are You Sure ?", currentlySelectedQueue);
            int option = JOptionPane.showConfirmDialog(this, msgToDisplay,
                    "Delete All Messages From Queue", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    int queueDepth = queueConnectivity.getQueueDepth(
                            connectedSchema, currentlySelectedQueue);
                    queueConnectivity.receiveMessages(connectedSchema,
                            currentlySelectedQueue, queueDepth).size();
                    JOptionPane.showMessageDialog(this,
                            String.format("All Messages from Queue : %s are Deleted Successfully",
                            currentlySelectedQueue), "All Messages Deleted Successfully",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (JMSException ex) {
                    msgToDisplay = String.format(
                            "Unable to Delete Messages from Queue : %s\n\tWould you like to check Error Details ?",
                            currentlySelectedQueue);
                    option = JOptionPane.showConfirmDialog(this, msgToDisplay,
                            "Unable to Delete All Messages from Queue",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                    if (option == JOptionPane.OK_OPTION) {
                        ExceptionDetailsDialog.showExceptionsDetailsDialog(this, "JMS Exception",
                                String.format("Empty Queue for Queue : %s",
                                currentlySelectedQueue), ex);
                        logger.error("JMSException Occured ", ex);
                    }
                }
            }
        } else { // Queue No More Exist in DB
            String msgToDisplay = String.format("Sorry this Queue : %s is no more in physical Database !", currentlySelectedQueue);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue No more Exist", JOptionPane.ERROR_MESSAGE);
            refreshQueueTree(false);
        }

    }

    private void deleteSelectedQueue() {
        // Checking of physical exsitence of Selected Queue
        List<String> queueNodes = DatabaseConnectivity.getQueueNames(connectedSchema);
        if (queueNodes.contains(currentlySelectedQueue)) {
            String msgToDisplay = String.format("Queue: %s is going to be Deleted Permanently from Database"
                    + "\n Are You Sure ?", currentlySelectedQueue);
            int option = JOptionPane.showConfirmDialog(this, msgToDisplay,
                    "Queue Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    queueConnectivity.deleteQueue(currentlySelectedQueue, connectedSchema);
                    JOptionPane.showMessageDialog(this, String.format(
                            "Queue : %s is Deleted Successfully", currentlySelectedQueue),
                            "Queue Deletion Successful", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    msgToDisplay = String.format("Unable to Delete Queue : %s\n\tWould you like to check Error Details ?", currentlySelectedQueue);
                    option = JOptionPane.showConfirmDialog(this, msgToDisplay, "Queue Deletion Failure", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (option == JOptionPane.OK_OPTION) {
                        ExceptionDetailsDialog.showExceptionsDetailsDialog(this,
                                "SQL Exception", String.format("Queue Deletion for Queue : %s",
                                currentlySelectedQueue), ex);
                        logger.error("SQLException Occured ", ex);
                    }
                }
            }
        } else {  // Queue No More Exist in DB
            String msgToDisplay = String.format("Sorry this Queue : %s is no more in physical Database !", currentlySelectedQueue);
            JOptionPane.showMessageDialog(this, msgToDisplay, "Queue No more Exist", JOptionPane.ERROR_MESSAGE);
        }
    }
}
