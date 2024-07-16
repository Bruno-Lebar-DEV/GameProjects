
package minizelda.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import minizelda.sprites.Spritesheet;

public class Blocks extends Rectangle {

    public Blocks(int x, int y) {
        super(x, y, 16, 16);
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.tileWall, x, y, 16, 16, null);
    }
}
