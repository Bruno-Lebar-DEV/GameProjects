package minizelda.entidades;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import minizelda.Game;
import minizelda.worlds.World;

public class Entidades {

    public static ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static ArrayList<Itens> itens = new ArrayList<Itens>();
    public static Player player;
    private int maxInimigos = 1;// + Game.DIFFICULTY;
    private int maxItens = 1;
    private Random random = new Random();

    public Entidades() {
        this.createPlayer();
        this.createInimigos();
        this.createItens(1);
    }

    public void createPlayer() {
        player = new Player();
    }

    public void createInimigos() {
        int _x, _y;

        // Adiciona inimigos
        for (int i = inimigos.size(); i < maxInimigos; i++) {
            while (true) {
                _x = (random.nextInt(Game.WIDTH / 16) * 16);
                _y = (random.nextInt(Game.HEIGHT / 16) * 16);

                if (player.isAreaSafe(_x, _y)) {
                    if (World.isFree(_x, _y)) {
                        inimigos.add(new Inimigo(_x, _y));
                        break;
                    }
                }
            }
        }
    }

    public void createItens(int type) {
        int _x;
        int _y;

        while (true) {
            _x = (random.nextInt(Game.WIDTH / 16) * 16);
            _y = (random.nextInt(Game.HEIGHT / 16) * 16);

            if (player.isAreaSafe(_x, _y)) {
                if (World.isFree(_x, _y)) {
                    itens.add(new Itens(_x, _y, type));
                    break;
                }
            }
        }
    }

    public void tick() {
        player.tick();

        // verifica se o jogador morreu para encerrar o jogo
        if (player.isDead) {
            //Game.stop() = false;
            return;
        }

        // Atualiza os inimigos
        for (int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).tick(player);
        }

        // Cria caso falte imigos
        if (inimigos.size() < maxInimigos) {
            this.createInimigos();
        }

        // Atualiza os Itens
        for (int i = 0; i < itens.size(); i++) {
            itens.get(i).tick();
        }

        // Cria caso falte itens
        if (itens.size() < maxItens) {
            this.createItens(1);
        }

    }

    public void render(Graphics g) {

        player.render(g);

        for (int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).render(g);
        }

        for (int i = 0; i < itens.size(); i++) {
            itens.get(i).render(g);
        }

    }

}
