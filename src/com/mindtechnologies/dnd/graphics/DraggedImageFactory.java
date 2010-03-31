package com.mindtechnologies.dnd.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JList;

/**
 * Dragged Image factory that retrieves dragged image data from a given
 * component.
 * 
 * @author Mohamed Mansour
 */
public class DraggedImageFactory {

  /**
   * Retrieve dragged image data from a component.
   * 
   * @param component
   *          The component to parse out dragged image data.
   * @return
   */
  public static DraggedImage getDraggedImage(JComponent component,
                                             boolean visible) {
    if (!visible) {
      return new DraggedImage() {
        public boolean isDraggable() {
          return false;
        }

        public BufferedImage getImage() {
          return null;
        }

        public Rectangle getBounds() {
          return null;
        }
      };
    }
    if (component instanceof JList) {
      return new DraggedImageJListImpl((JList) component);
    } else {
      return new DraggedImageJComponentImpl(component);
    }
  }
}
