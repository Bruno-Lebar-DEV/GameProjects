package zeldaclone.grafics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {
    public static final Font Arial20b = new Font("arial", Font.BOLD, 20);
    public static final Font Arial22b = new Font("arial", Font.BOLD, 22);
    public static final Font Arial38b = new Font("arial", Font.BOLD, 38);

    public static Font Pixel30; 

    public static  InputStream streamPixel = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/pixelart.ttf");

    public Fonts(){
        try {
            Pixel30 = Font.createFont(Font.TRUETYPE_FONT, streamPixel).deriveFont(30);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

}
