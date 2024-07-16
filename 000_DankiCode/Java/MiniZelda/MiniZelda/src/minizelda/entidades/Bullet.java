package minizelda.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import minizelda.Game;
import minizelda.worlds.Blocks;
import minizelda.worlds.World;

public class Bullet extends Rectangle {

    public int dirX = 1;
    public int dirY = 1;
    public int speed = 6;
    public int cadencia = 2;

    public int frames = 0;

    public Bullet(int x, int y, int dirX, int dirY) {
        super(x+4, y+4, 8, 8);
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public boolean isBlock(int x, int y) {
        for (int i = 0; i < World.blocos.size(); i++) {
            Blocks blocoAtual = World.blocos.get(i);
            if (blocoAtual.intersects(new Rectangle(x, y, 12, 12))) {
                return true;
            }
        }
        return false;
    }

    public boolean isInimigo(int x, int y){
        for (int i = 0; i < Entidades.inimigos.size(); i++) {
            Inimigo inimigoAtual = Entidades.inimigos.get(i);
            if (inimigoAtual.intersects(new Rectangle(x, y, 12, 12))) {
                Entidades.inimigos.remove(inimigoAtual);
                Game.POINTS += 1;
                return true;
            }
        } 
        return false;
    }

    public void tick() {
        x += speed * dirX;
        y += speed * dirY;

        frames++;

        if (frames == 60) {
            Player.bullets.remove(this);
            return;
        }

        if (isBlock(x, y)) {
            Player.bullets.remove(this);
            return;
        }

        if (isInimigo(x,y)){
            Player.bullets.remove(this);
            return;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, width, height);
    }
}
