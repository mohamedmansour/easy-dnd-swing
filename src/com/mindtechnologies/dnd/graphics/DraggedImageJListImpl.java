package com.mindtechnologies.dnd.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JList;

/**
 * Produces a draggable list item from a JList. This gets the selected item from
 * the list, and retrieves the cell that would be draggable and produces the
 * draggable data.
 * 
 * @author Mohamed Mansour
 */
class DraggedImageJListImpl extends AbstractDraggedImage<JList> {

  /**
   * Constructor.
   * 
   * @param component
   *          the JList that need to be processed.
   */
  public DraggedImageJListImpl(JList component) {
    super(component);
  }

  @Override
  protected void processBounds() {
    int selected_index = getComponent().getSelectedIndex();
    if (selected_index != -1) {
      bounds = getComponent().getCellBounds(selected_index, selected_index);
    }
  }

  @Override
  protected void processImage() {
    int selected_index = getComponent().getSelectedIndex();
    if (selected_index != -1) {
      Rectangle ibounds = getComponent().getBounds();
      Rectangle cbounds = getComponent().getCellBounds(selected_index,
                                                       selected_index);
      image = new BufferedImage((int) ibounds.getWidth(), (int) ibounds
          .getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
      Graphics2D graphics = image.createGraphics();
      getComponent().paint(graphics);
      graphics.dispose();
      try {
        image = image.getSubimage(cbounds.x, cbounds.y, cbounds.width,
                                  cbounds.height);
      } catch (Exception ex) {
        System.out.println("Exception!!!!!! " + ex.getMessage());
        ex.printStackTrace();
      }
    }
  }
}
