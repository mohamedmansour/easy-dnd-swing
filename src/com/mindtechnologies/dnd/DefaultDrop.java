package com.mindtechnologies.dnd;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JComponent;

import com.mindtechnologies.dnd.listeners.DropListener;

/**
 * Default dropping target for any drag.
 * 
 * @author Mohamed Mansour
 */
public class DefaultDrop extends DropTargetAdapter {

  protected DropTarget dropTarget;
  protected JComponent targetComponent;

  private DropListener listener = null;

  /**
   * Constructor for dropping.
   * 
   * @param target
   * @param listener
   */
  public DefaultDrop(JComponent target, DropListener listener) {
    this.targetComponent = target;
    this.listener = listener;
    this.dropTarget = new DropTarget(getComponent(), DnDConstants.ACTION_MOVE,
                                     this);
    setEnabled(false);
  }

  /**
   * Disable Drop.
   * 
   * @param enabled
   */
  public void setEnabled(boolean enabled) {
    dropTarget.setActive(enabled);
  }

  /**
   * Enable Drop.
   * 
   * @return
   */
  public boolean isEnabled() {
    return dropTarget.isActive();
  }

  /**
   * The component dropping to.
   * 
   * @return
   */
  public JComponent getComponent() {
    return targetComponent;
  }

  public final void drop(DropTargetDropEvent dtde) {
    if (!dtde.isDataFlavorSupported(GenericTransferable.ITEM_FLAVOR)) {
      dtde.rejectDrop();
      return;
    }

    dtde.acceptDrop(DnDConstants.ACTION_MOVE);

    try {
      Object data = dtde.getTransferable()
          .getTransferData(GenericTransferable.ITEM_FLAVOR);
      JComponent source = (JComponent) dtde.getTransferable()
          .getTransferData(GenericTransferable.SOURCE_COMPONENT_FLAVOR);

      if (listener.canDrop(source, getComponent(), data)) {
        listener.performDrop(dtde, data);
        dtde.dropComplete(true);
      } else {
        dtde.dropComplete(false);
      }
    } catch (Exception e) {
      dtde.dropComplete(false);
    }
  }

  @Override
  public final void dragOver(DropTargetDragEvent dtde) {
    try {
      JComponent source = (JComponent) dtde.getTransferable()
          .getTransferData(GenericTransferable.SOURCE_COMPONENT_FLAVOR);
      Object item = dtde.getTransferable()
          .getTransferData(GenericTransferable.ITEM_FLAVOR);

      if (listener.canDrop(source, getComponent(), item)) {
        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
      } else {
        dtde.rejectDrag();
      }
    } catch (Exception e) {
      dtde.rejectDrag();
    }
  }
}
