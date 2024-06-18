package survivorstrail_castleconundrums.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.graphics.Spritesheet;
import survivorstrail_castleconundrums.main.GameController;
import survivorstrail_castleconundrums.world.Camera;

public class Itens extends Entity {

    protected static Spritesheet spritePlataforma = new Spritesheet("/entidades/plataforma.png");
    protected static Spritesheet spriteItens = new Spritesheet("/entidades/Itens.png");

    public static BufferedImage BOTAO_VERMELHO_DISABLE = spriteItens.getSprite(1, 1, 32, 32);
    public static BufferedImage BOTAO_AZUL_DISABLE = spriteItens.getSprite(34, 1, 32, 32);
    public static BufferedImage BOTAO_VERDE_DISABLE = spriteItens.getSprite(67, 1, 32, 32);
    public static BufferedImage BOTAO_ENABLE = spriteItens.getSprite(100, 1, 32, 32);
    public static BufferedImage ALAVANCA_DISABLE = spriteItens.getSprite(1, 34, 32, 32);
    public static BufferedImage ALAVANCA_ENABLE = spriteItens.getSprite(34, 34, 32, 32);
    public static BufferedImage PLATAFORMA_DISABLE = spritePlataforma.getSprite(1, 1, 64, 64);
    public static BufferedImage PLATAFORMA_ENABLE = spritePlataforma.getSprite(66, 1, 64, 64);
    public static BufferedImage PORTA_DISABLE = spriteItens.getSprite(2, 67, 64, 32);
    public static BufferedImage PORTA_ENABLE = spriteItens.getSprite(67, 67, 64, 32);

    public int ID_RELATION = 0;
    public boolean enable = false;
    public boolean colision = false;
    public boolean interagivel = false;
    public boolean carregavel = false;
    public double x;
    public double y;
    public int width;
    public int height;
    public boolean isInteragivel = false;
    public int maskX;
    public int maskY;
    public int maskW;
    public int maskH;
    public BufferedImage spriteDisable;
    public BufferedImage spriteEnable;

    public boolean showMensagem = false;
    public String Mensagem = "";

    public Itens(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height, Entity.PLAYER_EN);
        this.x = _x;
        this.y = _y;
        this.width = _width;
        this.height = _height;

        this.maskX = 2;
        this.maskY = 2;
        this.maskW = width - 2;
        this.maskH = height - 2;
    }

    public void setMask(int _maskX, int _maskY, int _maskW, int _maskH) {
        this.maskX = _maskX;
        this.maskY = _maskY;
        this.maskW = _maskW;
        this.maskH = _maskH;
    }

    public void setSprites(BufferedImage sprite1, BufferedImage sprite2) {
        this.spriteDisable = sprite1;
        this.spriteEnable = sprite2;
    }

    public boolean isAtivo() {
        return enable;
    }

    public void Enable() {
        enable = true;
    }

    public void Disable() {
        enable = false;
    }

    public boolean isColision() {
        return colision;
    }

    public void EnableColision() {
        colision = true;
    }

    public void DisableColision() {
        colision = false;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void tick() {

    }

    public void render(Graphics g) {

        if (isAtivo())
            g.drawImage(spriteEnable, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
        else
            g.drawImage(spriteDisable, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);

        if (GameController.DEV_SHOW_COLIDERS) {
            g.setColor(Color.red);
            g.fillRect((int) this.getX() + this.maskX - Camera.x, (int) this.getY() + this.maskY - Camera.y, this.maskW,
                    this.maskH);
        }
    }
}
