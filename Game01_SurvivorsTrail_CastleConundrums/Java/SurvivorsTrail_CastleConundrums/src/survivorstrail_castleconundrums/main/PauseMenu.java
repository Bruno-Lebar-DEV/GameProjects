package survivorstrail_castleconundrums.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import survivorstrail_castleconundrums.graphics.Fonts;

public class PauseMenu {

    private int currentOption = 0;
    private int maxOption = 0;

    public boolean enter = false;
    public boolean move_up = false;
    public boolean move_down = false;

    public String[] options = { "Continue", "Restart", "Save", "Exit to Menu" };

    public void tick() {
        maxOption = options.length - 1;

        if (move_up) {
            move_up = false;
            currentOption--;
            if (currentOption < 0)
                currentOption = maxOption;
        }

        if (move_down) {
            move_down = false;
            currentOption++;
            if (currentOption > maxOption)
                currentOption = 0;
        }

        if (enter) {
            enter = false;

            switch (options[currentOption]) {

                case "Continue":
                    GameController.setGameMode(GameController.GAMEMODE_GAME);
                    break;

                case "Restart":
                    GameController.setGameMode(GameController.GAMEMODE_GAME);
                    Game.resetLevel();
                    break;

                case "SAVE GAME":
                    Game.saveGame();
                    break;

                case "Exit to Menu":
                    GameController.setGameMode(GameController.GAMEMODE_MENU);
                    break;

            }

            this.currentOption = 0;

        }

    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(100, 0, 0, 150));
        g2.fillRect(0, 0, GameController.WIDTH * GameController.SCALE, GameController.HEIGHT * GameController.SCALE);

        g.setColor(Color.WHITE);
        g.setFont(Fonts.Arial38b);
        g.drawString("MENU", 40,
                ((GameController.HEIGHT * GameController.SCALE) / 2) - 140);

        int posicao = 0;

        for (int i = 0; i < options.length; i++) {
            String text = options[i];

            if (currentOption == i) {
                posicao++;
                g.setFont(Fonts.Arial22b);
                g.drawString("> " + text, 50,
                        ((GameController.HEIGHT * GameController.SCALE) / 2) - 120 + (posicao * 50));
            } else {
                posicao++;
                g.setFont(Fonts.Arial20b);
                g.drawString(text, 60,
                        ((GameController.HEIGHT * GameController.SCALE) / 2) - 120 + (posicao * 50));
            }
        }

    }
}
