package Breakout;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Brick extends Rectangle {
    private Color color;
    private boolean isDestroyed;

    private final String[] type = {"speed up", "longer", "shorter", "balls", "slower", "bait", "ball bigger", "ball smaller"};

   private final Map<String, Color> ptoc = Map.of(
           "speed up", Color.blue,
           "longer", Color.yellow,
           "shorter", Color.green,
           "balls", Color.orange,
           "slower", Color.pink,
           "bait", Color.magenta, //does nothing lol
           "ball bigger", Color.cyan,
           "ball smaller", Color.black
   );


    public Brick (int x, int y, int width, int height, Color color, boolean isDestroyed) {
        super(x, y, width, height);
        this.color = color;
        this.isDestroyed = isDestroyed;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void remove(Brick[][] bricks) {
        // Iterate over the array to find the current brick and set it to null
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] == this) {
                    bricks[i][j] = null;
                    return; // Assuming each brick is unique, so we can break once found
                }
            }
        }
    }
    public void destroyed(ArrayList<Powerups> powerups, int x, int y) {
        int random = (int) (Math.random() * 1);
        this.isDestroyed = true;
        GameRunner.addScore(10);
        if (random < 5) {
            String sasan = type[(int) ((Math.random() * 7) + 1)];
            Powerups.spawnPowerup(powerups, x, y, sasan, ptoc.get(sasan));
            System.out.println(sasan);
            System.out.println(ptoc.get(sasan));
            System.out.println("Success");
            System.out.println(powerups);
        } else System.out.println("Failed!");

    }
    public void draw(Graphics2D pb) {
        pb.setColor(Color.gray);
        pb.drawRect(this.x,this.y, this.width, this.height);
        pb.setColor(color);
        pb.fill(this);
    }
}
