package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[1000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/sand.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/tree.png")));
            tile[2].collision = true;


            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/unknown.png")));
            tile[4].collision = true;

            /*      WATER      */


            tile[10] = new Tile();
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_up.png")));
            tile[10].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_left_up.png")));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_left.png")));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_left_bottom.png")));
            tile[13].collision = true;

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_bottom.png")));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_right_bottom.png")));
            tile[15].collision = true;

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_right.png")));
            tile[16].collision = true;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_right_up.png")));
            tile[17].collision = true;

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water.png")));
            tile[18].collision = true;

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_angle_up_left.png")));
            tile[19].collision = true;

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_angle_bottom_left.png")));
            tile[20].collision = true;

            tile[21] = new Tile();
            tile[21].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_angle_bottom_right.png")));
            tile[21].collision = true;

            tile[22] = new Tile();
            tile[22].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/water/water_with_grass_angle_up_right.png")));
            tile[22].collision = true;

            /*      House_1      */

            for (int i = 30; i < 50; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/house_1/house_" + (i - 29) + ".png")));
                tile[i].collision = true;
            }

            /*      Fish_Shop      */

            for (int i = 50; i < 70; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/fish_shop/fish_shop_" + (i - 49) + ".png")));
                tile[i].collision = true;
            }

            /*      Pier      */

            for (int i = 70; i < 78; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/pier/pier_" + (i - 69) + ".png")));
                tile[i].collision = false;
            }

            tile[76].collision = true;
            tile[77].collision = true;

            /*      Cart_1      */

            for (int i = 78; i < 84; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/cart_1/cart_" + (i - 77) + ".png")));
                tile[i].collision = true;
            }

            /*      Cart_2      */

            for (int i = 84; i < 90; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/cart_2/cart_" + (i - 83) + ".png")));
                tile[i].collision = true;
            }

            /*      Cart_3      */

            for (int i = 90; i < 96; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/cart_3/cart_" + (i - 89) + ".png")));
                tile[i].collision = true;
            }

            /*      Cart_4      */

            for (int i = 96; i < 102; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/block/cart_4/cart_" + (i - 95) + ".png")));
                tile[i].collision = true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/map/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (row < gp.maxWorldRow) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[row]);
                    mapTileNum[col][row] = num;
                    row++;
                }
                if (row == gp.maxWorldRow) {
                    row = 0;
                    col++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldRow * gp.tileSize;
            int worldY = worldCol * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldRow++;

            if (worldRow == gp.maxWorldRow) {
                worldRow = 0;
                worldCol++;
            }
        }
    }
}
