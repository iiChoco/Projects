package Breakout;

import utilities.GDV5;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {
    private final Color[] colors = {Color.blue, Color.white, Color.magenta, Color.cyan, Color.orange};
    private Color color;
    private final int dx = 5;
    int r, g, b = 0;
    int rr = 1;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void padleLogic() {
        this.move();
        this.setColor();
        this.RGB();
    }

    public void setColor() {

    }

    public void addWidth(int x) {
        this.width += x;
    }

    public void move() {
        boolean left = GDV5.KeysPressed[KeyEvent.VK_A] && this.x > 0;
        boolean right = GDV5.KeysPressed[KeyEvent.VK_D] && this.x < 800 - width;
        if (left) {
            this.x -= dx;
        }
        if (right) {
            this.x += dx;
        }
    }

    public void RGB() {
        if (r != 255 && rr == 1) {
            r += rr;
        } else if (g != 255 && rr == 1) {
            g += rr;
        } else if (b != 255 && rr == 1) {
            b += rr;
        } else if (r != 0 && rr == -1) {
            r += rr;
        } else if (g != 0 && rr == -1) {
            g += rr;
        } else if (b != 0 && rr == -1) {
            b += rr;
        } else if (r == 255 && g == 255 && b == 255) {
            rr = -1;
        } else if (r == 0 && g == 0 && b == 0) {
            rr = 1;
        }
    }

    public void draw(Graphics2D pb) {
        Color color = new Color(r, g, b);
        pb.setColor(color);
        pb.fill(this);
        pb.setColor(Color.white);
        pb.drawRect(this.x, this.y, this.width, this.height);
    }
}

