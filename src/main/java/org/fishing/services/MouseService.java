package org.fishing.services;

import static java.lang.Thread.sleep;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import lombok.SneakyThrows;

public class MouseService {

  static Robot robot;

  static {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
  }

  @SneakyThrows
  public void leftClick(int x, int y, int delay) {
    robot.mouseMove(x, y);
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    sleep(delay);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }

  @SneakyThrows
  public void leftClick(int x, int y) {
    robot.mouseMove(x, y);
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    sleep(50);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }

}
