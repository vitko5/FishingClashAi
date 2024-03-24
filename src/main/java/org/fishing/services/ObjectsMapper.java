package org.fishing.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.fishing.UIObject;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

@Getter
public class ObjectsMapper {

  private final Set<UIObject> map = new HashSet<>();

  public ObjectsMapper() {
    map.addAll(
        List.of(
            new UIObject()
              .withKey(ObjectType.BUTTON_DUEL)
              .withObjectMat(Imgcodecs.imread("c:\\btn_duel.png"))
              .withCoordinates(new Rect(1, 329, 103, 32))
              .withCorrelation(0.86),
            new UIObject()
                .withKey(ObjectType.BUTTON_CATCH)
                .withObjectMat(Imgcodecs.imread("c:\\btn_catch.png"))
                .withCoordinates(new Rect(538, 337, 80, 25))
                .withCorrelation(0.83),
            new UIObject()
                .withKey(ObjectType.BUTTON_CATCH_FROM_MAIN)
                .withObjectMat(Imgcodecs.imread("c:\\btn_catch_place.png"))
                .withCoordinates(new Rect(538, 337, 80, 25))
                .withCorrelation(0.83),
            new UIObject()
                .withKey(ObjectType.BUTTON_OK)
                .withObjectMat(Imgcodecs.imread("c:\\btn_ok.png"))
                .withCoordinates(new Rect(220, 337, 90, 25))
                .withCorrelation(0.80),
            new UIObject()
                .withKey(ObjectType.BUTTON_GRAB)
                .withObjectMat(Imgcodecs.imread("c:\\btn_grab.png"))
                .withCoordinates(new Rect(180, 335, 130, 28))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.BUTTON_ROD_REEL)
                .withObjectMat(Imgcodecs.imread("c:\\btn_wheel.png"))
                .withCoordinates(new Rect(530, 260, 80, 80))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.BUTTON_PICKUP)
                .withObjectMat(Imgcodecs.imread("c:\\btn_pickup.png"))
                .withCoordinates(new Rect(84, 332, 100, 25))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_ROD_THREAD)
                .withObjectMat(Imgcodecs.imread("c:\\icon_thread.png"))
                .withCoordinates(new Rect(175, 25, 33, 33))
                .withCorrelation(0.40),
            new UIObject()
                .withKey(ObjectType.ICON_ROD_THREAD_2)
                .withObjectMat(Imgcodecs.imread("c:\\icon_thread2.png"))
                .withCoordinates(new Rect(162, 29, 33, 30))
                .withCorrelation(0.40),
            new UIObject()
                .withKey(ObjectType.ICON_CLEAR_BAR)
                .withObjectMat(Imgcodecs.imread("c:\\icon_clear_bar.png"))
                .withCoordinates(new Rect(300, 34, 5, 13))
                .withCorrelation(0.07),
            new UIObject()
                .withKey(ObjectType.ICON_CLEAR_BAR_2)
                .withObjectMat(Imgcodecs.imread("c:\\icon_clear_bar.png"))
                .withCoordinates(new Rect(300, 39, 5, 13))
                .withCorrelation(0.09),
            new UIObject()
                .withKey(ObjectType.ICON_FIRST_PLACE)
                .withObjectMat(Imgcodecs.imread("c:\\icon_place_first.png"))
                .withCoordinates(new Rect(292, 50, 52, 40))
                .withCorrelation(0.94),
            new UIObject()
                .withKey(ObjectType.ICON_SECOND_PLACE)
                .withObjectMat(Imgcodecs.imread("c:\\icon_place_second.png"))
                .withCoordinates(new Rect(292, 50, 52, 40))
                .withCorrelation(0.94),
            new UIObject()
                .withKey(ObjectType.ICON_THIRD_PLACE)
                .withObjectMat(Imgcodecs.imread("c:\\icon_place_third.png"))
                .withCoordinates(new Rect(292, 50, 52, 40))
                .withCorrelation(0.94),
            new UIObject()
                .withKey(ObjectType.ICON_SECOND_ROUND)
                .withObjectMat(Imgcodecs.imread("c:\\icon_second_round.png"))
                .withCoordinates(new Rect(257, 50, 130, 40))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_THIRD_ROUND)
                .withObjectMat(Imgcodecs.imread("c:\\icon_third_round.png"))
                .withCoordinates(new Rect(257, 50, 130, 40))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_CHOOSE_CARD)
                .withObjectMat(Imgcodecs.imread("c:\\icon_choose_card.png"))
                .withCoordinates(new Rect(200, 22, 250, 30))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_DUEL_LOOSE)
                .withObjectMat(Imgcodecs.imread("c:\\duel_loose.png"))
                .withCoordinates(new Rect(230, 10, 150, 50))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_DUEL_WIN)
                .withObjectMat(Imgcodecs.imread("c:\\duel_win.png"))
                .withCoordinates(new Rect(230, 10, 150, 50))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_DUEL_DRAW)
                .withObjectMat(Imgcodecs.imread("c:\\duel_draw.png"))
                .withCoordinates(new Rect(230, 10, 150, 50))
                .withCorrelation(0.90),
            new UIObject()
                .withKey(ObjectType.ICON_SCORE_PANEL)
                .withObjectMat(Imgcodecs.imread("c:\\score_panel.png"))
                .withCoordinates(new Rect(467, 17, 20, 20))
                .withCorrelation(0.70),
            new UIObject()
                .withKey(ObjectType.BUTTON_BATTLE_LEAVE)
                .withObjectMat(Imgcodecs.imread("c:\\battle_leave.png"))
                .withCoordinates(new Rect(280, 9, 80, 22))
                .withCorrelation(0.80),
            new UIObject()
                .withKey(ObjectType.RECEIVE_VIZOV_BUTTON)
                .withObjectMat(Imgcodecs.imread("c:\\button_vizov.png"))
                .withCoordinates(new Rect(140, 13, 50, 34))
                .withCorrelation(0.80)
            )
    );
  }

  public UIObject get(ObjectType objectType) {
    return map.stream()
        .filter(o -> o.getKey().equals(objectType))
        .findAny()
        .orElseThrow();
  }

}
