package org.fishing.converters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BufferedImageToMatConverter {
  public static Mat convert(BufferedImage bi) {
    Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
    int[] data = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
    byte[] newData = new byte[data.length * 3]; // Умножаем на 3, так как каждый пиксель имеет 3 канала (BGR)
    for (int i = 0; i < data.length; i++) {
      int pixel = data[i];
      newData[i * 3] = (byte) (pixel & 0xFF); // Blue
      newData[i * 3 + 1] = (byte) ((pixel >> 8) & 0xFF); // Green
      newData[i * 3 + 2] = (byte) ((pixel >> 16) & 0xFF); // Red
    }
    mat.put(0, 0, newData);
    return mat;
  }

  public static BufferedImage convert(Mat mat) {
    Mat temp = new Mat();
    Imgproc.cvtColor(mat, temp, Imgproc.COLOR_BGR2RGB);
    BufferedImage bi = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
    byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
    mat.get(0, 0, data);
    return bi;
  }

  static Mat convertGray(BufferedImage image) {
    Mat mat;
    Raster raster = image.getData();
    DataBuffer dataBuffer = raster.getDataBuffer();

    if (dataBuffer instanceof DataBufferByte) {
      byte[] data = ((DataBufferByte) dataBuffer).getData();
      mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC1);
      mat.put(0, 0, data);
    } else if (dataBuffer instanceof DataBufferUShort) {
      short[] data = ((DataBufferUShort) dataBuffer).getData();
      mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_16UC1);
      mat.put(0, 0, data);
    } else if (dataBuffer instanceof DataBufferInt) {
      int[] data = ((DataBufferInt) dataBuffer).getData();
      mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_32SC1);
      mat.put(0, 0, data);
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }

    return mat;
  }
}
