package org.fishing;

import static java.lang.Thread.sleep;
import static org.fishing.GameState.FISH_BATTLE_1_RESULT;
import static org.fishing.GameState.FISH_BATTLE_1_START;
import static org.fishing.GameState.FISH_BATTLE_2_RESULT;
import static org.fishing.GameState.FISH_BATTLE_2_START;
import static org.fishing.GameState.FISH_BATTLE_3_RESULT;
import static org.fishing.GameState.FISH_BATTLE_3_START;
import static org.fishing.GameState.FISH_BATTLE_LOOSE;
import static org.fishing.GameState.FISH_BATTLE_PRIZES;
import static org.fishing.GameState.MAIN_MENU;
import static org.fishing.GameState.UNDEFINED;
import static org.fishing.services.ObjectType.BUTTON_BATTLE_LEAVE;
import static org.fishing.services.ObjectType.BUTTON_CATCH;
import static org.fishing.services.ObjectType.BUTTON_CATCH_FROM_MAIN;
import static org.fishing.services.ObjectType.BUTTON_DUEL;
import static org.fishing.services.ObjectType.BUTTON_GRAB;
import static org.fishing.services.ObjectType.BUTTON_OK;
import static org.fishing.services.ObjectType.BUTTON_PICKUP;
import static org.fishing.services.ObjectType.BUTTON_ROD_REEL;
import static org.fishing.services.ObjectType.ICON_CHOOSE_CARD;
import static org.fishing.services.ObjectType.ICON_CLEAR_BAR;
import static org.fishing.services.ObjectType.ICON_CLEAR_BAR_2;
import static org.fishing.services.ObjectType.ICON_DUEL_DRAW;
import static org.fishing.services.ObjectType.ICON_DUEL_LOOSE;
import static org.fishing.services.ObjectType.ICON_DUEL_WIN;
import static org.fishing.services.ObjectType.ICON_FIRST_PLACE;
import static org.fishing.services.ObjectType.ICON_ROD_THREAD;
import static org.fishing.services.ObjectType.ICON_ROD_THREAD_2;
import static org.fishing.services.ObjectType.ICON_SCORE_PANEL;
import static org.fishing.services.ObjectType.ICON_SECOND_PLACE;
import static org.fishing.services.ObjectType.ICON_SECOND_ROUND;
import static org.fishing.services.ObjectType.ICON_THIRD_PLACE;
import static org.fishing.services.ObjectType.ICON_THIRD_ROUND;
import static org.fishing.services.ObjectType.RECEIVE_VIZOV_BUTTON;

import org.fishing.services.MouseService;
import org.fishing.services.ObjectsMapper;
import org.fishing.services.WindowService;
import org.opencv.core.Core;

@SuppressWarnings("BusyWait")
public class Main {
  static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

  private static final MouseService mouse = new MouseService();
  private static final WindowService window = new WindowService();
  private static final ObjectsMapper objects = new ObjectsMapper();

  static GameState state = UNDEFINED;
  static int totalFight = 0;
  static int winningFight = 0;
  static int looseFight = 0;
  static int winFirstPlace = 0;
  static int winSecondPlace = 0;
  static int winThirdPlace = 0;
  static int looseFirstRound = 0;
  static int looseSecondRound = 0;

  public static void main(String[] args) throws InterruptedException {

    window.findAndPrintWindowSize("BlueStacks App Player 1");

    //while (true) {
    //  window.updateWindow();
    //  window.saveRoiAsPng(new Rect(15, 292, 110, 23));
    //  Thread.sleep(30);
    //}

    //startRandomDuel();
    //startFishPlace();
    //startFishingBattle();
  }

  private static void startRandomDuel() throws InterruptedException {
    window.updateWindow();
    sleep(2000);

    while(true) {
      // Нажимаем на поединок
      mouse.leftClick(880, 374);

      // Ждем выбора карты
      while (window.awaitImage(objects.get(ICON_CHOOSE_CARD)))
        sleep(25);

      // Выбираем все время среднюю
      sleep(2000);
      mouse.leftClick(1177, 232);
      sleep(1000);

      // Ловим рыбу 1 раза
      catchFishInFishingBattle();
      sleep(2000);

      // Меняем карту на вторую
      mouse.leftClick(1412, 298);
      sleep(1000);

      // Ловим рыбу 1 раза
      catchFishInFishingBattle();
      sleep(2000);

      // Определяем конец боя
      int leave = 0;
      while(true) {
        if (leave == 0 && window.whileImage(objects.get(ICON_SCORE_PANEL))) {
          if (window.whileImage(objects.get(BUTTON_BATTLE_LEAVE))) {
            sleep(1000);
            leave = 1;
            mouse.leftClick(1172, 49);
            sleep(1000);
            mouse.leftClick(1249, 291);
            sleep(1000);
          }
        }

        if (window.whileImage(objects.get(ICON_DUEL_LOOSE))) {
          sleep(3000);
          mouse.leftClick(1454, 378);
          sleep(3000);
          break;
        }

        if (window.whileImage(objects.get(ICON_DUEL_DRAW))) {
          sleep(3000);
          mouse.leftClick(1454, 378);
          sleep(3000);
          break;
        }

        if (window.whileImage(objects.get(ICON_DUEL_WIN))) {
          sleep(3000);
          mouse.leftClick(1454, 378);
          sleep(3000);
          mouse.leftClick(1454, 378);
          sleep(3000);
          mouse.leftClick(1402, 337);
          sleep(3000);
          break;
        }

        sleep(50);
      }
    }
  }

  private static void startFishPlace() throws InterruptedException {
    while(true) {
      catchFishInPlace();
    }
  }

  private static void startFishingBattle() throws InterruptedException {
    window.updateWindow();
    sleep(2000);

    while(true) {
      if (state == FISH_BATTLE_LOOSE) {
        looseFight++;
        sleep(2000);
        mouse.leftClick(1115, 379);
        sleep(2000);
        state = UNDEFINED;
      }

      if (state == FISH_BATTLE_PRIZES) {
        state = UNDEFINED;
        winningFight++;

        while (true) {
          if (window.whileImage(objects.get(ICON_FIRST_PLACE))) {
            winFirstPlace++;
            sleep(2000);
            mouse.leftClick(1410, 378);
            sleep(4000);
            mouse.leftClick(1410, 378);
            sleep(4000);
            break;
          }

          if (window.whileImage(objects.get(ICON_SECOND_PLACE))) {
            winSecondPlace++;
            sleep(1000);
            mouse.leftClick(1115, 379);
            break;
          }

          if (window.whileImage(objects.get(ICON_THIRD_PLACE))) {
            winThirdPlace++;
            sleep(1000);
            mouse.leftClick(1115, 379);
            break;
          }
          sleep(25);
        }
      }

      if (state == FISH_BATTLE_3_RESULT) {
        state = FISH_BATTLE_PRIZES;
        while (window.awaitImage(objects.get(BUTTON_GRAB))) {
          if (window.whileImage(objects.get(ICON_FIRST_PLACE))) {
            break;
          }
          sleep(25);
        }
      }

      if (state == FISH_BATTLE_3_START) {
        // Ловим рыбу
        catchFishInFishingBattle();
        state = FISH_BATTLE_3_RESULT;
      }

      if (state == FISH_BATTLE_2_RESULT) {
        state = FISH_BATTLE_PRIZES;
        while (window.awaitImage(objects.get(BUTTON_GRAB))) {
          if (window.whileImage(objects.get(BUTTON_OK))) {
            looseSecondRound++;
            state = FISH_BATTLE_LOOSE;
            break;
          }
          else if (window.whileImage(objects.get(ICON_THIRD_ROUND))) {
            state = FISH_BATTLE_3_START;
            break;
          }
          else if (window.whileImage(objects.get(ICON_FIRST_PLACE))) {
            break;
          }
          sleep(25);
        }
      }

      if (state == FISH_BATTLE_2_START) {
        // Ловим рыбу
        catchFishInFishingBattle();
        catchFishInFishingBattle();
        state = FISH_BATTLE_2_RESULT;
      }

      if (state == FISH_BATTLE_1_RESULT) {
        state = FISH_BATTLE_2_START;
        while (window.awaitImage(objects.get(ICON_SECOND_ROUND))) {
          if (window.whileImage(objects.get(BUTTON_OK))) {
            looseFirstRound++;
            state = FISH_BATTLE_LOOSE;
            break;
          }

          if (window.whileImage(objects.get(ICON_FIRST_PLACE))) {
            winFirstPlace++;
            state = FISH_BATTLE_PRIZES;
            break;
          }

          if (window.whileImage(objects.get(ICON_SECOND_PLACE))) {
            winSecondPlace++;
            state = FISH_BATTLE_PRIZES;
            break;
          }

          if (window.whileImage(objects.get(ICON_THIRD_PLACE))) {
            winThirdPlace++;
            state = FISH_BATTLE_PRIZES;
            break;
          }

          sleep(25);
        }
      }

      if (state == FISH_BATTLE_1_START) {
        // Ловим рыбу
        catchFishInFishingBattle();
        state = FISH_BATTLE_1_RESULT;
      }

      if (state == MAIN_MENU) {
        // Нажимаем начать сосятазине рыбаков
        totalFight++;
        mouse.leftClick(987, 374);
        sleep(1000);
        state = FISH_BATTLE_1_START;
      }

      if (state == UNDEFINED) {
        sleep(3000);

        if (window.whileImage(objects.get(RECEIVE_VIZOV_BUTTON))) {
          mouse.leftClick(1009, 65);
          sleep(4000);
          mouse.leftClick(1380, 377);
          sleep(4000);
          mouse.leftClick(1421, 377);
          sleep(4000);
          mouse.leftClick(1386, 374);
          sleep(4000);
          mouse.leftClick(1179, 368);
          sleep(4000);
        }

        for (int i = 0; i < 10; i++) {
          System.out.println();
        }
        System.out.println("Всего поединков: " + totalFight);
        double winPercent = totalFight > 0 ? ((double)winningFight / (double)totalFight) * 100 : 0;
        System.out.println("Победных: " + winningFight + " (" + winPercent + "%)");
        double loosePercent = totalFight > 0 ? ((double)looseFight / (double)totalFight) * 100 : 0;
        System.out.println("Проиграл: " + looseFight + " (" + loosePercent + "%)");

        double looseFirstRoundPercent = looseFight > 0 ? ((double)looseFirstRound / (double)looseFight) * 100 : 0;
        System.out.println(" - в первом раунде: " + looseFirstRound + " (" + looseFirstRoundPercent + "%)");
        double looseSecondRoundPercent = looseFight > 0 ? ((double)looseSecondRound / (double)looseFight) * 100 : 0;
        System.out.println(" - во втором раунде: " + looseSecondRound + " (" + looseSecondRoundPercent + "%)");


        System.out.println("1е места: " + winFirstPlace);
        System.out.println("2е места: " + winSecondPlace);
        System.out.println("3е места: " + winThirdPlace);

        while (window.awaitImage(objects.get(BUTTON_DUEL))) {

          if (window.whileImage(objects.get(BUTTON_PICKUP))) {
            mouse.leftClick(983, 373);
            sleep(300);
          }
          sleep(25);
        }
        state = MAIN_MENU;
      }
      sleep(25);
    }
  }



  private static void catchFishInFishingBattle() throws InterruptedException {
    // Ожидаем появление кнопки забросить
    while (window.awaitImage(objects.get(BUTTON_CATCH)))
      sleep(25);

    // Нажимаем на забросить
    sleep(1000);
    mouse.leftClick(1426, 379);

    // Ожидаем появление котушки
    while (window.awaitImage(objects.get(BUTTON_ROD_REEL)))
      sleep(25);

    // Подсекаем рыбу
    mouse.leftClick(1426, 321);
    sleep(300);

    // Цикл пока идет ловля рыбы
    while (window.whileImage(objects.get(ICON_ROD_THREAD))) {
      if (window.whileImage(objects.get(ICON_CLEAR_BAR)))
        mouse.leftClick(1426, 321, 150);
      sleep(25);
    }

    // Ожидаем кнопку получить рыбу
    while (window.awaitImage(objects.get(BUTTON_PICKUP)))
      sleep(25);

    // Забираем пойманую рыбу
    mouse.leftClick(983, 373);
    sleep(300);
  }

  private static void catchFishInPlace() throws InterruptedException {
    window.updateWindow();

    // Ожидаем появление кнопки забросить
    while (window.awaitImage(objects.get(BUTTON_CATCH_FROM_MAIN)))
      sleep(25);

    // Нажимаем на забросить
    sleep(500);
    mouse.leftClick(1426, 379);

    // Ожидаем появление котушки
    while (window.awaitImage(objects.get(BUTTON_ROD_REEL)))
      sleep(25);

    // Подсекаем рыбу
    mouse.leftClick(1426, 321);
    sleep(300);

    // Цикл пока идет ловля рыбы
    while (window.whileImage(objects.get(ICON_ROD_THREAD_2))) {
      if (window.whileImage(objects.get(ICON_CLEAR_BAR_2)))
        mouse.leftClick(1426, 321, 150);
      sleep(25);
    }

    // Ожидаем кнопку получить рыбу
    while (window.awaitImage(objects.get(BUTTON_PICKUP)))
      sleep(25);

    // Забираем пойманую рыбу
    mouse.leftClick(983, 373);
    sleep(300);
  }


}