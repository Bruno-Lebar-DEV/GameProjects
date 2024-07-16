package minizelda.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import minizelda.sprites.Spritesheet;
import minizelda.worlds.World;

public class Inimigo extends Rectangle {

    public double speed = 2;
    public int curAnimation = 0;
    public int curFrames = 0;
    public int taregetFrames = 15;

    public Inimigo(int x, int y) {
        super(x, y, 16, 16);
        speed = new Random().nextDouble(1, speed);
    }

    public void perseguirPlayer(Player p) {
        if (new Random().nextInt(100) < 50) {

            if (x < p.x && World.isFree(x + speed, y)) {
                x += speed;
            } else if (x > p.x && World.isFree(x - speed, y)) {
                x -= speed;
            }
        } else {
            if (y > p.y && World.isFree(x, y - speed)) {
                y -= speed;
            } else if (y < p.y && World.isFree(x, y + speed)) {
                y += speed;
            }
        }

    }

    public boolean isPlayer(int x, int y) {
        if (Entidades.player.intersects(new Rectangle(x, y, 16, 16))) {
            return true;
        }
        return false;
    }

    public void tick(Player player) {
        boolean moved = true;

        if (isPlayer(x, y)) {
            Entidades.player.perdeVida();
            Entidades.inimigos.remove(this);
        }

        perseguirPlayer(player);

        if (moved) {
            curFrames++;
            if (curFrames == taregetFrames) {
                curFrames = 0;
                curAnimation++;
                if (curAnimation == Spritesheet.inimigo_front.length) {
                    curAnimation = 0;
                }
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.inimigo_front[curAnimation], x, y, 16, 16, null);
    }
}