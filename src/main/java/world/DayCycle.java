package world;

import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class DayCycle {
    GamePanel gp;
    Tile night;
    Time time;


    public DayCycle(GamePanel gp) {
        this.gp = gp;
        night = new Tile();
        time = Time.getInstance();
        getTileImage();
    }

    private void getTileImage() {
        try {
            night.image = ImageIO.read(getClass().getResourceAsStream("/textures/world/night/night.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        //System.out.println("       Another time: " + time.toString());
        if(time.hour >= 21 || time.hour <= 7) {
            for(int i = 0; i < gp.maxScreenCol*2; i++) {
                for(int j = 0; j < gp.maxScreenRow*2; j++) {
                    if(i >= 11 && j >= 7 && i <= 20 && j <= 16) {

                    }
                    else {
                        g2.drawImage(night.image, i*(gp.tileSize-24), j*(gp.tileSize-24), gp.tileSize-24, gp.tileSize-24, null);
                    }
                }
            }
        }
    }
}
