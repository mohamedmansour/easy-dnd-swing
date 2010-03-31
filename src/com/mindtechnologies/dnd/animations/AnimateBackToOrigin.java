package com.mindtechnologies.dnd.animations;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 * Animate the object on the glass pane back to the original location. Used if
 * the drag and drop has failed.
 * 
 * @author Mohamed Mansour
 */
public class AnimateBackToOrigin implements ActionListener {
  private boolean isInitialized;
  private long startTime;
  private Point startPoint;
  private Point endPoint;
  private DragGlassPane glassPane;

  private static final double INITIAL_SPEED = 500.0;
  private static final double INITIAL_ACCELERATION = 5000.0;

  /**
   * Constructor. Animate dragged image on the glass pane.
   * 
   * @param glassPane
   *          The glass pane where the drag effects
   * @param startPoint
   *          Start point of the drag.
   * @param endPoint
   *          End point of the drag.
   */
  public AnimateBackToOrigin(DragGlassPane glassPane, Point startPoint,
                             Point endPoint) {
    this.glassPane = glassPane;
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    isInitialized = false;
  }

  public void actionPerformed(ActionEvent e) {

    // Initialize the start time of the animation.
    if (!isInitialized) {
      isInitialized = true;
      startTime = System.currentTimeMillis();
    }

    // Figure out the elapsed time from the start time.
    long elapsed = System.currentTimeMillis() - startTime;
    double time = elapsed / 1000.0;

    // The ratio in the x.
    double a = (endPoint.y - startPoint.y)
        / (double) (endPoint.x - startPoint.x);

    // The ratio in the y.
    double b = endPoint.y - a * endPoint.x;

    // Calculate the travel distance given time via this equation:
    //  d = 1/2at^2 + vt
    int travelX = (int) (INITIAL_ACCELERATION * time * time / 2.0 + INITIAL_SPEED
        * time);

    // Calculate the direction of the movement.
    if (startPoint.x > endPoint.x) {
      travelX = -travelX;
    }

    // Calculate the travel distance within the vertical direction.
    int travelY = (int) ((startPoint.x + travelX) * a + b);

    // Distance between the start and end points.
    int distanceX = Math.abs(startPoint.x - endPoint.x);

    // If the travel distance exceeds the expected distance, the animation
    // should stop since it ended.
    if (Math.abs(travelX) >= distanceX) {
      ((Timer) e.getSource()).stop();

      glassPane.setPoint(endPoint);
      glassPane.repaint(glassPane.getRepaintRect());

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          glassPane.setImage(null);
          glassPane.setVisible(false);
        }
      });

      return;
    }

    // Repaint the region.
    glassPane.setPoint(new Point(startPoint.x + travelX, travelY));
    glassPane.repaint(glassPane.getRepaintRect());
  }
}
