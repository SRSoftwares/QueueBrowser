/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Renderer for Check Box List being added to a JList
 *
 * @author Sumit Roy
 */
public class CheckBoxListRenderer extends JCheckBox implements ListCellRenderer {
/**
 *  Getter method for JList Items with Check box
 * @param list the list for which the renderer applies to
 * @param value value of JList item
 * @param index index of JList item
 * @param isSelected selection status of list check box item
 * @param cellHasFocus status that states whether the cell is focused or not
 * @return customized renderer for list check box item
 */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((CheckBoxListItem) value).isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}
