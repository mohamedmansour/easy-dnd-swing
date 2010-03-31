package com.mindtechnologies.dnd.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Properly defines the dragged image. Caches the image and bound coordinates so
 * that it wont recalculate it when needed.
 * 
 * @author Mohamed Mansour
 * @param <T>
 *          A Generic that is of any JComponent type.
 */
abstract class AbstractDraggedImage<T extends JComponent> implements
    DraggedImage {
  protected T component = null;
  protected BufferedImage image = null;
  protected Rectangle bounds = new Rectangle();

  /**
   * Process capturing the dragged Image.
   */
  protected abstract void processImage();

  /**
   * Process fetching image bounds for dragging.
   */
  protected abstract void processBounds();

  /**
   * Constructor.
   * 
   * @param component
   *          The JComponent being dragged.
   */
  public AbstractDraggedImage(T component) {
    setComponent(component);
    processBounds();
    processImage();
  }

  /**
   * Sets the component being dragged.
   * 
   * @param component
   *          The JComponent.
   */
  public void setComponent(T component) {
    this.component = component;
  }

  /**
   * Return the Component being dragged.
   * 
   * @return Must be of type JComponent.
   */
  public T getComponent() {
    return this.component;
  }

  public BufferedImage getImage() {
    return this.image;
  }

  public Rectangle getBounds() {
    return this.bounds;
  }

  public boolean isDraggable() {
    return getImage() != null;
  }

}
