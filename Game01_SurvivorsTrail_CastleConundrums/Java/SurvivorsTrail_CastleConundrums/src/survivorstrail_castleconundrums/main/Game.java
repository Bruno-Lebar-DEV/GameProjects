package survivorstrail_castleconundrums.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import survivorstrail_castleconundrums.entities.Entity;
import survivorstrail_castleconundrums.entities.Player;
import survivorstrail_castleconundrums.world.World;

public class Game {
    public static int SAVE_LEVEL = 1;
    public static int LOAD_LEVEL = 1;
    public static int MAX_LEVEL = 3;

    public static Random rand = new Random();
    public static Player player = new Player();;
    public static ArrayList<Entity> entities = new ArrayList<Entity>();;
    public static World world;

    public boolean pauseGame = false;
    public boolean nextLevel = false;

    public Game() {
    }

    public static void newGame() {
        LOAD_LEVEL = 1;
        SAVE_LEVEL = 1;
        world = new World("/level1.png");
    }

    public static void loadGame() {
        world = new World("/level" + SAVE_LEVEL + ".png");
    }

    public static void saveGame() {
        SAVE_LEVEL = LOAD_LEVEL;
    }

    public static void nextLevel() {
        LOAD_LEVEL++;
        if (LOAD_LEVEL > MAX_LEVEL) {
            LOAD_LEVEL = MAX_LEVEL;
        }

        world = new World("/level" + LOAD_LEVEL + ".png");
    }

    public static void resetLevel() {
        world = new World("/level" + LOAD_LEVEL + ".png");
    }

    public void tick() {
        if (pauseGame) {
            pauseGame = false;
            GameController.setGameMode(GameController.GAMEMODE_PAUSE);
        }

        if (nextLevel) {
            nextLevel = false;
            Game.nextLevel();
        }


        player.tick();

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }

    }

    public void render(Graphics g) {
        world.render(g);
        player.render(g);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }
    }
}
