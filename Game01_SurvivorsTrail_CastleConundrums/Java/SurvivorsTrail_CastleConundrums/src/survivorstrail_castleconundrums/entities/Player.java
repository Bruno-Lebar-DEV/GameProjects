package survivorstrail_castleconundrums.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;
import survivorstrail_castleconundrums.world.Camera;
import survivorstrail_castleconundrums.world.World;

public class Player extends Entity {

    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    public boolean interct = false;

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
    public boolean isDamage = false;

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

    public void tick() {
        moved = false;

        if (moveUp && World.isFree(this.getX(), this.getY() - this.getSpeed())) {
            moved = true;
            this.setY(getY() - this.getSpeed());
        } else if (moveDown && World.isFree(this.getX(), this.getY() + this.getSpeed())) {
            moved = true;
            this.setY(getY() + this.getSpeed());
        }

        if (moveLeft && World.isFree(this.getX() - (int) this.getSpeed(), this.getY())) {
            moved = true;
            dir = left_dir;
            this.setX(getX() - this.getSpeed());
        } else if (moveRight && World.isFree(this.getX() + (int) this.getSpeed(), this.getY())) {
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
