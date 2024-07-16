package minizelda;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import minizelda.entidades.Entidades;
import minizelda.sprites.Spritesheet;
import minizelda.worlds.World;

public class Game extends Canvas implements Runnable, KeyListener {

    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int GAME_WIDTH = 320 - 32;
    public static final int GAME_HEIGHT = 240 - 32;
    public static final int SCALE = 3;
    public static final int FPS = 60;
    public static int POINTS = 0;

    private BufferedImage image;

    public Spritesheet sprites;
    public World world;
    public Entidades entidades;
    public HUD hud;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.initFrame();
        this.addKeyListener(this);
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.sprites = new Spritesheet();
        this.world = new World();
        this.entidades = new Entidades();
        this.hud = new HUD();
    }

    public void initFrame() {
        frame = new JFrame("GAME - Mini Zelda");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void tick() {
        entidades.tick();
        hud.tick();
    }

    public void render() {
        requestFocus();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);
        entidades.render(g);
        hud.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bs.show();
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amoutOfTicks = FPS;
        double ns = 1000000000 / amoutOfTicks;
        double delta = 0;

        int frames = 0;
        double timer = System.currentTimeMillis();

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Entidades.player.shoot = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Entidades.player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            Entidades.player.left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            Entidades.player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Entidades.player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Entidades.player.shoot = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            Entidades.player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            Entidades.player.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            Entidades.player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Entidades.player.down = false;
        }
    }

}
