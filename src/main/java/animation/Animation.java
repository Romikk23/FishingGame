package animation;

import main.GamePanel;
import tile.Tile;
import world.Time;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Objects;

public class Animation {
    GamePanel gp;
    Time time;
    int spriteCounter = 0;
    Tile[] tile;
    int screenX;
    int screenY;
    int i = 0;
    boolean pos = false;

    public Animation(GamePanel gp) {
        this.gp = gp;
        time = Time.getInstance();
        tile = new Tile[3];
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        getTileImage();
    }

    public void drawAnimation(Graphics2D g2) {
        skipDay(g2);
        animSave(g2);
    }

    private void getTileImage() {

    }

    private void skipDay(Graphics2D g2) {
        if (gp.player.sleep) {
            animation(g2);
            if (spriteCounter == gp.maxScreenRow) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gp.player.sleep = false;
                gp.player.direction = "down";
                time.setTime(8, 0);
                spriteCounter = 0;
            }
        }

    }

    public boolean loading(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, 600, 600);
        BufferedImage bi = null;
        try {
            if(pos){
                bi = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_right_1.png")));
            } else {
                bi = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_right_2.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(i < 768) {
            if (i % 30 == 0) {
                pos = !pos;
            }
            g2.drawImage(bi, i, 576/2, 128, 128, null);
            i += 3;
            return true;
        }
        return false;
    }

    private void animSave(Graphics2D g2) {
        if (gp.player.animSave) {
            animation(g2);
            if (spriteCounter == gp.maxScreenRow) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gp.player.animSave = false;
                spriteCounter = 0;
            }
        }

    }

    private void animation(Graphics2D g2) {
        for (int k = 0; k < spriteCounter; k++) {
            for (int j = 0; j < gp.maxScreenCol; j++) {
                g2.setColor(Color.black);
                g2.fillRect(j * gp.tileSize, k * gp.tileSize, gp.tileSize, gp.tileSize);
                g2.fillRect(j * gp.tileSize, (gp.maxScreenRow - k) * gp.tileSize, gp.tileSize, gp.tileSize);
            }
        }
        spriteCounter++;
        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
