package animation;

import main.GamePanel;

import java.awt.*;

public class Animation {
    GamePanel gp;
    int spriteCounter = 0;

    public Animation(GamePanel gp) {
        this.gp = gp;
    }
    public void drawAnimation(Graphics2D g2){
        skipDay(g2);
    }
    private void skipDay(Graphics2D g2) {
        if(gp.player.sleep) {
            for(int k = 0; k < spriteCounter; k++) {
                for (int j = 0; j < gp.maxScreenCol; j++) {
                    g2.setColor(Color.black);
                    g2.fillRect(j * gp.tileSize, k * gp.tileSize, gp.tileSize, gp.tileSize);
                    g2.fillRect(j * gp.tileSize, (gp.maxScreenRow-k) * gp.tileSize, gp.tileSize, gp.tileSize);
                }
            }
            spriteCounter++;
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(spriteCounter == gp.maxScreenRow){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gp.player.sleep = false;
                spriteCounter = 0;
            }
        }

    }
}
