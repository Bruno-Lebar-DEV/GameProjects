package zeldaclone.entities;

import java.awt.image.BufferedImage;

public class Ammo extends Entity {
    public int quantidade = 64;

    public Ammo(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
}
