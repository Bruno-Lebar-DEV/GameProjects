package minizelda.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import minizelda.Game;
import minizelda.sprites.Spritesheet;
import minizelda.worlds.World;

public class Player extends Rectangle {

    public boolean right, up, down, left, shoot;
    public boolean isDead = false;
    public double speed = 2;
    public static int vidaAtual = 5;
    public static int maxVida = 5;
    public int dirHor = 0;
    public int dirVer = 0;
    public int curAnimation = 0;
    public int curFrames = 0;
    public int taregetFrames = 15;
    public int delayBullet = Game.FPS / 4;
    public int curDelay = 0;

    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public Player() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2, 16, 16);
        vidaAtual = 5;
        isDead = false;
    }

    public void newPlayer() {
        int _x, _y;
        Random random = new Random();
        while (true) {
            _x = (random.nextInt(Game.WIDTH / 16) * 16);
            _y = (random.nextInt(Game.HEIGHT / 16) * 16);

            if (World.isFree(_x, _y)) {
                this.newPlayer();
                break;
            }
        }

    }

    public boolean isAreaSafe(int _x, int _y) {
        int xMaior, yMaior, xMenor, yMenor;

        if (x > _x) {
            xMaior = x;
            xMenor = _x;
        } else {
            xMaior = _x;
            xMenor = x;
        }
        if (y > _y) {
            yMaior = y;
            yMenor = _y;
        } else {
            yMaior = _y;
            yMenor = y;
        }
        double distance = Math.sqrt(((xMaior - xMenor) * 2) + ((yMaior - yMenor) * 2));

        if (distance > 16)
            return true;
        else
            return false;
    }

    public void perdeVida() {
        vidaAtual -= 1;
        if (vidaAtual <= 0)
            isDead = true;
    }

    public void tick() {
        boolean moved = false;
        curDelay++;

        if (isDead) {
            return;
        }

        if (right && World.isFree(x + speed, y)) {
            moved = true;
            x += speed;
            dirVer = 0;
            dirHor = 1;
        } else if (left && World.isFree(x - speed, y)) {
            moved = true;
            x -= speed;
            dirVer = 0;
            dirHor = -1;
        } else if (up && World.isFree(x, y - speed)) {
            moved = true;
            y -= speed;
            dirVer = -1;
            dirHor = 0;
        } else if (down && World.isFree(x, y + speed)) {
            moved = true;
            y += speed;
            dirVer = 1;
            dirHor = 0;
        }

        if (moved) {
            curFrames++;
            if (curFrames == taregetFrames) {
                curFrames = 0;
                curAnimation++;
                if (curAnimation == Spritesheet.player_front.length) {
                    curAnimation = 0;
                }
            }
        }

        if (curDelay >= delayBullet) {
            if (shoot && (dirHor != 0 || dirVer != 0)) {
                curDelay = 0;
                shoot = false;
                bullets.add(new Bullet(x, y, dirHor, dirVer));
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }

    }

    public void render(Graphics g) {

        g.drawImage(Spritesheet.player_front[curAnimation], x, y, 16, 16, null);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }
}