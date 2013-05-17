/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains miscellaneous utility functions applicable to different
 * JComponent
 *
 * @author Sumit Roy
 */
public class MiscUtility {

    private static final Logger logger = LoggerFactory.getLogger(MiscUtility.class);
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private static String dateTimePattern = "dd-MMM-yyyy hh:mm:ss a";
    private static String timePattern = "hh:mm:ss a";
    private static String datePattern = "dd-MMM-yyyy";
    private static Calendar cal;

    /**
     * Sets a title to a JPanel with given text
     *
     * @param tagetPanel : JPanel where the title will be attached
     * @param newTitle : Title to be set
     */
    public static void addTitledBorderToPanel(JPanel tagetPanel, String newTitle) {
        tagetPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(204, 204, 204), 1, true), newTitle));
    }
    
    /**
     * Adds a Mouse wheel scroll action to a given combo box, when user scroll 
     * down , items below the list gets selected and in case of up scrolling items
     * above the list gets selected
     * @param comboBox target combo box for which the scroll action is being added
     */

    public static void addScrollAction(final JComboBox comboBox) {
        comboBox.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                int scrollType = e.getWheelRotation();
                if (scrollType > 0 && (comboBox.getSelectedIndex() < (comboBox.getItemCount() - 1))) { // DOWN SCROLLING
                    comboBox.setSelectedIndex(comboBox.getSelectedIndex() + 1);


                } else if (scrollType < 0) {   // UP SCROLLING
                    if (comboBox.getSelectedIndex() > 0) {
                        comboBox.setSelectedIndex(comboBox.getSelectedIndex() - 1);

                    }

                }


            }
        });


    }

    /**
     * Adds a widgets type look and feel to a JButton , when mouse will be on
     * the icon, icon will be grow, and mouse will out,icon will be shrink to
     * default size
     *
     * @param button JButton for which Hover effects will be added
     * @param hoverInIconName Typically a Big size icon ,that will be appear
     * when mouse will be on the JButton
     * @param hoverOutIconName Typically a small size /default icon ,that will
     * be appear when mouse will be leave the JButton
     */
    public static void changeButtonLook(final JButton button, final String hoverInIconName, final String hoverOutIconName) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    button.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/" + hoverInIconName))));
                } catch (IOException ex) {
                    logger.error("IOException Occured {}", ex);

                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    button.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/" + hoverOutIconName))));
                } catch (IOException ex) {
                    logger.error("IOException Occured {}", ex);
                }
            }
        });
    }

    /**
     * Format a long time to a specific format of SimpleDateFormat type
     *
     * @param timeStamp long milliseconds timestamp to be formatted
     * @param dateTime  flag that indicates how to format the date time,if it is
     * true given time will be formatted with both date and time, if dateTime is
     * false , given time will be formatted with time only.
     * @return formatted timestamp String
     */
    public static String parseTime(long timeStamp, boolean dateTime) {
        if (timeStamp < 0) {
            return "N/A";
        } else if (dateTime) {
            simpleDateFormat.applyPattern(dateTimePattern);
        } else {
            simpleDateFormat.applyPattern(timePattern);
        }
        return simpleDateFormat.format(new Date(timeStamp));
    }

    /**
     * This method add table cell header renderer to a JTable for better Table
     * Header representation
     *
     * @param messageTable JTable for which the table header will be added
     */
    public static void addCellHeaderRender(JTable messageTable) {
        TableColumnModel columnModel = messageTable.getColumnModel();
        TableHeaderRender headerRender = new TableHeaderRender();
        TableRenderer render = new TableRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setHeaderRenderer(headerRender);
            column.setCellRenderer(render);
        }
        messageTable.getTableHeader().setBackground(Color.WHITE);
    }

    /**
     * Adds an enter key action to a JText component like JTextbox or JTextArea 
     * when user hits enter key on his key board a JButton is being clicked/fired
     * @param textComponent targeted text component for which enter key action is to be bound
     * @param btnToClick targeted button which will be virtually click/fired when enter key is hit
     */
    public static void addEnterKeyAction(JTextComponent textComponent, final JButton btnToClick) {
        textComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnToClick.doClick();
                }
            }
        });

    }
}
