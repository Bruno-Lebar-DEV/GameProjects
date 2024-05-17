package survivorstrail_castleconundrums.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.Itens.Itens;
import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;
import survivorstrail_castleconundrums.world.Camera;
import survivorstrail_castleconundrums.world.World;

public class Player extends Entity {

    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    public boolean interact = false;

    private final int maxAnimation = 4;
    private final int maxFrames = 5;
    private int frames = 0;
    private int animation = 0;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    public int ammos = 0;
    public double life = 100;
    public final int maxAmmo = 64;
    public final double maxLife = 100;

    public int left_dir = -1;
    public int right_dir = 1;
    public int dir = 0;
    public int mouseX = 0;
    public int mouseY = 0;

    public boolean shoot = false;
    public boolean tecladoShoot = false;
    public boolean mouseShoot = false;
    public boolean weapon = false;
    public boolean moved = false;

    public Player() {
        super(0, 0, 16, 16, Entity.PLAYER_EN);
        this.setMask(1, 1, 14, 14);
        this.setSpeed(1.2);

        rightPlayer = new BufferedImage[maxAnimation + 1];
        leftPlayer = new BufferedImage[maxAnimation + 1];

        for (int i = 0; i < maxAnimation; i++) {
            leftPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 1, 16, 16);
            rightPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 18, 16, 16);

        }
    }

    public boolean isColiddingItens(double x, double y, Itens item) {

        Rectangle e1Mask = new Rectangle((int) x + this.maskX - Camera.x,
                (int) y + this.maskY - Camera.y,
                this.maskW, this.maskH);
        Rectangle e2Mask = new Rectangle((int) item.getX() + item.maskX - Camera.x,
                (int) item.getY() + item.maskY - Camera.y,
                item.maskW, item.maskH);

        if (GameController.DEV_SHOW_COLIDERS)
            if (e1Mask.intersects(e2Mask))
                System.out.println("Colisão");

        return e1Mask.intersects(e2Mask);
    }

    public boolean isColiddingNextLevel(double x, double y, Itens item) {

        Rectangle e1Mask = new Rectangle((int) x + this.maskX - Camera.x,
                (int) y + this.maskY - Camera.y,
                this.maskW, this.maskH);
        Rectangle e2Mask = new Rectangle((int) item.getX() + item.maskX + 8 - Camera.x,
                (int) item.getY() + item.maskY + 8 - Camera.y,
                item.maskW - 8, item.maskH - 8);

        if (GameController.DEV_SHOW_COLIDERS)
            if (e1Mask.intersects(e2Mask))
                System.out.println("Colisão");

        return e1Mask.intersects(e2Mask);
    }

    private boolean podeAndar(double x, double y) {
        // verifica se pode andar pelos blocos
        if (!World.isFree(x, y))
            return false;

        // verifica se pode andar na porta de saida
        if (Game.portaSaida.isColision() && isColiddingItens(x, y, Game.portaSaida))
            return false;

        // verifica se pode andar nos itens
        for (int i = 0; i < Game.itens.size(); i++) {

            if (Game.itens.get(i).isColision() && isColiddingItens(x, y, Game.itens.get(i))) {
                return false;
            }
        }

        return true;
    }

    public void tick() {
        moved = false;

        if (moveUp && podeAndar(this.getX(), this.getY() - this.getSpeed())) {
            moved = true;
            this.setY(getY() - this.getSpeed());
        } else if (moveDown && podeAndar(this.getX(), this.getY() + this.getSpeed())) {
            moved = true;
            this.setY(getY() + this.getSpeed());
        }

        if (moveLeft && podeAndar(this.getX() - (int) this.getSpeed(), this.getY())) {
            moved = true;
            dir = left_dir;
            this.setX(getX() - this.getSpeed());
        } else if (moveRight && podeAndar(this.getX() + (int) this.getSpeed(), this.getY())) {
            moved = true;
            dir = right_dir;
            this.setX(getX() + this.getSpeed());
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                animation++;
                if (animation >= maxAnimation) {
                    animation = 0;
                }
            }
        }

        for (int i = 0; i < Game.entities.size(); i++) {
            isColidding(this, Game.entities.get(i));
        }

        if (isColiddingNextLevel(x, y, Game.portaSaida)) {
            Game.nextLevel();
        }

        for (int i = 0; i < Game.itens.size(); i++) {
            if (isColiddingItens(this.x, this.y, Game.itens.get(i))) {
                if (Game.itens.get(i).isInteragivel) {
                    if (interact) {
                        interact = false;
                        if (Game.itens.get(i).isAtivo())
                            Game.itens.get(i).Disable();
                        else
                            Game.itens.get(i).Enable();
                    }
                } else {
                    if (!Game.itens.get(i).isAtivo())
                        Game.itens.get(i).Enable();
                }
            }
        }

        Camera.x = Camera.clamp((int) this.getX() - (GameController.WIDTH / 2), 0,
                World.WIDTH * 16 - GameController.WIDTH);
        Camera.y = Camera.clamp((int) this.getY() - (GameController.HEIGHT / 2), 0,
                World.HEIGHT * 16 - GameController.HEIGHT);

    }

    public void render(Graphics g) {
        if (dir == left_dir) {
            g.drawImage(leftPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
        } else {
            g.drawImage(rightPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
        }

        if (GameController.DEV_SHOW_COLIDERS) {
            g.setColor(Color.blue);
            g.fillRect((int) this.getX() + this.maskX - Camera.x, (int) this.getY() + this.maskY - Camera.y, this.maskW,
                    this.maskH);
        }
    }

}
