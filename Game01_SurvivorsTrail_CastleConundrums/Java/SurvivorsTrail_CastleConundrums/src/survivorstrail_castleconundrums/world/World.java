package survivorstrail_castleconundrums.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import survivorstrail_castleconundrums.Itens.Alavanca;
import survivorstrail_castleconundrums.Itens.BotaoAzul;
import survivorstrail_castleconundrums.Itens.BotaoVerde;
import survivorstrail_castleconundrums.Itens.BotaoVermelho;
import survivorstrail_castleconundrums.Itens.Plataforma;
import survivorstrail_castleconundrums.Itens.PortaSaida;
import survivorstrail_castleconundrums.entities.Enemy;
import survivorstrail_castleconundrums.entities.Entity;
import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;

public class World {

    public static Tile[] tiles;
    public static int WIDTH;
    public static int HEIGHT;

    public static final int TILE_SIZE = 32;

    private static final int PLAYER = 0xFF404040;
    private static final int PORTA_SAIDA = 0xFFFFD800;

    private static final int CHAO = 0xFF000000;
    private static final int PAREDE = 0xFFFFFFFF;
    private static final int AGUA = 0xFF00FFFF;
    private static final int INIMIGO = 0xFFFF0000;
    private static final int BOTAO_VERDE = 0xFF00FF21;
    private static final int BOTAO_VERMELHO = 0xFFFF6A00;
    private static final int BOTAO_AZUL = 0xFF0094FF;
    private static final int ALAVANCA = 0xFFFF00DC;
    private static final int PLATAFORMA = 0xFFB200FF;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource("/mapas" + path));
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();

            int[] pixels = new int[WIDTH * HEIGHT];
            tiles = new Tile[WIDTH * HEIGHT];

            map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
            for (int _x = 0; _x < WIDTH; _x++) {
                for (int _y = 0; _y < HEIGHT; _y++) {
                    int pixelAtual = pixels[_x + (_y * WIDTH)];

                    if (pixelAtual == CHAO) {
                        tiles[_x + (_y * WIDTH)] = new FloorTile(_x * TILE_SIZE, _y * TILE_SIZE, Tile.TILE_FLOOR);

                    } else if (pixelAtual == PAREDE) {
                        tiles[_x + (_y * WIDTH)] = new WallTile(_x * TILE_SIZE, _y * TILE_SIZE, Tile.TILE_WALL);

                    } else if (pixelAtual == AGUA) {
                        tiles[_x + (_y * WIDTH)] = new WallTile(_x * TILE_SIZE, _y * TILE_SIZE, Tile.TILE_WATER);

                    } else {
                       
                        tiles[_x + (_y * WIDTH)] = new FloorTile(_x * TILE_SIZE, _y * TILE_SIZE, Tile.TILE_FLOOR);

                        if (pixelAtual == PLAYER) {
                            Game.player.setX(_x * TILE_SIZE);
                            Game.player.setY(_y * TILE_SIZE);

                        } else if (pixelAtual == PORTA_SAIDA) {
                            PortaSaida porta = new PortaSaida(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.portaSaida = porta;

                        } else if (pixelAtual == INIMIGO) {
                            Enemy enemy = new Enemy(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
                            Game.entities.add(enemy);

                        } else if (pixelAtual == BOTAO_VERDE) {
                            BotaoVerde botao = new BotaoVerde(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.itens.add(botao);

                        } else if (pixelAtual == BOTAO_VERMELHO) {
                            BotaoVermelho botao = new BotaoVermelho(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.itens.add(botao);

                        } else if (pixelAtual == BOTAO_AZUL) {
                            BotaoAzul botao = new BotaoAzul(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.itens.add(botao);

                        } else if (pixelAtual == ALAVANCA) {
                            Alavanca alavanca = new Alavanca(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.itens.add(alavanca);

                        } else if (pixelAtual == PLATAFORMA) {
                            Plataforma plataforma = new Plataforma(_x * TILE_SIZE, _y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            Game.itens.add(plataforma);
                        }

                       
                    }

                }
            }

        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(double nextX, double nextY) {
        int x1 = (int) nextX / TILE_SIZE;
        int y1 = (int) nextY / TILE_SIZE;

        int x2 = (int) (nextX + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = (int) nextY / TILE_SIZE;

        int x3 = (int) nextX / TILE_SIZE;
        int y3 = (int) (nextY + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (int) (nextX + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (int) (nextY + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public void render(Graphics g) {
        int xStart = Camera.x >> 5;
        int yStart = Camera.y >> 5;
        int xFinal = xStart + (GameController.WIDTH >> 5);
        int yFinal = yStart + (GameController.HEIGHT >> 5);

        for (int _x = xStart; _x <= xFinal; _x++) {
            for (int _y = yStart; _y <= yFinal; _y++) {
                if (_x < 0 || _y < 0 || _x >= WIDTH || _y >= HEIGHT) {
                    continue;
                }
                Tile tile = tiles[_x + (_y * WIDTH)];
                tile.render(g);
            }
        }
    }
}
