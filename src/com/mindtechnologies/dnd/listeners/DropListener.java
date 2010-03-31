package com.mindtechnologies.dnd.listeners;

import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JComponent;

/**
 * Drop listener when an item is about to be dropped into the location. This can
 * be mainly used to decide whether the location being dropped is a valid
 * location.
 * 
 * @author Mohamed Mansour
 */
public abstract class DropListener {

  /**
   * Can drop the object to the given target.
   * 
   * @param source
   * @param target
   * @param data
   * @return
   */
  public boolean canDrop(JComponent source, JComponent target, Object data) {
    return true;
  }

  /**
   * Perform the drop.
   * 
   * @param dtde
   * @param data
   */
  public abstract void performDrop(DropTargetDropEvent dtde, Object data);
}
