/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

/**
 * This class keeps tracks of Check boxes that are added to a JList
 * @author Sumit Roy
 */
public class CheckBoxListItem {

    private String label;
    private boolean isSelected;

    /**
     * Default Constructor for CheckBox List Item with Empty Label and selection status to false
     */
    public CheckBoxListItem() {
        label = "";
        isSelected = false;
    }

    /**
     * Overload Constructor for CheckBox List Item with custom Label and selection status to false
     * @param label Name of the Item 
     */
    public CheckBoxListItem(String label) {
        this.label = label;
        isSelected = false;
    }

    /**
     * Overload Constructor for CheckBox List Item with custom Label and selection status to custom boolean value
     * @param label Name of the Item 
     * @param isSelected status whether the check box will be selected initially
     */
    public CheckBoxListItem(String label, boolean isSelected) {
        this.label = "";
        this.isSelected = isSelected;
    }

    /**
     * Overriden toString() method that returns name of the item
     * @return name of the item
     */
    @Override
    public String toString() {
        return label;
    }
    
    
    /**
     * Tells the status of the check box whether selected/deselected
     * @return selection status of check box whether selected/deselected
     */
    public boolean isSelected() {
        return isSelected;
    }
    
    /**
     * Setter method to select check box accordingly
     * @param isSelected select the check box according to isSelected value
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    
}
