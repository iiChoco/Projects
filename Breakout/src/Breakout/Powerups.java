package Breakout;

import java.awt.*;
import java.util.ArrayList;


public class Powerups extends Rectangle {
    private final String type;
    private final Color[] colors = {Color.blue, Color.white, Color.magenta, Color.black, Color.cyan, Color.orange};
    private final Color color;
    private final int width = 50;
    private final int height = 50;
    private final int dy = 5;
    private final int count = 0;




    public Powerups(int x, int y, int width, int height, String type, Color color) {
        super(x, y, width, height);
        this.type = type;
        this.color = color;
    }
    public void powerupLogic(ArrayList<Powerups> powerups, ArrayList<Ball> balls, Paddle paddle) {
        this.move();
        this.intersection(balls, paddle);
        this.deletion(paddle, powerups);
    }
    public void intersection(ArrayList<Ball> balls, Paddle paddle) {
        boolean intersect = this.intersects(paddle);
        if (intersect) {
            switch (type) {
                case "speed up":
                    for (Ball ball : balls) {
                        ball.setDx(8);
                        ball.setDy(8);
                    }
                    break;
                case "slower":
                    for (Ball ball : balls) {
                        ball.setDx(3);
                        ball.setDy(3);
                    }
                    break;
                case "longer":
                    System.out.print(paddle.getWidth() + "->");
                    paddle.grow(100,0);
                    System.out.print(paddle.getWidth());
                    break;
                case "shorter":
                    paddle.grow(-100, 0);
                    break;
                case "balls":
                    balls.add(new Ball(400, 400, 20, 20, Color.white));
                    break;
                case "ball bigger":
                    for (Ball ball : balls) {
                        ball.grow(10, 10);
                    }
                    break;
                case "ball smaller":
                    for (Ball ball : balls) {
                        ball.grow(-10, -10);
                    }
                    break;
            }
        }
    }

    public void deletion(Paddle paddle, ArrayList<Powerups> powerups) {
        for (Powerups powerup : powerups) {
            if (powerup != null) {
                if (powerup.y > 900) {
                    powerups.set(powerups.indexOf(powerup), null);
                }
                if (powerup.intersects(paddle)) {
                    powerups.set(powerups.indexOf(powerup), null);
                }
            }
        }
    }
    public static void spawnPowerup(ArrayList<Powerups> powerups, int x, int y, String type, Color color) {
        powerups.add(new Powerups(x, y, 50, 50, type, color));
    }
    public void move() {
        this.y += dy;
    }

    public void draw(Graphics2D pb) {
        pb.setColor(color);
        pb.fill(this);

    }

}
