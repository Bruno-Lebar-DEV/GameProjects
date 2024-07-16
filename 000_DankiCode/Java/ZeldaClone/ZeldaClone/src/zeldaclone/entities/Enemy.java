package zeldaclone.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import zeldaclone.main.Game;
import zeldaclone.world.Camera;
import zeldaclone.world.World;

public class Enemy extends Entity {

    private final int maxAnimation = 4;
    private final int maxFrames = 5;
    private final int minEnemyType = 0;
    private final int maxEnemyType = 4;
    private int frames = 0;
    private int animation = 0;
    private int damageFrames = 0;

    private BufferedImage[] movedEnemy;
    private BufferedImage[] damageEnemy;

    public double maxLife = 10;
    public double life = 10;
    public boolean moved = false;
    public boolean atack = false;
    public boolean isDamage = false;
    public int enemyType = 0;

    public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.setMask(4, 4, 8, 8);
        this.enemyType = Game.rand.nextInt(minEnemyType, maxEnemyType);
        this.setSpeed(0.4 + (0.2 * this.enemyType));
        this.maxLife = (1 + (this.enemyType));
        this.life = maxLife;

        movedEnemy = new BufferedImage[maxAnimation + 1];
        damageEnemy = new BufferedImage[maxAnimation + 1];

        for (int i = 0; i < maxAnimation; i++) {
            movedEnemy[i] = spriteEnemy.getSprite((i * 17) + 18, (this.enemyType * 17) + 1, 16, 16);
            damageEnemy[i] = spriteEnemy.getSprite((i * 17) + 18, 69, 16, 16);
        }
    }

    public boolean isColiddingWithPlayer() {
        Rectangle enemyCurrent = new Rectangle((int) this.getX() + this.maskX, (int) this.getY() + this.maskY,
                this.maskW,
                this.maskH);
        Rectangle player = new Rectangle((int) Game.player.getX() + Game.player.maskX,
                (int) Game.player.getY() + Game.player.maskY, Game.player.maskW,
                Game.player.maskH);

        if (enemyCurrent.intersects(player)) {
            return true;
        }

        return false;
    }

    public boolean isColidding(double nextX, double nextY) {
        Rectangle enemyCurrent = new Rectangle((int) nextX + this.maskX, (int) nextY + this.maskY, this.maskW,
                this.maskH);
        for (int i = 0; i < Game.enemies.size(); i++) {
            Enemy e = Game.enemies.get(i);
            if (e == this)
                continue;
            Rectangle targetEnemy = new Rectangle((int) e.getX() + e.maskX, (int) e.getY() + e.maskY, e.maskW, e.maskH);
            if (enemyCurrent.intersects(targetEnemy)) {
                return true;
            }
        }
        return false;
    }

    public boolean isColiddingBullet() {
        for (int i = 0; i < Game.bullets.size(); i++) {
            Bullet e = Game.bullets.get(i);
            if (Entity.isColidding(this, e)) {
                Game.bullets.remove(e);
                return true;
            }
        }
        return false;
    }

    public void tick() {
        moved = false;
        atack = false;

        if (!isColiddingWithPlayer()) {

            if (Game.rand.nextInt(100) < 60) {

                if (Game.player.getY() < this.getY() && World.isFree(this.getX(), this.getY() - this.getSpeed())
                        && !isColidding(this.getX(), this.getY() - this.getSpeed())) {
                    this.setY(getY() - this.getSpeed());
                    moved = true;

                } else if (Game.player.getY() > this.getY() && World.isFree(this.getX(), this.getY() + this.getSpeed())
                        && !isColidding(this.getX(), this.getY() + this.getSpeed())) {
                    this.setY(getY() + this.getSpeed());
                    moved = true;

                }

                if (Game.player.getX() < this.getX() && World.isFree(this.getX() - this.getSpeed(), this.getY())
                        && !isColidding(this.getX() - this.getSpeed(), this.getY())) {
                    this.setX(getX() - this.getSpeed());
                    moved = true;

                } else if (Game.player.getX() > this.getX() && World.isFree(this.getX() + this.getSpeed(), this.getY())
                        && !isColidding(this.getX() + this.getSpeed(), this.getY())) {
                    this.setX(getX() + this.getSpeed());
                    moved = true;

                }
            }
        } else {
            // estamos colidindo com o player
            atack = true;
            if (Game.rand.nextInt(100) < 10) {
                Game.player.life -= Game.rand.nextInt(5);
                Game.player.isDamage = true;
            }
        }

        if (isColiddingBullet()) {
            this.life--;
            isDamage = true;
            if (life <= 0) {
                Game.enemies.remove(this);
                Game.entities.remove(this);
            }
        }

        if (isDamage) {
            damageFrames++;
            if (damageFrames >= maxFrames) {
                damageFrames = 0;
                isDamage = false;
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
    }

    public void render(Graphics g) {
        if (isDamage) {
            g.drawImage(damageEnemy[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
        } else {
            g.drawImage(movedEnemy[animation], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
        }

        if (Game.SHOW_COLIDERS) {
            g.setColor(Color.blue);
            g.fillRect((int) this.getX() + this.maskX - Camera.x, (int) this.getY() + this.maskY - Camera.y, this.maskW,
                    this.maskH);
        }
    }

}
