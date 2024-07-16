package minizelda;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import minizelda.entidades.Player;
import minizelda.sprites.Spritesheet;

public class HUD {
    public HUD() {

    }

    public void tick() {

    }

    public void render(Graphics g) {
        final int xVida = 6;
        final int yVida = 6;

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.white);
        g.drawString("Pontos: " + Game.POINTS, Game.WIDTH - 108, 20);

        for (int i = 0; i < Player.maxVida; i++) {
            if (i <= Player.vidaAtual - 1) {
                g.drawImage(Spritesheet.vidaCheia, xVida + (i * 18), yVida, 16, 16, null);
            } else {
                g.drawImage(Spritesheet.vidaVazia, xVida + (i * 18), yVida, 16, 16, null);
            }
        }

    }
}
