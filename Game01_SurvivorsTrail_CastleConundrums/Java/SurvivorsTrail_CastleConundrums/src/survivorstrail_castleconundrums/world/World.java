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

    public static final int TILE_SIZE = 16;

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
                        tiles[_x + (_y * WIDTH)] = new FloorTile(_x * 16, _y * 16, Tile.TILE_FLOOR);

                    } else if (pixelAtual == PAREDE) {
                        tiles[_x + (_y * WIDTH)] = new WallTile(_x * 16, _y * 16, Tile.TILE_WALL);

                    } else if (pixelAtual == AGUA) {
                        tiles[_x + (_y * WIDTH)] = new WallTile(_x * 16, _y * 16, Tile.TILE_WATER);

                    } else {
                       
                        tiles[_x + (_y * WIDTH)] = new FloorTile(_x * 16, _y * 16, Tile.TILE_FLOOR);

                        if (pixelAtual == PLAYER) {
                            Game.player.setX(_x * 16);
                            Game.player.setY(_y * 16);

                        } else if (pixelAtual == PORTA_SAIDA) {
                            PortaSaida porta = new PortaSaida(_x * 16, _y * 16, 32, 32);
                            Game.portaSaida = porta;

                        } else if (pixelAtual == INIMIGO) {
                            Enemy enemy = new Enemy(_x * 16, _y * 16, 16, 16, Entity.ENEMY_EN);
                            Game.entities.add(enemy);

                        } else if (pixelAtual == BOTAO_VERDE) {
                            BotaoVerde botao = new BotaoVerde(_x * 16, _y * 16, 32, 32);
                            Game.itens.add(botao);

                        } else if (pixelAtual == BOTAO_VERMELHO) {
                            BotaoVermelho botao = new BotaoVermelho(_x * 16, _y * 16, 32, 32);
                            Game.itens.add(botao);

                        } else if (pixelAtual == BOTAO_AZUL) {
                            BotaoAzul botao = new BotaoAzul(_x * 16, _y * 16, 32, 32);
                            Game.itens.add(botao);

                        } else if (pixelAtual == ALAVANCA) {
                            Alavanca alavanca = new Alavanca(_x * 16, _y * 16, 32, 32);
                            Game.itens.add(alavanca);

                        } else if (pixelAtual == PLATAFORMA) {
                            Plataforma plataforma = new Plataforma(_x * 16, _y * 16, 32, 32);
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
        int xStart = Camera.x >> 4;
        int yStart = Camera.y >> 4;
        int xFinal = xStart + (GameController.WIDTH >> 4);
        int yFinal = yStart + (GameController.HEIGHT >> 4);

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
