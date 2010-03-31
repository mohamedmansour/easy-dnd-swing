package com.mindtechnologies.dnd;

import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;

import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.mindtechnologies.dnd.animations.AnimateBackToOrigin;
import com.mindtechnologies.dnd.animations.DragGlassPane;
import com.mindtechnologies.dnd.graphics.DraggedImage;
import com.mindtechnologies.dnd.graphics.DraggedImageFactory;
import com.mindtechnologies.dnd.listeners.DragListener;

/**
 * Default drag source that defines the source of the drag.
 * 
 * @author Mohamed Mansour
 */
public class DefaultDrag extends DragSourceAdapter implements
    DragGestureListener {

  protected DragSource dragSource;
  protected JComponent sourceComponent;

  private DraggedImage draggedImage = null;
  private DragListener dragListener = null;
  private DragStatus dragLock = null;
  private boolean dragVisible = true;
  private boolean enabled = false;

  /**
   * Constructor.
   * 
   * @param source
   *          - The component we are dragging from.
   * @param listener
   *          - The dragging listener.
   */
  public DefaultDrag(JComponent source, DragListener listener) {
    this.sourceComponent = source;
    this.dragListener = listener;
    this.dragSource = DragSource.getDefaultDragSource();
    this.dragLock = new DragStatus();
  }

  /**
   * Get the zoom factor for dragged Image.
   * 
   * @return the factor where 1.0f is the normality.
   */
  public float getZoomFactor() {
    return getDragPane().getZoom();
  }

  /**
   * Set the zoom factor for dragged Image.
   * 
   * @param factor
   *          The factor where 1.0f is the normality.
   */
  public void setZoomFactor(float factor) {
    getDragPane().setZoom(factor);
  }

  public float getTransparency() {
    return getDragPane().getAlpha();
  }

  public void setTransparency(float alpha) {
    getDragPane().setAlpha(alpha);
  }

  /**
   * Disable Drag.
   * 
   * @param enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    if (enabled) {
      dragSource.createDefaultDragGestureRecognizer(getComponent(),
                                                    DnDConstants.ACTION_MOVE,
                                                    this);
      dragSource.addDragSourceMotionListener(this);
    } else {
      dragSource.removeDragSourceListener(this);
      dragSource.removeDragSourceMotionListener(this);
    }
  }

  /**
   * Enable Drag.
   * 
   * @return
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the showDragImage
   */
  public boolean isDragVisible() {
    return dragVisible;
  }

  /**
   * @param showDragImage
   *          the showDragImage to set
   */
  public void setDragVisible(boolean visible) {
    this.dragVisible = visible;
  }

  /**
   * The component that is being dragged.
   * 
   * @return
   */
  public JComponent getComponent() {
    return sourceComponent;
  }

  /**
   * The image data of the item being dragged.
   * 
   * @return
   */
  public DraggedImage getImageData() {
    return draggedImage;
  }

  /**
   * Retrieve the dragging glass pane.
   * 
   * @return The glass pane.
   */
  private DragGlassPane getDragPane() {
    // Check if glass pane already exists in the root pane, if not, add it.
    // It is for allowing dragging effects.
    JRootPane rootPane = SwingUtilities.getRootPane(getComponent());
    if (!(rootPane.getGlassPane() instanceof DragGlassPane)) {
      rootPane.setGlassPane(new DragGlassPane());
    }
    return (DragGlassPane) rootPane.getGlassPane();
  }

  public final void dragGestureRecognized(DragGestureEvent dge) {
    dragLock.setRunning(true);

    // Fetch the grabbed image.
    Object obj = dragListener.getObject(dge);

    // Must contain something or do not initiate the drag.
    if (obj == null) {
      return;
    }

    dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop,
                         new GenericTransferable(obj, getComponent()), this);

    draggedImage = DraggedImageFactory.getDraggedImage(getComponent(),
                                                       isDragVisible());
    if (!getImageData().isDraggable()) {
      return;
    }

    DragGlassPane glassPane = getDragPane();
    glassPane.setVisible(true);
    Point p = (Point) dge.getDragOrigin().clone();
    SwingUtilities.convertPointToScreen(p, getComponent());
    SwingUtilities.convertPointFromScreen(p, glassPane);
    glassPane.setPoint(p);
    glassPane.setImage(getImageData().getImage());
    glassPane.repaint();
  }

  @Override
  public final void dragMouseMoved(DragSourceDragEvent dsde) {
    DraggedImage draggedImage = getImageData();

    if (!dragLock.isRunning() || draggedImage == null
        || !draggedImage.isDraggable()) {
      return;
    }
    DragGlassPane glassPane = getDragPane();
    Point p = (Point) dsde.getLocation().clone();
    SwingUtilities.convertPointFromScreen(p, glassPane);
    glassPane.setPoint(p);
    glassPane.repaint(glassPane.getRepaintRect());
  }

  @Override
  public final void dragDropEnd(DragSourceDropEvent dsde) {
    dragLock.setRunning(false);

    if (!getImageData().isDraggable()) {
      return;
    }

    DragGlassPane glassPane = getDragPane();
    Point p = (Point) dsde.getLocation().clone();
    SwingUtilities.convertPointFromScreen(p, glassPane);
    if (!dsde.getDropSuccess()) {
      Point end = (Point) getImageData().getBounds().getLocation().clone();
      SwingUtilities.convertPointToScreen(end, getComponent());
      SwingUtilities.convertPointFromScreen(end, glassPane);
      Timer backTimer = new Timer(1000 / 60, new AnimateBackToOrigin(glassPane,
                                                                     p, end));
      backTimer.start();
    } else {
      glassPane.setImage(null);
      glassPane.setPoint(p);
      glassPane.repaint(glassPane.getRepaintRect());
    }

  }

  @Override
  public final void dragEnter(DragSourceDragEvent dsde) {
    dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
  }

  @Override
  public final void dragExit(DragSourceEvent dse) {
    dse.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
  }
}
