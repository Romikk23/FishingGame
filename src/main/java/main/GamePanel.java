package main;

import animation.Animation;
import entity.Player;
import hud.Hud;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
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

    //FPS
    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    public Thread gameThread;
    public Player player = new Player(this, keyH);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    TileManager tileManager = new TileManager(this);
    Hud hud = new Hud(this);
    Position pos = new Position(this);
    Animation anim = new Animation(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
        pos.log(player);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2);
        hud.draw(g2);
        anim.drawAnimation(g2);
        g2.dispose();
    }
}
