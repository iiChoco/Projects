package Breakout;

import java.awt.*;
import java.util.ArrayList;

public class Particles extends Rectangle{
    private int dx;
    private int dy;
    private static final int parts = 10;
    private static final int rows = 5;
    public Particles(int x, int y, int width, int height) {
        super(x, y, width, height);
    }



    public void particlesLogic(Particles particle, ArrayList<Particles> particles) {
        move(particle);
        deletion(particles);
    }

    public void move(Particles particle) {
        if (Math.random() < .5) {
            dx = 2;
        } else dx = -2;
        dy = 1;
        if (particle != null) {
            this.x += dx;
            this.y += dy;
        }
    }

    public void deletion(ArrayList<Particles> particle) {
        if (GameRunner.getTimer() % 90 == 0) {
            for (Particles particles : particle) {
                if (particles != null) {
                    particle.set(particle.indexOf(particles), null);
                }
            }
        }
    }

    public static void spawnParticles(Brick brick, ArrayList<Particles> particles) {
        int x = brick.x;
        int y = brick.y;
        int width = brick.width;
        int height = brick.height;
        int n = 1;
        for (int i = 0; i < 90; i++) {
            particles.add(new Particles(x, y, width / parts, height / rows));
            x += width / parts;
            if (n % parts == 0) {
                x = brick.x;
                y += height / rows;
            }
            n++;
        }
    }

    public static void drawParticles(Graphics2D pb, ArrayList<Particles> particles) {
        for (Particles particle : particles) {
            if (particle != null) {
                particle.draw(pb);
            }
        }
    }

    public void draw(Graphics2D pb) {
        pb.setColor(Color.DARK_GRAY);
        pb.fill(this);
    }

}
