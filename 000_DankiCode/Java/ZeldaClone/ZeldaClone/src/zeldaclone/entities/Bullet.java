package zeldaclone.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import zeldaclone.main.Game;
import zeldaclone.world.Camera;

public class Bullet extends Entity {

    private double dx;
    private double dy;

    private int time = 0;
    private final int MaxTime = 60;

    public Bullet(double x, double y, int width, int height, BufferedImage sprite, double dx, double dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
        this.setMask(0, 0, width, height);
        this.setSpeed(3);
    }

    public void tick() {
        x += dx * speed;
        y += dy * speed;
        time++;
        if (time == MaxTime) {
            Game.bullets.remove(this);
            return;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval((int) this.getX() - Camera.x, (int) this.getY() - Camera.y, this.getWidth(), this.getHeight());
        
        if (Game.SHOW_COLIDERS) {
            g.setColor(Color.blue);
            g.fillOval((int) this.getX() + this.maskX - Camera.x, (int) this.getY() + this.maskY - Camera.y, this.maskW,
                    this.maskH);
        }

    }

}
