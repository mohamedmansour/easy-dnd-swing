package com.mindtechnologies.dnd;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Dragging Status is used to ensure only one drag update could be done at any
 * given time. Since there is no multitouch environment we shouldn't offer the
 * ability to control other drags since we know the system will have at most one
 * cursor at any given time.
 * 
 * @author Mohamed Mansour
 * 
 */
public class DragStatus {
  private AtomicBoolean status = new AtomicBoolean(false);

  /**
   * Dragging is already running.
   * 
   * @return
   */
  public boolean isRunning() {
    return status.get();
  }

  /**
   * Set the dragging state.
   * 
   * @param isStarted
   */
  public void setRunning(boolean isStarted) {
    status.set(isStarted);
  }
}
