package com.mindtechnologies.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;

/**
 * Generic Transferrable that contains the Source Component and the data Object
 * ready for transport.
 * 
 * @author Mohamed Mansour
 */
public class GenericTransferable implements Transferable {
  private Object item;
  private JComponent source;

  public static DataFlavor ITEM_FLAVOR = null;
  public static DataFlavor SOURCE_COMPONENT_FLAVOR = new DataFlavor(
                                                                    DataFlavor.javaJVMLocalObjectMimeType,
                                                                    "SourceComponent");

  public GenericTransferable(Object item, JComponent source) {
    ITEM_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, item
        .getClass().getCanonicalName());
    this.item = item;
    this.source = source;
  }

  public Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (flavor == ITEM_FLAVOR) {
      return item;
    } else if (flavor == SOURCE_COMPONENT_FLAVOR) {
      return source;
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
  }

  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {
      ITEM_FLAVOR,
      SOURCE_COMPONENT_FLAVOR };
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return ITEM_FLAVOR.equals(flavor);
  }

  /**
   * @return contents of the data being dragged.
   */
  public Object getItem() {
    return item;
  }

  /**
   * The source the data been dragged from.
   * 
   * @return
   */
  public JComponent getSource() {
    return source;
  }
}
