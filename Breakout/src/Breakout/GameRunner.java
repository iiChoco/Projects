package Breakout;

import utilities.GDV5;
import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;

import static Breakout.Splash.setState;

public class GameRunner extends GDV5 {

    // private Brick brick = new Brick(0, 0, 20, 10, Color.RED);
    private static int rows = 4;
    private final int columns = 8;
    private Brick[][] bricks;
    private final ArrayList<Ball> balls = new ArrayList<Ball>();
    private final Color[] colors = {Color.blue, Color.white, Color.magenta, Color.black, Color.cyan, Color.orange};
    private Color color = colors[(int) (Math.random() * colors.length)];
    private final int padding = 2;
    private final int BRICK_WIDTH = 100;
    private final int BRICK_HEIGHT = 20;
    private final Paddle paddle = new Paddle(400, 800, 100, 20);
    private final ArrayList<Powerups> powerups = new ArrayList<Powerups>();
    private final ArrayList<Particles> particles = new ArrayList<Particles>();
    private static int timer = 0;
    private static int level = 1;
    private static int score;

    public void count() {
        timer++;
    }

    public static void addScore(int x) {
        score += x;
    }

    public static void setRows(int rows) {
        GameRunner.rows = rows;
    }

    public static void setScore(int score) {
        GameRunner.score = score;
    }

    public static int getTimer() {
        return timer;
    }

    public static void setTimer(int timer) {
        GameRunner.timer = timer;
    }

    public GameRunner() {
        spawnBricks();
        balls.add(new Ball(400, 400, 20, 20, Color.white));
        setState("start");
    }

    public void spawnBricks() {
        bricks = new Brick[rows][columns];
        int x = 1, y = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bricks[i][j] = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT, color, false);
                x += BRICK_WIDTH + padding;
            }
            x = 1;
            colorChange();
            y += BRICK_HEIGHT + padding;
        }
    }

    public static void setLevel(int level) {
        GameRunner.level = level;
    }


    public void levelChange() {
        boolean allNull = false;
        for (Brick[] brickRow : bricks) {
            for (Brick brick : brickRow) {
                if (brick == null) {
                    allNull = true;
                } else {
                    allNull = false;
                    break;
                }
            }
        }
        if (allNull) {
            level++;
            rows++;
            spawnBricks();
        }
    }

    public void colorChange() {
        Color temp = colors[(int) (Math.random() * colors.length)];
        if (temp != color) {
            color = temp;
        } else {
            colorChange();
        }
    }

    public static void main(String[] args) {
        GameRunner breakout = new GameRunner();
        breakout.start();
        Misc.playSound("john.wav");
    }


    @Override
    public void update() {
        count();
        Splash.setState(balls, paddle);
        Splash.deathDetection();
        iHaveTooManyLinesInUpdate();
    }

    public void iHaveTooManyLinesInUpdate() {
        if (Splash.getState().equals("game")) {
            if (Splash.getNewGame()) {
                spawnBricks();
                Splash.setNewGame(false);
            }
            levelChange();
            for (Ball ball : balls) {
                ball.ballLogic(bricks, powerups, paddle, particles);
            }
            for (Powerups powerup : powerups) {
                if (powerup != null) {
                    powerup.powerupLogic(powerups, balls, paddle);
                }
            }
            for (Particles particle : particles) {
                if (particle != null) {
                    particle.particlesLogic(particle, particles);
                }
            }
            paddle.padleLogic();
        }
    }


    @Override
    public void draw(Graphics2D win) {
        if (Splash.getState().equals("start")) {
            win.setColor(Color.white);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
            FontMetrics metrics = win.getFontMetrics(win.getFont());
            win.drawString("Welcome to Breakout", ((GDV5.getMaxWindowX() - metrics.stringWidth("Welcome to Breakout")) / 2), 200);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            FontMetrics john = win.getFontMetrics(win.getFont());
            win.drawString("Created by Jeff Wang", ((GDV5.getMaxWindowX() - john.stringWidth("Created by Jeff Wang")) / 2), 250);
            win.drawString("Press enter to start!", ((GDV5.getMaxWindowX() - john.stringWidth("Press enter to start!")) / 2), 350);
            win.drawString("Use A and D to move the paddle!", ((GDV5.getMaxWindowX() - john.stringWidth("Use A and D to move the paddle!")) / 2), 400);
            win.drawString("Enjoy many powerups!", ((GDV5.getMaxWindowX() - john.stringWidth("Enjoy many powerups!")) / 2), 450);
            win.drawString("Hit bricks to brick (break haha) them!", ((GDV5.getMaxWindowX() - john.stringWidth("Hit bricks to brick (break haha) them!")) / 2), 500);
        }
        if (Splash.getState().equals("game")) {
            win.setColor(Color.gray);
            win.setFont(new Font("MONOSPACED", Font.BOLD, 92));
            FontMetrics orange = win.getFontMetrics(win.getFont());
            win.drawString("SCORE:" + score, (GDV5.getMaxWindowX() - orange.stringWidth("SCORE:" + score)) / 2, GDV5.getMaxWindowY() / 2);
            for (int i = 0; i < Math.min(rows, bricks.length); i++) {
                for (int j = 0; j < Math.min(columns, bricks[i].length); j++) {
                    if (bricks[i][j] != null) {
                        bricks[i][j].draw(win);
                    }
                }
            }
            for (Powerups powerup : powerups) {
                if (powerup != null) {
                    powerup.draw(win);
                }
            }
            Particles.drawParticles(win, particles);
            for (Ball ball : balls) {
                ball.draw(win);
            }
            paddle.draw(win);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            FontMetrics circle = win.getFontMetrics(win.getFont());
            win.drawString("Lives: " + Ball.getLives(), 50, 850);
            win.drawString("Level: " + level, 750 - circle.stringWidth("Level: " + level), 850);
        }
        if (Splash.getState().equals("lose")) {
            win.setColor(Color.white);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
            FontMetrics metrics = win.getFontMetrics(win.getFont());
            win.drawString("You lose!", ((GDV5.getMaxWindowX() - metrics.stringWidth("You lose!")) / 2), 200);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            FontMetrics john = win.getFontMetrics(win.getFont());
            win.drawString("Created by Jeff Wang", ((GDV5.getMaxWindowX() - john.stringWidth("Created by Jeff Wang")) / 2), 250);
            win.drawString("Press enter to play again!", ((GDV5.getMaxWindowX() - john.stringWidth("Press enter to play again!")) / 2), 350);
        }


    }
}
