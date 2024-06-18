package survivorstrail_castleconundrums.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.graphics.Spritesheet;

public class Tile {
    private static Spritesheet spritesheet = new Spritesheet("/mapas/texturas.png");
    private static Spritesheet spritesheet2 = new Spritesheet("/mapas/Paredes.png");

    public static BufferedImage TILE_FLOOR = spritesheet2.getSprite(34, 34, 32, 32);
    public static BufferedImage TILE_WALL = spritesheet2.getSprite(34, 1, 32, 32);
    public static BufferedImage TILE_WATER = spritesheet.getSprite(67, 1, 32, 32);

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
