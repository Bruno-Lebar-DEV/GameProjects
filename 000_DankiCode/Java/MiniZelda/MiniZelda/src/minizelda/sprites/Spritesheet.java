package minizelda.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

    public static BufferedImage spritesheet;

    public static BufferedImage[] player_front;
    public static BufferedImage[] inimigo_front;
    public static BufferedImage tileWall;
    public static BufferedImage vidaCheia;
    public static BufferedImage vidaVazia;

    public Spritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player_front = new BufferedImage[2];
        player_front[0] = Spritesheet.getSprite(0, 0, 16, 16);
        player_front[1] = Spritesheet.getSprite(16, 0, 16, 16);
       
        inimigo_front = new BufferedImage[2];
        inimigo_front[0] = Spritesheet.getSprite(0, 16, 16, 16);
        inimigo_front[1] = Spritesheet.getSprite(16, 16, 16, 16);
       
        tileWall = Spritesheet.getSprite(192, 201, 16, 16);
        vidaCheia = Spritesheet.getSprite(208, 201, 16, 16);
        vidaVazia = Spritesheet.getSprite(224, 201, 16, 16);
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }
}
