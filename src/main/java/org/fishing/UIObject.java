package org.fishing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.fishing.services.ObjectType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class UIObject {

  private ObjectType key;
  private Mat objectMat;
  private Rect coordinates;
  private double correlation;

}
