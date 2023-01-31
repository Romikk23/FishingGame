package hud;

import item.Item;
import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Hud {
    GamePanel gp;
    Tile[] tile;
    Tile[] numbers;
    Tile[] coins;
    Item[] itemsInventory;
    BufferedImage sell;
    BufferedImage guide;
    BufferedImage g;
    BufferedImage[] buy;
    int invPosX = 4;
    int invPosY = 10;

    public Hud(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        numbers = new Tile[12];
        coins = new Tile[11];
        buy = new BufferedImage[5];
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
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/inventory/hud_inv_count.png")));

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

            sell = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/sell/sellfish.png")));
            guide = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/guide/guide.png")));
            g = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/guide/press_g.png")));


            buy[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/buy/buy_11.png")));
            buy[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/buy/buy_20.png")));
            buy[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/buy/buy_18.png")));
            buy[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/buy/buy_19.png")));
            buy[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/hud/buy/buy_13.png")));


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
        if(gp.player.isSelling) {
            drawSellHud(g2);
        }
        if(gp.player.isBuying) {
            drawBuyHud(g2);
        }
        if(gp.player.isGuiding) {
            drawGuideHud(g2);
        }
        if(gp.player.nearGuide) {
            drawPressG(g2);
        }
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
                g2.drawImage(tile[9].image, x+6, invPosY * gp.tileSize +50, gp.tileSize, gp.tileSize, null);
                if (itemsInventory[pointer].amount < 10) {
                    g2.drawImage(numbers[itemsInventory[pointer].amount].image, x + 7, (invPosY * gp.tileSize) +50, gp.tileSize, gp.tileSize, null);
                } else if (itemsInventory[pointer].amount >= 10 && itemsInventory[pointer].amount < 20) {
                    g2.drawImage(numbers[10].image, x + 12, invPosY * gp.tileSize + 50, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(numbers[itemsInventory[pointer].amount % 10].image, x + 14, invPosY * gp.tileSize + 50, gp.tileSize, gp.tileSize, null);
                } else if (itemsInventory[pointer].amount == 20) {
                    g2.drawImage(numbers[11].image, x + 12, invPosY * gp.tileSize + 50, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(numbers[0].image, x + 14, invPosY * gp.tileSize + 50, gp.tileSize, gp.tileSize, null);
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

    private void drawSellHud(Graphics2D g2) {
        g2.drawImage(sell, 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);

    }

    private void drawGuideHud(Graphics2D g2) {
        g2.drawImage(guide, 2 * gp.tileSize, 2 * gp.tileSize, 12 * gp.tileSize, 8 * gp.tileSize, null);

    }
    private void drawPressG(Graphics2D g2) {
        g2.drawImage(g, (9 * gp.tileSize)-22, (5 * gp.tileSize)-10, gp.tileSize, gp.tileSize, null);
    }

    private void drawBuyHud(Graphics2D g2) {
        switch (gp.player.selectedPosShop) {
            case 0 -> g2.drawImage(buy[0], 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);
            case 1 -> g2.drawImage(buy[1], 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);
            case 2 -> g2.drawImage(buy[2], 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);
            case 3 -> g2.drawImage(buy[3], 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);
            case 4 -> g2.drawImage(buy[4], 3 * gp.tileSize, 2 * gp.tileSize, 9 * gp.tileSize, 6 * gp.tileSize, null);

        }
    }
}

