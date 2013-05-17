/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Customized Renderer for a JTable
 * @author Sumit Roy
 */
public class TableRenderer extends DefaultTableCellRenderer {
    
    /**
     * Customized table cell renderer
     * @param table associated the rendering
     * @param value cell value of table
     * @param sel selection status : true/false
     * @param hasFocus checks whether the cell has focus or not
     * @param row row index of the cell
     * @param col column index of the cell
     * @return render component
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean sel, boolean hasFocus, int row, int col) {
        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, sel, hasFocus, row, col);
        Color backColor = new Color(238, 238, 224);
        label.setForeground(new Color(0, 0, 0));

        if (row % 2 == 0) {
            label.setBackground(backColor);
        } else {
            label.setBackground(Color.white);
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);

        if (value instanceof Boolean) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setSelected((Boolean) value);

            if (row % 2 == 0) {
                checkBox.setBackground(backColor);
            } else {
                checkBox.setBackground(Color.white);
            }

            return checkBox;


        } else {
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }
        return this;
    }
}
