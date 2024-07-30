package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Enemy {
    public double x, y;
    public int width, height;

    public Enemy() {
        this.x = Game.WIDTH - 11;
        this.y = Game.HEIGHT / 2;
        this.width = 4;
        this.height = 30;
    }

    public void tick() {
        if (new Random().nextInt(100) < 60) {
            if (Game.ball.y < this.y + (this.height / 2)) {
                y -= Game.SPEED;
            } else if (Game.ball.y > this.y + (this.height / 2)) {
                y += Game.SPEED;
            }
        }

        if (this.y + this.height > Game.HEIGHT) {
            this.y = Game.HEIGHT - this.height;
        } else if (this.y < 0) {
            this.y = 0;
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) this.x, (int) this.y, this.width, this.height);
    }
}
