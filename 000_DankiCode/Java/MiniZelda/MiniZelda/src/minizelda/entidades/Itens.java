package minizelda.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;

import minizelda.sprites.Spritesheet;

public class Itens extends Rectangle {
    public int type; // 1=vida //2=espada

    public Itens(int x, int y, int type) {
        super(x, y, 16, 16);
        this.type = type;
    }

    public boolean isPlayer(int x, int y) {
        if (Entidades.player.intersects(new Rectangle(x, y, 16, 16))) {
            return true;
        }
        return false;
    }

    public void tick() {
        if (isPlayer(x, y)) {
            if (Player.vidaAtual < Player.maxVida) {
                Player.vidaAtual += 1;
                Entidades.itens.remove(this);
            }
        }
    }

    public void render(Graphics g) {
        if (type == 1)
            g.drawImage(Spritesheet.vidaCheia, x, y, 16, 16, null);

    }
}
