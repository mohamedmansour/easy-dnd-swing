package com.mindtechnologies.dnd.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The Image that is being dragged.
 * 
 * @author Mohamed Mansour
 */
public interface DraggedImage {
  /**
   * The image data that is being dragged while drag and dropping.
   * 
   * @return a buffered image.
   */
  public BufferedImage getImage();

  /**
   * The bounds where the
   * 
   * @return rectangular bound coordinates.
   */
  public Rectangle getBounds();

  /**
   * Check if an image is draggable.
   * 
   * @return true if image exists.
   */
  public boolean isDraggable();
}
