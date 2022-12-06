package animation;

import main.GamePanel;
import tile.Tile;
import java.awt.*;

public class Animation {
    GamePanel gp;
    int spriteCounter = 0;
    Tile[] tile;
    int screenX;
    int screenY;

    public Animation(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[3];
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2  - (gp.tileSize/2);
        getTileImage();
    }

    public void drawAnimation(Graphics2D g2) {
        skipDay(g2);
    }

    private void getTileImage() {

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
                gp.player.direction = "down";
                spriteCounter = 0;
            }
        }

    }
}
