package com.mindtechnologies.dnd.listeners;

import java.awt.dnd.DragGestureEvent;

/**
 * Dragged Listener that retrieves the object that is going to be dragged.
 * 
 * @author Mohamed Mansour
 */
public abstract class DragListener {
  /**
   * Contents of the dragged object.
   * 
   * @param de
   * @return
   */
  public abstract Object getObject(DragGestureEvent de);
}
