package survivorstrail_castleconundrums.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.graphics.Spritesheet;


public class Tile {
    private static Spritesheet spritesheet = new Spritesheet("/mapas/texturas.png");

    public static BufferedImage TILE_FLOOR = spritesheet.getSprite(1, 1, 16, 16);
    public static BufferedImage TILE_WALL = spritesheet.getSprite(18, 1, 16, 16);
    public static BufferedImage TILE_WATER = spritesheet.getSprite(35, 1, 16, 16);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}
