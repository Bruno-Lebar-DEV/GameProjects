package survivorstrail_castleconundrums.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.graphics.Spritesheet;
import survivorstrail_castleconundrums.main.GameController;
import survivorstrail_castleconundrums.world.Camera;

public class Entity {

    protected static Spritesheet spriteItens = new Spritesheet("/entidades/Itens.png");
    protected static Spritesheet spritePlayer = new Spritesheet("/entidades/Player.png");
    protected static Spritesheet spriteEnemy = new Spritesheet("/entidades/Inimigo.png");

    public static BufferedImage WEAPON_EN = spriteItens.getSprite(1, 1, 16, 16);
    public static BufferedImage LIFEPACK_EN = spriteItens.getSprite(18, 1, 16, 16);
    public static BufferedImage AMMO_EN = spriteItens.getSprite(35, 1, 16, 16);
    public static BufferedImage ENEMY_EN = spriteEnemy.getSprite(1, 1, 16, 16);
    public static BufferedImage PLAYER_EN = spritePlayer.getSprite(1, 1, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed;
    protected int maskX;
    protected int maskY;
    protected int maskW;
    protected int maskH;

    private BufferedImage sprite;

    public Entity(double _x, double _y, int _width, int _height, BufferedImage _sprite) {
        this.x = _x;
        this.y = _y;
        this.width = _width;
        this.height = _height;

        this.sprite = _sprite;

        this.maskX = 0;
        this.maskY = 0;
        this.maskW = width;
        this.maskH = height;

        this.speed = 1;

    }

    public void setMask(int _maskX, int _maskY, int _maskW, int _maskH) {
        this.maskX = _maskX;
        this.maskY = _maskY;
        this.maskW = _maskW;
        this.maskH = _maskH;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(double _x) {
        this.x = _x;
    }

    public void setY(double _y) {
        this.y = _y;
    }

    public void setWidth(int _width) {
        this.width = _width;
    }

    public void setHeight(int _height) {
        this.height = _height;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double _speed) {
        this.speed = _speed;
    }

    public static boolean isColidding(Entity e1, Entity e2) {
        Rectangle e1Mask = new Rectangle((int) e1.getX() + e1.maskX - Camera.x, (int) e1.getY() + e1.maskY - Camera.y,
                e1.maskW,
                e1.maskH);
        Rectangle e2Mask = new Rectangle((int) e2.getX() + e2.maskX - Camera.x, (int) e2.getY() + e2.maskY - Camera.y,
                e2.maskW, e2.maskH);

        if (GameController.DEV_SHOW_COLIDERS)
            if (e1Mask.intersects(e2Mask))
                System.out.println("Colisão");
        return e1Mask.intersects(e2Mask);
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        g.drawImage(sprite, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
    }

}
