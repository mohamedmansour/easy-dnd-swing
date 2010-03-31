package com.mindtechnologies.dnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.mindtechnologies.dnd.listeners.DragListener;
import com.mindtechnologies.dnd.listeners.DropListener;

/**
 * An example class that drags couple of simple components.
 * 
 * @author Mohamed Mohamed
 */
public class SimpleDnD extends JFrame {
  private static final long serialVersionUID = 1L;

  /**
   * @param args
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        SimpleDnD frame = new SimpleDnD();
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);
      }
    });
  }

  private JButton btnFoo;
  private JCheckBox chkFoo;
  private JLabel lblFoo;
  private JTextField txtFoo;
  private JTextArea txtDropArea;
  private JPanel pnlControlArea;
  
  public SimpleDnD() {
    initComponents();
    initDnD();
  }

  private void initComponents() {
    setLayout(new BorderLayout());
    
    pnlControlArea = new JPanel();
    pnlControlArea.setBackground(Color.WHITE);
    pnlControlArea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    add(pnlControlArea, BorderLayout.NORTH);
    
    txtDropArea = new JTextArea();
    txtDropArea.setBackground(Color.WHITE);
    txtDropArea.setBorder(new LineBorder(Color.GRAY, 10, true));
    add(txtDropArea, BorderLayout.CENTER);
    
    btnFoo = new JButton("Foo");
    pnlControlArea.add(btnFoo);
    
    chkFoo = new JCheckBox("Foo");
    pnlControlArea.add(chkFoo);
    
    lblFoo = new JLabel("Foo");
    pnlControlArea.add(lblFoo);
    
    txtFoo = new JTextField("Foo");
    pnlControlArea.add(txtFoo);
  }
  

  private void initDnD() {
    // Drag Area.
    DefaultDrag dragA = new DefaultDrag(btnFoo, new DragListener() {
      @Override
      public Object getObject(DragGestureEvent de) {
        return btnFoo.getText() + " JButton";
      }
    });
    DefaultDrag dragB = new DefaultDrag(chkFoo, new DragListener() {
      @Override
      public Object getObject(DragGestureEvent de) {
        return chkFoo.getText() + " " + chkFoo.isSelected() + " JCheckBox";
      }
    });
    DefaultDrag dragC = new DefaultDrag(lblFoo, new DragListener() {
      @Override
      public Object getObject(DragGestureEvent de) {
        return lblFoo.getText() + " JLabel";
      }
    });
    DefaultDrag dragD = new DefaultDrag(txtFoo, new DragListener() {
      @Override
      public Object getObject(DragGestureEvent de) {
        return txtFoo.getText() + " JTextField";
      }
    });
    
    // Drop Area.
    DefaultDrop drop = new DefaultDrop(txtDropArea, new DropListener() {
      @Override
      public void performDrop(DropTargetDropEvent dtde, Object data) {
        txtDropArea.append(data + "\n");
      }
    });
    dragA.setEnabled(true);
    dragB.setEnabled(true);
    dragC.setEnabled(true);
    dragD.setEnabled(true);
    drop.setEnabled(true);
  }
}
