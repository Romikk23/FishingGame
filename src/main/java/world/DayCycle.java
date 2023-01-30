package world;

import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class DayCycle {
    GamePanel gp;
    Tile night;
    public Time time;


    public DayCycle(GamePanel gp) {
        this.gp = gp;
        night = new Tile();
        time = Time.getInstance();
        getTileImage();
    }

    private void getTileImage() {
        try {
            night.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/world/night/night.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (time.hour >= 21 || time.hour <= 7) {
            for (int i = 0; i < gp.maxScreenCol; i++) {
                for (int j = 0; j < gp.maxScreenRow; j++) {
                    g2.drawImage(night.image, i * gp.tileSize, j * gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
