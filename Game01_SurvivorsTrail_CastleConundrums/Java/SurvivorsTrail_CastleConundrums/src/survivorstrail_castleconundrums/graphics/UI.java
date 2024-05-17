package survivorstrail_castleconundrums.graphics;

import java.awt.Color;
import java.awt.Graphics;

import survivorstrail_castleconundrums.Itens.Itens;
import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;
import survivorstrail_castleconundrums.world.Camera;

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

    private void renderLevel(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Fonts.Arial20b);
        g.drawString("LEVEL: " + Game.LOAD_LEVEL + "/" + Game.MAX_LEVEL, 500, 32);
    }

    private void renderPortaLib(Graphics g) {
        if (Game.porta_liberada)
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.RED);
        g.setFont(Fonts.Arial20b);
        g.drawString("COMPLETE", 500, 64);
    }

    private void renderInteracts(Graphics g) {
        for (int i = 0; i < Game.itens.size(); i++) {
            Itens item = Game.itens.get(i);
            if (item.showMensagem) {
                g.setColor(Color.BLACK);
                g.setFont(Fonts.Arial20b);
                g.drawString(item.Mensagem, ((int) item.getX() + item.maskX - Camera.x + 32) * GameController.SCALE,
                        ((int) item.getY() + item.maskY - Camera.y + 32) * GameController.SCALE);
            }
        }
    }

    public void render(Graphics g) {
        renderBarLife(g);
        renderLevel(g);
        renderPortaLib(g);
        renderInteracts(g);
    }
}
