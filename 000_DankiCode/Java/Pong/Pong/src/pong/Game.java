
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int GAME_FPS = 60;
    public static int WIDTH = 200;
    public static int HEIGHT = 120;
    public static int SCALE = 4;
    public static int SPEED = 2;
    public static boolean isRunning = true;
    public BufferedImage layer;
    public static Player player;
    public static Player2 player2;
    public static Enemy enemy;
    public static Ball ball;
    public static boolean vsCPU = true;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.addKeyListener(this);
        this.layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        player = new Player();
        if (vsCPU) {
            enemy = new Enemy();
        } else {
            player2 = new Player2();
        }
        ball = new Ball();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Game: Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(game).start();
    }

    public void tick() {
        player.tick();

        if (vsCPU) {
            enemy.tick();
        } else {
            player2.tick();
        }

        ball.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = layer.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        player.render(g);
        if (vsCPU) {
            enemy.render(g);
        } else {
            player2.render(g);
        }
        ball.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bs.show();

    }

    @Override
    public void run() {
        requestFocus();

        long lastTime = System.nanoTime();
        double amoutOfTicks = GAME_FPS;
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

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.down = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.down = false;
        }

    }
}
