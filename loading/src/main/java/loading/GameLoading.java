package loading;

import javax.swing.*;
import java.awt.*;

public class GameLoading extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 p
    public final int screenHeight = tileSize * maxScreenRow; // 576 p

    //FPS
    final int FPS = 30;

    public Thread gameThread;

    AnimationLoad anim = new AnimationLoad(this);

    public GameLoading() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                repaint();
                delta--;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(anim.loading(g2)) {
            g2.dispose();
        }
    }
}