package Snake;

import utilities.GDV5;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameRunner extends GDV5 {

    private static final int UNIT_SIZE = 50;
    private static final int UNITS = (getMaxWindowX() * getMaxWindowY()) / (UNIT_SIZE);
    private int bodyParts = 6;
    private int applesEaten;

    public GameRunner() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D win) {
        win.setColor(Color.RED);
        for (int i = 0; i < getMaxWindowY()/UNIT_SIZE; i++) {
            win.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, getMaxWindowY());
            win.drawLine(0, i * UNIT_SIZE, getMaxWindowX(), i * UNIT_SIZE);
        }
    }

    public static void main(String[] args) {
        GameRunner snake = new GameRunner();
        snake.start();
    }
}
