package Breakout;

import utilities.GDV5;
import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends Rectangle {
    private int dx = 5;
    private int dy = -5;
    private Color color;
    private static int lives = 99999;


    public Ball (int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void ballLogic(Brick[][] bricks, ArrayList<Powerups> powerups, Paddle paddle, ArrayList<Particles> particles) {
        this.move();
        this.bounce(bricks, powerups, paddle, particles);
    }

    public void setDx(int x) {
        if (this.dx > 0) {
            dx = x;
        } else {
            dx = -x;
        }
    }
    public void setDy(int x) {
        if (this.dy > 0) {
            dy = x;
        } else {
            dy = -x;
        }
    }
    public static void setLives(int lives) {
        Ball.lives = lives;
    }
    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public static int getLives() {
        return lives;
    }
    public void bounce(Brick[][] bricks, ArrayList<Powerups> powerups, Paddle paddle, ArrayList<Particles> particles) {
        if (this.x <= 0 || this.x >= 800) {
            Misc.playSound("bop.wav");
            dx = -dx;
        }
        if (this.y <= 0) {
            Misc.playSound("bop.wav");
            dy = -dy;
        }
        for (Brick[] brickRow : bricks) {
            for (Brick brick : brickRow) {
                if (brick != null) {
                    if (this.intersects(brick)) {
                        Misc.playSound("bop.wav");
                        switch (GDV5.collisionDirection(brick, this, dx, dy)) {
                            case 0, 2 -> { // if horizontal
                                dx = -dx;
                                brick.destroyed(powerups, brick.x, brick.y);
                                Particles.spawnParticles(brick, particles);
                                brick.remove(bricks);

                            }
                            case 1, 3 -> { // if vertical
                                dy = -dy;
                                brick.destroyed(powerups, brick.x, brick.y);
                                Particles.spawnParticles(brick, particles);
                                brick.remove(bricks);
                            }
                        }
                    }
                }
            }
        }
        if (this.intersects(paddle)) {
            Misc.playSound("bop.wav");
            dy = -dy;
        }
        if (this.y >= 900) {
            lives--;
            this.x = 400;
            this.y = 400;
            dy = -dy;
        }
    }


    public void draw(Graphics2D pb) {
        pb.setColor(color);
        pb.fillOval(this.x,this.y, this.width, this.height);
    }
}
