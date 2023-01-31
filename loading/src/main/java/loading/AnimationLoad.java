package loading;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AnimationLoad {
    GameLoading gl;

    int screenX;
    int screenY;
    int i = 0;
    boolean pos = false;

    public AnimationLoad(GameLoading gl) {
        this.gl = gl;
        screenX = gl.screenWidth / 2 - (gl.tileSize / 2);
        screenY = gl.screenHeight / 2 - (gl.tileSize / 2);
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
        if(i < 650) {
            if (i % 10 == 0) {
                pos = !pos;
            }
            g2.drawImage(bi, i, 576/2, 128, 128, null);
            i += 3;
        } else {
            g2.drawImage(bi, 665, 576/2, 128, 128, null);
        }
        return true;
    }
}

