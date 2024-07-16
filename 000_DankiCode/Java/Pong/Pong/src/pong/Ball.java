import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    public double x, y;
    public double dx, dy;
    public int width, height;
    public double speed = 2;

    public Ball() {
        this.x = Game.WIDTH / 2;
        this.y = Game.HEIGHT / 2;
        this.width = 5;
        this.height = 5;
        int angle = new Random().nextInt(75) + 120;
        this.dx = Math.cos(Math.toRadians(angle));
        this.dy = Math.sin(Math.toRadians(angle));
    }

    public void tick() {

        if (y + (dy * speed) + width > Game.HEIGHT) {
            dy = dy * (-1);
        } else if (y + (dy * speed) < 0) {
            dy = dy * (-1);
        }

        if (x >= Game.WIDTH) {
            // Ponto da CPU/Player2

        } else if (x <= 0) {
            // Ponto do Player1
        }

        Rectangle boundsBall = new Rectangle((int) (x + (dx * speed)), (int) (y + (dy * speed)), width, height);

        Rectangle boundsPlayer = new Rectangle((int) Game.player.x, (int) Game.player.y, Game.player.width,
                Game.player.height);

        if (Game.vsCPU) {
            Rectangle boundsEnemy = new Rectangle((int) Game.enemy.x, (int) Game.enemy.y, Game.enemy.width,
                    Game.enemy.height);
            if (boundsBall.intersects(boundsPlayer)) {
                int angle = new Random().nextInt(75) + 120;
                this.dx = Math.cos(Math.toRadians(angle));
                this.dy = Math.sin(Math.toRadians(angle));
                if(dx < 0)
                dx = dx * (-1);
            } else if (boundsBall.intersects(boundsEnemy)) {
                int angle = new Random().nextInt(75) + 120;
                this.dx = Math.cos(Math.toRadians(angle));
                this.dy = Math.sin(Math.toRadians(angle));
                if(dx > 0)
                dx = dx * (-1);
            }
        } else {
            Rectangle boundsPlayer2 = new Rectangle((int) Game.player2.x, (int) Game.player2.y, Game.player2.width,
                    Game.player2.height);
            if (boundsBall.intersects(boundsPlayer)) {
                int angle = new Random().nextInt(75) + 120;
                this.dx = Math.cos(Math.toRadians(angle));
                this.dy = Math.sin(Math.toRadians(angle));
                if(dx < 0)
                dx = dx * (-1);
            } else if (boundsBall.intersects(boundsPlayer2)) {
                int angle = new Random().nextInt(75) + 120;
                this.dx = Math.cos(Math.toRadians(angle));
                this.dy = Math.sin(Math.toRadians(angle));
                if(dx > 0)
                dx = dx * (-1);
            }

        }

        x += dx * speed;
        y += dy * speed;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) x, (int) y, width, height);
    }

}
