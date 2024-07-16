package zeldaclone.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import zeldaclone.grafics.Fonts;

public class Menu {

    public String[] options = {};

    public String[] optionMenu = { "New Game", "Load Game", "Config", "Close Game" };
    public String[] optionGame = { "Continue", "Reset Level", "Config", "Back to Menu" };
    public String[] optionConfig = { "Music", "Exit" };

    public int menuAtual = 0; // 0=menu, 1=Game, 2=Config
    public int menuAnt = 0; // 0=menu, 1=Game, 2=Config

    public int currentOption = 0;
    public int minOption = 0;
    public int maxOption = 0;
    public boolean enter, up, down = false;

    public static boolean gameSalvo = false;

    public void tick() {

        switch (menuAtual) {
            case 0:
                options = optionMenu;
                break;
            case 1:
                options = optionGame;
                break;
            case 2:
                options = optionConfig;
                break;
        }

        maxOption = options.length - 1;

        if (up) {
            up = false;
            currentOption--;
            if (currentOption < 0)
                currentOption = maxOption;
        }

        if (down) {
            down = false;
            currentOption++;
            if (currentOption > maxOption)
                currentOption = 0;
        }

        if (enter) {
            enter = false;

            switch (options[currentOption]) {
                case "New Game":
                    this.currentOption = 0;
                    Game.newGame();
                    menuAtual = 1;
                    break;

                case "Load Game":
                    this.currentOption = 0;
                    if (SaveLoad.loadGame())
                        menuAtual = 1;
                    break;

                case "Continue":
                    this.currentOption = 0;
                    Game.gameState = "NORMAL";
                    break;

                case "Reset Level":
                    this.currentOption = 0;
                    Game.resetGame();
                    break;

                case "Config":
                    this.currentOption = 0;
                    this.menuAnt = menuAtual;
                    this.menuAtual = 2;
                    break;

                case "Music":
                    if (Game.musica) {
                        Game.musica = false;
                        Game.musicBackground.pause();
                    } else if (Game.musicBackground.isResumed()) {
                        Game.musica = true;
                        Game.musicBackground.resume();
                    }
                    break;

                case "Exit":
                    this.currentOption = 0;
                    this.menuAtual = menuAnt;
                    this.menuAnt = menuAtual;
                    break;

                case "Back to Menu":
                    this.currentOption = 0;
                    this.menuAtual = 0;
                    break;

                case "Close Game":
                    Game.closeGame();
                    break;

            }

        }
    }

    public void render(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        g.setColor(Color.WHITE);
        g.setFont(Fonts.Pixel30);
        g.drawString("MENU", ((Game.WIDTH * Game.SCALE) / 2) - ((6 * 19) / 2),
                ((Game.HEIGHT * Game.SCALE) / 2) - 140);

        int posicao = 0;

        for (int i = 0; i < options.length; i++) {
            String text = options[i];

            if (text == "Music") {
                if (Game.musica)
                    text = "Music: ON";
                else
                    text = "Music: OFF";
            }

            if (currentOption == i) {
                posicao++;
                g.setFont(Fonts.Arial22b);
                g.drawString("> " + text, ((Game.WIDTH * Game.SCALE) / 2) - 55,
                        ((Game.HEIGHT * Game.SCALE) / 2) - 120 + (posicao * 50));
            } else {
                posicao++;
                g.setFont(Fonts.Arial20b);
                g.drawString(text, ((Game.WIDTH * Game.SCALE) / 2) - 40,
                        ((Game.HEIGHT * Game.SCALE) / 2) - 120 + (posicao * 50));
            }
        }

        if (gameSalvo) {
            gameSalvo = false;
            // Mostra aviso de game salvo
        }
    }
}
