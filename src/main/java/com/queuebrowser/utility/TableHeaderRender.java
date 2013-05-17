/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queuebrowser.utility;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * Customized Table Header Renderer for JTable
 * @author Sumit Roy
 */


public class TableHeaderRender extends JLabel implements TableCellRenderer{
    /**
     * 
     * @param table associated the rendering
     * @param value cell value of table
     * @param isSelected checks whether the cell is selected or not
     * @param hasFocus  checks whether the cell has focus or not
     * @param row row index of the cell
     * @param column column index of the cell
     * @return render component
     */

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(Color.black);
        JLabel label=new JLabel((String) value,JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(getIcon(table, column));
        return label;
    }
    private Icon getIcon(JTable table, int column) {
    SortKey sortKey = getSortKey(table, column);
    if (sortKey != null && table.convertColumnIndexToView(sortKey.getColumn()) == column) {
      switch (sortKey.getSortOrder()) {
        case ASCENDING:
          return UIManager.getIcon("Table.ascendingSortIcon");
        case DESCENDING:
          return UIManager.getIcon("Table.descendingSortIcon");
      }
    }
    return null;
  }
    private SortKey getSortKey(JTable table, int column) {
    RowSorter rowSorter = table.getRowSorter();
    if (rowSorter == null) {
      return null;
    }

    List sortedColumns = rowSorter.getSortKeys();
    if (sortedColumns.size() > 0) {
      return (SortKey) sortedColumns.get(0);
    }
    return null;
  }
}
