package survivorstrail_castleconundrums.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import survivorstrail_castleconundrums.Itens.Itens;
import survivorstrail_castleconundrums.Itens.PortaSaida;
import survivorstrail_castleconundrums.entities.Entity;
import survivorstrail_castleconundrums.entities.Player;
import survivorstrail_castleconundrums.world.World;

public class Game {
    public static int SAVE_LEVEL = 1;
    public static int LOAD_LEVEL = 1;
    public static int MAX_LEVEL = 10;

    public static Random rand = new Random();
    public static Player player = new Player();;
    public static ArrayList<Entity> entities = new ArrayList<Entity>();;
    public static ArrayList<Itens> itens = new ArrayList<Itens>();;
    public static World world;
    public static PortaSaida portaSaida;

    public boolean pauseGame = false;
    public boolean nextLevel = false;

    public static boolean porta_liberada = false;
    public boolean player_win = false;

    public Game() {
    }

    public static void newGame() {
        LOAD_LEVEL = 1;
        SAVE_LEVEL = 1;
        world = new World("/level1.png");
    }

    public static void loadGame() {
        LOAD_LEVEL = SAVE_LEVEL;
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

        porta_liberada = false;
        portaSaida = new PortaSaida(256, 16, 32, 32);
        entities = new ArrayList<Entity>();
        itens = new ArrayList<Itens>();
        world = new World("/level" + LOAD_LEVEL + ".png");
    }

    public static void resetLevel() {
        porta_liberada = false;
        portaSaida = new PortaSaida(256, 16, 32, 32);
        entities = new ArrayList<Entity>();
        itens = new ArrayList<Itens>();
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

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }

        for (int i = 0; i < itens.size(); i++) {
            itens.get(i).tick();
        }

        porta_liberada = true;
        for (int i = 0; i < itens.size(); i++) {
            if (!itens.get(i).isAtivo())
                porta_liberada = false;
        }

        portaSaida.tick();
        player.tick();

    }

    public void render(Graphics g) {
        world.render(g);

        for (int i = 0; i < itens.size(); i++) {
            itens.get(i).render(g);
        }

        portaSaida.render(g);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }

        player.render(g);
    }
}
