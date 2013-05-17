/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuebrowser.utility;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.slf4j.LoggerFactory;

/**
 * Customized Renderer for JTree
 *
 * @author Sumit Roy
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

    /**
     * Customize Tree Node renderer
     * @param tree JTree component associated with this rendering operation
     * @param value value of the tree node
     * @param sel selection status : true/false
     * @param expanded expanded status : true/false
     * @param leaf checks whether it is a leaf node or not
     * @param row row depth of the tree node
     * @param hasFocus focus status of the tree node
     * @return render component
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TreeRenderer.class);

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node == null) {
            return comp;
        }
        Image image;
        if (node.getLevel() == 0) {
            try {
                image = ImageIO.read(this.getClass().getResource("/images/queue.png"));
                setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                logger.error("IOException Occured {}", ex);
            }
        }
        if (node.getLevel() == 1) {
            try {
                image = ImageIO.read(this.getClass().getResource("/images/msgBlue.png"));
                setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                logger.error("IOException Occured {}", ex);
            }
        }
        return comp;
    }
}
