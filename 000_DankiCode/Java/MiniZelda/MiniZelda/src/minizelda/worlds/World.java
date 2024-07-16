
package minizelda.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import minizelda.Game;

public class World {

    public static ArrayList<Blocks> blocos = new ArrayList<Blocks>();
    private Random random = new Random();
    private int maxBlocos = 10; // * Game.DIFFICULTY;

    public World() {
        this.createBarrer();
        this.createObstaculos();
    }

    public void createBarrer() {

        for (int x = 0; x < Game.WIDTH / 16; x++) {
            blocos.add(new Blocks(x * 16, 32));
        }
        for (int x = 0; x < Game.WIDTH / 16; x++) {
            blocos.add(new Blocks(x * 16, Game.HEIGHT - 16));
        }
        for (int y = 2; y < Game.HEIGHT / 16; y++) {
            blocos.add(new Blocks(0, y * 16));
        }
        for (int y = 2; y < Game.HEIGHT / 16; y++) {
            blocos.add(new Blocks(Game.WIDTH - 16, y * 16));
        }

    }

    public void createObstaculos() {
        int _x, _y;

        // Adiciona blocos no meio do mapa
        for (int i = 0; i < maxBlocos; i++) {

            // Adiciona blocos no meio do mapa
            while (true) {
                _x = (random.nextInt(Game.WIDTH / 16) * 16);
                _y = (random.nextInt(Game.HEIGHT / 16) * 16);
                if (Game.WIDTH / 2 != _x || Game.HEIGHT / 2 != _y) {
                    if (World.isFree(_x, _y)) {
                        blocos.add(new Blocks(_x, _y));
                        break;
                    }
                }
            }
        }
    }

    public static boolean isFree(double x, double y) {
        if (y <= 32) {
            return false;
        }

        for (int i = 0; i < blocos.size(); i++) {
            Blocks blocoAtual = blocos.get(i);
            if (blocoAtual.intersects(new Rectangle((int) x, (int) y, 16, 16))) {
                return false;
            }
        }
        return true;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        for (int i = 0; i < blocos.size(); i++) {
            blocos.get(i).render(g);
        }
    }
}
