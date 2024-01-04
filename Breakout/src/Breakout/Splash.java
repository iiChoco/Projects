package Breakout;

import utilities.GDV5;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Splash {
    private static String state = "";
    private static boolean newGame = false;

    public static void setState(ArrayList<Ball> balls, Paddle paddle) {
        boolean enter = GDV5.KeysPressed[VK_ENTER];
        if (enter && state.equals("start")) {
            state = "game";
        }
        if (enter && state.equals("lose")) {
            newGame = true;
            reset(balls, paddle);
            state = "game";
        }
    }
    public static void reset(ArrayList<Ball> balls, Paddle paddle) {
        balls.subList(1, balls.size()).clear();
        Ball.setLives(3);
        paddle.setSize(100, 20);
        for (Ball ball : balls) ball.setSize(20, 20);
        GameRunner.setLevel(1);
        GameRunner.setRows(4);
        GameRunner.setScore(0);
        GameRunner.setTimer(0);
    }

    public static boolean getNewGame() {
        return newGame;
    }
    public static void setNewGame(boolean newGame) {
        Splash.newGame = newGame;
    }


    public static void deathDetection() {
        if (Ball.getLives() == 0) {
            state = "lose";
        }
    }
    public static void setState(String s) {
        state = s;
    }

    public static String getState() {
        return state;
    }

}
