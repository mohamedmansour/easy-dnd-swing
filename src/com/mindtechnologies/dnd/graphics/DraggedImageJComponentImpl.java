package com.mindtechnologies.dnd.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Any JComponent could be draggable. This will take the bounds of the component
 * and treat it as a draggable item.
 * 
 * @author Mohamed Mansour
 */
class DraggedImageJComponentImpl extends AbstractDraggedImage<JComponent> {

  /**
   * Constructor.
   * 
   * @param component
   *          Can be any JComponent.
   */
  public DraggedImageJComponentImpl(JComponent component) {
    super(component);
  }

  @Override
  protected void processBounds() {
    bounds = getComponent().getBounds();
  }

  @Override
  protected void processImage() {
    Rectangle bounds = getComponent().getBounds();
    image = new BufferedImage((int) bounds.getWidth(),
                              (int) bounds.getHeight(),
                              java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics2D graphics = image.createGraphics();
    getComponent().paint(graphics);
    graphics.dispose();
  }
}
