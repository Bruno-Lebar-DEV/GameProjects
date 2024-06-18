package survivorstrail_castleconundrums.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import survivorstrail_castleconundrums.graphics.UI;

public class GameController extends Canvas implements Runnable, KeyListener {

    public static final int GAMEMODE_MENU = 1;
    public static final int GAMEMODE_GAME = 2;
    public static final int GAMEMODE_PAUSE = 3;

    private Thread thread;
    private boolean isRunning;
    private static int GameMode;

    public static MainMenu mainMenu = new MainMenu();
    public static Game game = new Game();
    public static PauseMenu pauseMenu = new PauseMenu();
    public static UI ui = new UI();

    public static JFrame frame;
    public static BufferedImage bufferedImage;

    public static final int WIDTH = 480;
    public static final int HEIGHT = 320;
    public static final int SCALE = 3;

    public static final boolean DEV_SHOW_COLIDERS = false;
    public static final boolean DEV_SHOW_FPS = false;
    public static final boolean DEV_DEBUG = true;

    public GameController() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.initFrame();

        setGameMode(GAMEMODE_MENU);

        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void initFrame() {
        frame = new JFrame("Survivor's Trail: Castle Conundrums");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static int getGameMode() {
        return GameMode;
    }

    public static void setGameMode(int newGameMode) {
        GameMode = newGameMode;
    }

    public static void closeGame() {
        System.exit(1);
    }

    public void tick() {

        switch (getGameMode()) {
            case GAMEMODE_MENU:
                mainMenu.tick();
                break;

            case GAMEMODE_GAME:
                game.tick();
                break;

            case GAMEMODE_PAUSE:
                pauseMenu.tick();
                break;
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bufferedImage.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (getGameMode() == GAMEMODE_GAME)
            game.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(bufferedImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        if (getGameMode() == GAMEMODE_MENU)
            mainMenu.render(g);
        else if (getGameMode() == GAMEMODE_PAUSE)
            pauseMenu.render(g);
        else {
            ui.render(g);
        }

        bs.show();
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

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amoutOfTicks = 60.0;
        double ns = 1000000000 / amoutOfTicks;
        double delta = 0;

        int FPS = 0;
        double timer = System.currentTimeMillis();

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                FPS++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                String frames = "FPS: " + FPS;
                if (DEV_SHOW_FPS)
                    System.out.println(frames);
                FPS = 0;
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

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (getGameMode() == GAMEMODE_MENU)
                mainMenu.enter = true;

            if (getGameMode() == GAMEMODE_PAUSE)
                pauseMenu.enter = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {

            if (getGameMode() == GAMEMODE_GAME)
                game.pauseGame = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {

            if (getGameMode() == GAMEMODE_MENU)
                mainMenu.move_up = true;

            if (getGameMode() == GAMEMODE_PAUSE)
                pauseMenu.move_up = true;

            if (getGameMode() == GAMEMODE_GAME) {
                Game.player.moveUp = true;
                Game.player.moveDown = false;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {

            if (getGameMode() == GAMEMODE_MENU)
                mainMenu.move_down = true;

            if (getGameMode() == GAMEMODE_PAUSE)
                pauseMenu.move_down = true;

            if (getGameMode() == GAMEMODE_GAME) {
                Game.player.moveDown = true;
                Game.player.moveUp = false;
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (getGameMode() == GAMEMODE_GAME) {
                Game.player.moveLeft = true;
                Game.player.moveRight = false;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (getGameMode() == GAMEMODE_GAME) {
                Game.player.moveRight = true;
                Game.player.moveLeft = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_I)
            if (getGameMode() == GAMEMODE_GAME)
                Game.player.interact = true;

        if (DEV_DEBUG)
            if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                if (getGameMode() == GAMEMODE_GAME)
                    game.nextLevel = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (getGameMode() == GAMEMODE_GAME) {

            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                Game.player.moveUp = false;
            } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                Game.player.moveDown = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                Game.player.moveLeft = false;
            } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Game.player.moveRight = false;
            }

            if (DEV_DEBUG)
                if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                    game.nextLevel = false;

        }
    }

}
