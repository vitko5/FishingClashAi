package org.fishing.services;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.fishing.UIObject;
import org.fishing.converters.BufferedImageToMatConverter;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class WindowService {
  static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

  private static final JFrame window = new JFrame();
  private static final JLabel screen = new JLabel();
  private static final Rectangle bounds = new Rectangle(852, 29, 1495, 370);
  private static final Robot robot;
  private static BufferedImage screenshot;
  private static Rect windowRect;


  static int fileIndex = 0;

  static {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
  }


  public WindowService() {
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().add(screen);
    window.pack();
    window.setSize(660, 400);
    window.setLocation(852, 410);
    window.setVisible(true);
  }

  public void updateWindow() {
    screenshot = robot.createScreenCapture(bounds);
    screen.setIcon(new ImageIcon(screenshot));
    window.repaint();
  }

  public void updateWindow(BufferedImage img) {
    screen.setIcon(new ImageIcon(img));
    window.repaint();
  }

  public boolean awaitImage(UIObject uiObject) {
    updateWindow();
    Mat scr = BufferedImageToMatConverter.convert(screenshot);
    Imgproc.rectangle(scr,
        new Point(uiObject.getCoordinates().x, uiObject.getCoordinates().y),
        new Point(uiObject.getCoordinates().x + uiObject.getCoordinates().width, uiObject.getCoordinates().y + uiObject.getCoordinates().height),
        new Scalar(0, 255, 0), 1);

    updateWindow(BufferedImageToMatConverter.convert(scr));

    Mat roiMat = new Mat(scr, uiObject.getCoordinates());
    Mat result = new Mat();
    Imgproc.matchTemplate(roiMat, uiObject.getObjectMat(), result, Imgproc.TM_CCOEFF_NORMED);
    MinMaxLocResult mmr = Core.minMaxLoc(result);
    double maxCorrelation = mmr.maxVal;
    return maxCorrelation < uiObject.getCorrelation();
  }

  public boolean whileImage(UIObject uiObject) {
    updateWindow();
    Mat scr = BufferedImageToMatConverter.convert(screenshot);
    Imgproc.rectangle(scr,
        new Point(uiObject.getCoordinates().x, uiObject.getCoordinates().y),
        new Point(uiObject.getCoordinates().x + uiObject.getCoordinates().width, uiObject.getCoordinates().y + uiObject.getCoordinates().height),
        new Scalar(0, 255, 0), 1);

    updateWindow(BufferedImageToMatConverter.convert(scr));

    Mat roiMat = new Mat(scr, uiObject.getCoordinates());
    Mat result = new Mat();
    Imgproc.matchTemplate(roiMat, uiObject.getObjectMat(), result, Imgproc.TM_CCOEFF_NORMED);
    MinMaxLocResult mmr = Core.minMaxLoc(result);
    double maxCorrelation = mmr.maxVal;
    if (uiObject.getKey().equals(ObjectType.RECEIVE_VIZOV_BUTTON))
      System.out.println("Correlation: " + maxCorrelation + ">=" + uiObject.getCorrelation());
    return maxCorrelation >= uiObject.getCorrelation();
  }

  public void findAndPrintWindowSize(String windowTitle) {
    HWND hwnd = User32.INSTANCE.FindWindow(null, windowTitle);
    if (hwnd != null) {
      RECT rect = new RECT();
      User32.INSTANCE.GetWindowRect(hwnd, rect);

      int width = rect.right - rect.left;
      int height = rect.bottom - rect.top;

      windowRect = new Rect(rect.left, rect.top, width, height);
      System.out.println("Window found: " + windowTitle);
      System.out.println("Window HWND: " + hwnd);
      System.out.println("Size: " + width + "x" + height);
      System.out.println("Coordinates: (" + rect.left + ", " + rect.top + ")");
    } else {
      System.out.println("Window not found: " + windowTitle);
    }
  }

  //saveRoiAsPng(screenshot, new Rect(1, 329, 103, 32), "c:\\test.png");
  public void saveRoiAsPng(Rect roi) {
    if (fileIndex >= 20) {

      Mat image = BufferedImageToMatConverter.convert(screenshot);
      Mat roiMat = new Mat(image, roi);
      MatOfByte matOfByte = new MatOfByte();

      Imgcodecs.imencode(".png", roiMat, matOfByte);
      byte[] byteArray = matOfByte.toArray();
      ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
      try {
        BufferedImage bufferedImage = ImageIO.read(bais);

        String filename = "c:\\screen_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".png";
        File outputFile = new File(filename);
        ImageIO.write(bufferedImage, "png", outputFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      fileIndex = 0;
    }
    else {
      fileIndex++;
    }
  }

}
