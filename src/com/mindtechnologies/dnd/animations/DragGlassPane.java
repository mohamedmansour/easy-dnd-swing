package com.mindtechnologies.dnd.animations;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * The drag glass pane where the drag effects happens such as dragged images.
 * 
 * @author Mohamed Mansour
 */
public class DragGlassPane extends JPanel {

  private static final long serialVersionUID = 1L;
  private BufferedImage dragged = null;
  private Point location = new Point(0, 0);
  private Point oldLocation = new Point(0, 0);
  private int width = 0;
  private int height = 0;
  private float zoom = 1.0f;
  private float alpha = 0.7f;

  /**
   * Constructor.
   */
  public DragGlassPane() {
    setOpaque(false);
  }

  /**
   * The image to be drawn.
   * 
   * @param dragged
   *          The dragged image.
   */
  public void setImage(BufferedImage dragged) {
    if (dragged != null) {
      width = dragged.getWidth();
      height = dragged.getHeight();
    }
    this.dragged = dragged;
  }

  /**
   * Set the new location of the dragged image.
   * 
   * @param location
   */
  public void setPoint(Point location) {
    this.oldLocation = this.location;
    this.location = location;
  }

  /**
   * @return the zoom
   */
  public float getZoom() {
    return zoom;
  }

  /**
   * @param zoom
   *          the zoom to set
   */
  public void setZoom(float zoom) {
    this.zoom = zoom;
  }

  /**
   * @return the alpha
   */
  public float getAlpha() {
    return alpha;
  }

  /**
   * @param alpha
   *          the alpha to set
   */
  public void setAlpha(float alpha) {
    if (alpha > 1.0f) {
      alpha = 1.0f;
    }
    this.alpha = alpha;
  }

  /**
   * Retrieve the repaint regions, for performance, just repaint what has been
   * changed. We could even repaint a polygon, but that is too much computations
   * this is quick and easy.
   * 
   * @return The rectangle to be painted.
   */
  public Rectangle getRepaintRect() {
    int width = (int) (this.width * getZoom());
    int height = (int) (this.height * getZoom());
    int x = (int) location.getX();
    int y = (int) location.getY();
    int x2 = (int) oldLocation.getX();
    int y2 = (int) oldLocation.getY();
    return new Rectangle(x, y, width, height)
        .union(new Rectangle(x2, y2, width, height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (dragged == null || !isVisible()) {
      return;
    }

    Graphics2D g2 = (Graphics2D) g;

    // Add transparency for the drag.
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                               getAlpha()));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    // Repaint the image.
    int x = (int) location.getX();
    int y = (int) location.getY();
    int width = (int) (this.width * getZoom());
    int height = (int) (this.height * getZoom());
    g2.drawImage(dragged, x, y, width, height, null);
  }
}
