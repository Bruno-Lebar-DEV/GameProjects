package zeldaclone.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import zeldaclone.main.Game;
import zeldaclone.world.Camera;
import zeldaclone.world.World;

public class Player extends Entity {

    private final int maxAnimation = 4;
    private final int maxFrames = 5;
    private int frames = 0;
    private int animation = 0;
    private int damageFrames = 0;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage[] damageRightPlayer;
    private BufferedImage[] damageLeftPlayer;
    private BufferedImage rightWeapon = spritePlayer.getSprite(1, 69, 16, 16);;
    private BufferedImage leftWeapon = spritePlayer.getSprite(18, 69, 16, 16);;
    private BufferedImage damageRightWeapon = spritePlayer.getSprite(35, 69, 16, 16);;
    private BufferedImage damageLefttWeapon = spritePlayer.getSprite(52, 69, 16, 16);;

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
    public boolean moveUp, moveDown, moveLeft, moveRight = false;
    public boolean isDamage = false;

    public Player(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.setMask(1, 1, 14, 14);
        this.setSpeed(1.2);

        rightPlayer = new BufferedImage[maxAnimation + 1];
        leftPlayer = new BufferedImage[maxAnimation + 1];
        damageRightPlayer = new BufferedImage[maxAnimation + 1];
        damageLeftPlayer = new BufferedImage[maxAnimation + 1];

        for (int i = 0; i < maxAnimation; i++) {
            leftPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 1, 16, 16);
            rightPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 18, 16, 16);
            damageLeftPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 35, 16, 16);
            damageRightPlayer[i] = spritePlayer.getSprite((i * 17) + 18, 52, 16, 16);

        }
    }

    public void checkCollisionLifePack() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof LifePack) {
                if (Entity.isColidding(this, e)) {
                    life += 8;
                    if (life >= 100) {
                        life = 100;
                    }
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }

    public void checkCollisionAmmo() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof Ammo) {
                if (Entity.isColidding(this, e)) {
                    ammos += 16;
                    if (ammos >= maxAmmo) {
                        ammos = maxAmmo;
                    }
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }

    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof Weapon) {
                if (Entity.isColidding(this, e)) {
                    this.weapon = true;
                    Game.entities.remove(i);
                    return;
                }
            }
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

        if (shoot) {
            shoot = false;

            double dx = 0;
            double dy = 0;
            int px = 0;
            int py = 4;

            if (tecladoShoot) {
                tecladoShoot = false;

                if (dir == right_dir) {
                    px = 16;
                    dx = 1;
                } else {
                    px = -8;
                    dx = -1;
                }

            } else if (mouseShoot) {
                mouseShoot = false;

                if (dir == right_dir) {
                    px = 20;
                    double angle = Math.atan2(mouseY - (this.getY() + py - Camera.y),
                            mouseX - (this.getX() + px - Camera.x));
                    dx = Math.cos(angle);
                    dy = Math.sin(angle);
                } else {
                    px = -8;
                    double angle = Math.atan2(mouseY - (this.getY() + py - Camera.y),
                            mouseX - (this.getX() + px - Camera.x));
                    dx = Math.cos(angle);
                    dy = Math.sin(angle);
                }

            }
            if (weapon && ammos > 0) {
                this.ammos--;

                Bullet bullet = new Bullet(this.getX() + px, this.getY() + py, 4, 4, null, dx, dy);
                Game.bullets.add(bullet);
            }
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

        if (isDamage) {
            damageFrames++;
            if (damageFrames >= maxFrames) {
                damageFrames = 0;
                isDamage = false;
            }
        }

        if (life <= 0) {
            life = 0;
            Game.gameState = "GAME_OVER";
        }

        if (life < maxLife)

            checkCollisionLifePack();

        if (ammos < maxAmmo)

            checkCollisionAmmo();

        checkCollisionWeapon();

        Camera.x = Camera.clamp((int) this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp((int) this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

    }

    public void render(Graphics g) {
        if (dir == left_dir) {

            if (isDamage) {
                g.drawImage(damageRightPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y,
                        null);
                if (weapon) {
                    g.drawImage(damageLefttWeapon, (int) this.getX() - Camera.x - 10, (int) this.getY() - Camera.y,
                            null);
                }
            } else {
                g.drawImage(leftPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y,
                        null);
                if (weapon) {
                    g.drawImage(leftWeapon, (int) this.getX() - Camera.x - 10, (int) this.getY() - Camera.y, null);
                }
            }

        } else {

            if (isDamage) {
                g.drawImage(damageRightPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y,
                        null);
                if (weapon) {
                    g.drawImage(damageRightWeapon, (int) this.getX() - Camera.x + 10, (int) this.getY() - Camera.y,
                            null);
                }
            } else {
                g.drawImage(rightPlayer[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
                if (weapon) {
                    g.drawImage(rightWeapon, (int) this.getX() - Camera.x + 10, (int) this.getY() - Camera.y, null);
                }
            }
        }

        if (Game.SHOW_COLIDERS) {
            g.setColor(Color.blue);
            g.fillRect((int) this.getX() + this.maskX - Camera.x, (int) this.getY() + this.maskY - Camera.y, this.maskW,
                    this.maskH);
        }
    }

}
