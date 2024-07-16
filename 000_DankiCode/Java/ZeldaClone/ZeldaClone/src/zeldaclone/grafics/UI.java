package zeldaclone.grafics;

import java.awt.Color;
import java.awt.Graphics;

import zeldaclone.main.Game;

public class UI {

    private void renderBarLife(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(10, 10, 200, 30);
        g.setColor(Color.GREEN);
        g.fillRect(10, 10, (int) ((Game.player.life / Game.player.maxLife) * 200), 30);
        g.setColor(Color.WHITE);
        g.setFont(Fonts.Arial20b);
        g.drawString((int) Game.player.life + "/" + (int) Game.player.maxLife, 50, 32);
    }

    private void renderAmmos(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Fonts.Arial20b);
        g.drawString("Balas: " + (int) Game.player.ammos + "/" + (int) Game.player.maxAmmo, 500, 32);
    }

    public void render(Graphics g) {
        renderBarLife(g);
        renderAmmos(g);
    }
}
