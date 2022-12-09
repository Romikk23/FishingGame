package hud;

import item.Item;
import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Hud {
    GamePanel gp;
    Tile[] tile;
    Tile[] numbers;
    Tile[] coins;
    Item[] itemsInventory;
    int invPosX = 4;
    int invPosY = 10;

    public Hud(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[9];
        numbers = new Tile[12];
        coins = new Tile[11];
        getTileImage();
    }

    public void update() {
        getInventoryPlayer();

    }

    public void getTileImage() {
        try {
            for (int i = 0; i < 8; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/inventory/hud_inv_" + (i + 1) + ".png")));
            }
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/inventory/hud_inv_selected.png")));

            for (int i = 0; i < 12; i++) {
                numbers[i] = new Tile();
                numbers[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/numbers/numb_" + i + ".png")));
            }

            for (int i = 0; i < 10; i++) {
                coins[i] = new Tile();
                coins[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/coin/" + i + ".png")));
            }

            coins[10] = new Tile();
            coins[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/coin/coin.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInventoryPlayer() {
        if (!gp.player.inventory.isEmpty()) {
            itemsInventory = new Item[gp.player.inventory.size()];
            try {
                for (int i = 0; i < gp.player.inventory.size(); i++) {
                    itemsInventory[i] = gp.player.inventory.get(i + 1);
                    itemsInventory[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/inventory_items/" + itemsInventory[i].id + ".png")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void draw(Graphics2D g2) {
        int tileNum = 0;
        for (int x = invPosX * gp.tileSize; x < invPosX * gp.tileSize + gp.tileSize * 8; x += gp.tileSize) {
            g2.drawImage(tile[tileNum].image, x, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
            if (tileNum == gp.player.selectedPositionInv)
                g2.drawImage(tile[8].image, x, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
            tileNum++;
        }
        drawInventoryItems(g2);
        drawCoins(g2);
    }

    private void drawInventoryItems(Graphics2D g2) {
        update();
        int pointer = 0;
        if (itemsInventory != null) {
            for (int x = (invPosX + 1) * gp.tileSize; x < invPosX * gp.tileSize + gp.tileSize * 7; x += gp.tileSize) {
                if (pointer < itemsInventory.length) {
                    g2.drawImage(itemsInventory[pointer].image, x, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                    drawAmountOfItem(g2, pointer, x);
                    pointer++;
                }
            }
        }
    }

    private void drawAmountOfItem(Graphics2D g2, int pointer, int x) {
        if (itemsInventory[pointer].isCountable) {
            if (itemsInventory[pointer].amount > 1) {
                if (itemsInventory[pointer].amount < 10) {
                    g2.drawImage(numbers[itemsInventory[pointer].amount].image, x - 6, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                } else if (itemsInventory[pointer].amount >= 10 && itemsInventory[pointer].amount < 20) {
                    g2.drawImage(numbers[10].image, x + 3, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(numbers[itemsInventory[pointer].amount % 10].image, x + 3, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                } else if (itemsInventory[pointer].amount == 20) {
                    g2.drawImage(numbers[11].image, x + 3, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(numbers[0].image, x + 3, invPosY * gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }

    private void drawCoins(Graphics2D g2) {
        int x = 14;
        int numbCoins = gp.player.coins;

        g2.drawImage(coins[10].image, (x - 1) * gp.tileSize - 8, 0, gp.tileSize, gp.tileSize, null);

        if (numbCoins >= 0 && numbCoins < 10) {
            g2.drawImage(coins[numbCoins].image, x * gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
        } else if (numbCoins < 100) {
            g2.drawImage(coins[numbCoins / 10].image, x * gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
            g2.drawImage(coins[numbCoins % 10].image, x * gp.tileSize + 22, 0, gp.tileSize, gp.tileSize, null);
        } else if (numbCoins < 1000) {
            g2.drawImage(coins[numbCoins / 100].image, x * gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
            g2.drawImage(coins[(numbCoins / 10) % 10].image, x * gp.tileSize + 22, 0, gp.tileSize, gp.tileSize, null);
            g2.drawImage(coins[numbCoins % 10].image, x * gp.tileSize + 44, 0, gp.tileSize, gp.tileSize, null);
        }
    }
}

