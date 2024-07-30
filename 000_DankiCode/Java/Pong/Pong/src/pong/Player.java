package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public boolean up, down;
    public double x, y;
    public int width, height;

    public Player() {
        this.x = 5;
        this.y = Game.HEIGHT / 2;
        this.width = 4;
        this.height = 30;
    }

    public void tick() {
        if (up) {
            this.y -= Game.SPEED;
        } else if (down) {
            this.y += Game.SPEED;
        }

        if (this.y + this.height > Game.HEIGHT) {
            this.y = Game.HEIGHT - this.height;
        } else if (this.y < 0) {
            this.y = 0;
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int) this.x, (int) this.y, this.width, this.height);
    }
}
