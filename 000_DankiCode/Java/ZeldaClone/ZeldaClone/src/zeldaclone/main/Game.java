package zeldaclone.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import zeldaclone.entities.*;
import zeldaclone.grafics.*;
import zeldaclone.world.*;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private Thread thread;
    private boolean isRunning = true;
    private boolean showMessageGameOver = false;
    private boolean restartGame = false;
    private int framesGameOver = 0;

    private static int MAX_LEVEL = 3;
    public static int CUR_LEVEL = 1;

    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;
    public static final boolean SHOW_COLIDERS = false;

    public static Random rand = new Random();;
    public static JFrame frame;
    public static BufferedImage image;
    public static Spritesheet spritesheet;
    public static Player player;
    public static ArrayList<Entity> entities;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Bullet> bullets;
    public static String gameState = "MENU";
    public static World world;
    public static UI ui;
    public static boolean iniciaJogo = true;
    public static boolean musica = true;
    public static Sound musicBackground;
    public Menu menu;
    public static int mX;
    public static int mY;

    public Game() {
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.initFrame();

        // musica de fundo

        musicBackground = new Sound("/sounds/musicBackgroud.wav");
        musicBackground.loop();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("/entidades/spritesheet.png");
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        player = new Player(0, 0, 16, 16, Entity.PLAYER_EN);
        world = new World("/level1.png");
        ui = new UI();
        menu = new Menu();

        entities.add(player);
    }

    public static void newGame() {
        gameState = "NORMAL";
        CUR_LEVEL = 1;

        rand = new Random();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("/entidades/spritesheet.png");
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        world = new World("/level1.png");
        ui = new UI();

        entities.add(player);
    }

    public static void nextLevelGame() {
        CUR_LEVEL++;
        if (CUR_LEVEL > MAX_LEVEL) {
            CUR_LEVEL = MAX_LEVEL;
        }

        double lifeAtual = player.life;
        int balasAtual = player.ammos;

        resetGame();

        player.life = lifeAtual;
        player.ammos = balasAtual;
    }

    public static void resetGame() {
        gameState = "NORMAL";

        String newWorld = "level" + CUR_LEVEL + ".png";

        rand = new Random();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("/entidades/spritesheet.png");
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        world = new World("/" + newWorld);
        ui = new UI();

        entities.add(player);
    }

    public static void closeGame() {
        System.exit(1);
    }

    public void initFrame() {
        frame = new JFrame("Game: Zelda Clone");
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

        if (gameState != "GAME_OVER") {
            this.restartGame = false;
        }

        if (gameState == "NORMAL") {
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).tick();
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).tick();
            }

            if (enemies.size() == 0) {
                // avançar proximo nivel
                nextLevelGame();
            }
        } else if (gameState == "GAME_OVER") {
            this.framesGameOver++;
            if (this.framesGameOver == 30) {
                this.framesGameOver = 0;
                if (showMessageGameOver)
                    this.showMessageGameOver = false;
                else
                    this.showMessageGameOver = true;
            }

            if (restartGame) {
                restartGame = false;
                resetGame();
            }
        } else if (gameState == "MENU") {
            menu.tick();
        }

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        /* Renderiza o jogo */
        // Graphics2D g2 = (Graphics2D) g;
        world.render(g);
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
        /* */
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        ui.render(g);
        if (gameState == "GAME_OVER") {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
            g.setColor(Color.WHITE);
            g.setFont(Fonts.Arial38b);
            g.drawString("GAME OVER!", ((WIDTH * SCALE) / 2) - 100, ((HEIGHT * SCALE) / 2) - 30);
            g.setFont(new Font("arial", Font.BOLD, 28));
            if (showMessageGameOver)
                g.drawString("Pressione Enter para para continuar", ((WIDTH * SCALE) / 2) - 200,
                        ((HEIGHT * SCALE) / 2) + 30);
        } else if (gameState == "MENU") {
            menu.render(g);
        }

        bs.show();
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amoutOfTicks = 60.0;
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
        // metodos aqui

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("KeyPressed() -> " + KeyEvent.getKeyText(e.getKeyCode()));
        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shoot = true;
            player.tecladoShoot = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            restartGame = true;
            if (gameState == "MENU")
                menu.enter = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            gameState = "MENU";
        }

        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            player.moveUp = true;

            if (gameState == "MENU")
                menu.up = true;

        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.moveDown = true;

            if (gameState == "MENU")
                menu.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.moveLeft = true;
            player.moveRight = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.moveLeft = false;
            player.moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shoot = false;
            player.tecladoShoot = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            player.moveUp = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.moveDown = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.moveLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.moveRight = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.shoot = true;
        player.mouseShoot = true;
        player.mouseX = (e.getX() / SCALE);
        player.mouseY = (e.getY() / SCALE);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        player.shoot = false;
        player.mouseShoot = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

}
