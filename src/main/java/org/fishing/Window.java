package org.fishing;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.opencv.core.Core;

public class Window extends JFrame {
  static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    System.out.println("Version: " + Core.VERSION);
  };

  private final Robot robot;

  Window() throws AWTException {
    JFrame window = new JFrame();
    JLabel screen = new JLabel();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);

    Rectangle bounds = new Rectangle(768, 36, 1495, 445);
    this.robot = new Robot();

    BufferedImage screenshot = robot.createScreenCapture(bounds);

  }

}
