package main;

import animation.Animation;
import entity.Player;
import hud.Hud;
import item.Item;
import tile.TileManager;
import world.DayCycle;
import world.Time;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 p
    public final int screenHeight = tileSize * maxScreenRow; // 576 p

    // WORLD SETTINGS
    public final int maxWorldCol = 43;
    public final int maxWorldRow = 38;

    private boolean firstStart = true;

    //FPS
    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Time time = Time.getInstance(21, 55);
    public Thread gameThread;
    public Player player = new Player(this, keyH);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    TileManager tileManager = new TileManager(this);
    Hud hud = new Hud(this);
    Log logging = new Log(this);
    Animation anim = new Animation(this);
    DayCycle dayCycle = new DayCycle(this);
    Save save = new Save(this, player);
    SoundPlayer soundPlayer = new SoundPlayer();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        if (!save.getSave()) {
            player.coins = 15;
            player.inventory.add(new Item(10, 0));
            player.worldX = tileSize * 9;
            player.worldY = tileSize * 10;
        }
        soundPlayer.playMusic("music.wav");
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long lastMillis = System.currentTimeMillis();
        long musicMillis = System.currentTimeMillis();
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            if (System.currentTimeMillis() - lastMillis >= 2000) {
                lastMillis = System.currentTimeMillis();
                time.addMinute();
//                logging.log();
            }
            if (System.currentTimeMillis() - musicMillis >= 105*1000) {
                musicMillis = System.currentTimeMillis();
                soundPlayer.playMusic("music.wav");
            }
        }
    }

    public void update() {
        player.update();
        save.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2);
        hud.draw(g2);
        anim.drawAnimation(g2);
        dayCycle.draw(g2);
        g2.dispose();
    }
}
