package entity;

import entity.processes.Fishing;
import item.Inventory;
import item.Item;
import main.GamePanel;
import main.KeyHandler;

import main.Save;
import world.Time;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    Time time;

    public final int screenX;
    public final int screenY;

    BufferedImage[] fishing_rot;
    BufferedImage flashlight;
    Fishing fishing;

    public Inventory inventory = new Inventory();
    public int coins;
    public int selectedPositionInv;
    public int selectedPosShop;

    public boolean sleep = false;
    public boolean animSave = false;
    public boolean isFishing = false;
    public boolean isSelling = false;
    public boolean isBuying = false;
    public boolean flashlightOn = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.fishing = new Fishing(this);
        time = Time.getInstance();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        fishing_rot = new BufferedImage[4];
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        coins = 123;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 22;
        speed = 4;
        SPEEDANIMATION = 15;
        selectedPositionInv = 1;
        selectedPosShop = 0;
        direction = "down";
    }

    public void setWorldCoord(int x, int y) {
        worldX = x;
        worldY = y;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/player/player_right_2.png")));

            flashlight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/world/night/flashlight.png")));


            for (int i = 0; i < 4; i++) {
                fishing_rot[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/overlay/fishing_1/fishing_" + (i + 1) + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > SPEEDANIMATION) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            if (isFishing) {
                isFishing = false;
                fishing.interrupt();
            }
            isSelling = false;
            isBuying = false;
        }
        if (keyH.selectItemPressed) {
            selectedPositionInv = keyH.selectedInventoryItem;
        }

        if (keyH.gPressed) {
            if (worldX / gp.tileSize == 21 && worldY / gp.tileSize == 29) {
                Save save = new Save(this);
                save.makeSave();
                if (time.hour >= 21 || time.hour <= 6) {
                    sleep = true;
                    if (inventory.itemExist(11) == 0) {
                        inventory.add(new Item(11, 10));
                    } else {
                        inventory.addAmount(inventory.itemExist(11), 10);
                    }
                } else {
                    animSave = true;
                }
            }
            if ((worldX / gp.tileSize == 17 && worldY / gp.tileSize == 16) || (worldX / gp.tileSize == 16 && worldY / gp.tileSize == 16)) {
                isSelling = true;
            }
            if ((worldY / gp.tileSize == 12) && (worldX / gp.tileSize >= 22 && worldX / gp.tileSize <= 29)) {
                isBuying = true;
            }
            keyH.gPressed = false;
        }

        if (keyH.spacePressed) {
            if ((worldX / gp.tileSize >= 7 && worldX / gp.tileSize <= 9) && worldY / gp.tileSize == 29) {
                if (!isFishing) {
                    if (selectedPositionInv <= inventory.size() && ((inventory.get(selectedPositionInv).id == 11 || inventory.get(selectedPositionInv).id == 18 || inventory.get(selectedPositionInv).id == 19 || inventory.get(selectedPositionInv).id == 20))) {
                        spriteNum = 1;
                        isFishing = true;
                        fishing.startFishing(inventory.get(selectedPositionInv).id);
                        inventory.minusAmount(selectedPositionInv);
                    }
                } else {
                    if (fishing.bites) {
                        inventory.addItemOrAddAmount(fishing.idCatch);
                    }
                    fishing.interrupt();
                    isFishing = false;
                }
            }

            if(isSelling) {
                if(inventory.size() >= selectedPositionInv) {
                    int itemId = inventory.get(selectedPositionInv).id;

                    switch (itemId) {
                        case 12 -> {
                            inventory.minusAmount(selectedPositionInv);
                            coins += 3;
                        }
                        case 14 -> {
                            inventory.minusAmount(selectedPositionInv);
                            coins += 5;
                        }
                        case 15 -> {
                            inventory.minusAmount(selectedPositionInv);
                            coins += 15;
                        }
                        case 16 -> {
                            inventory.minusAmount(selectedPositionInv);
                            coins += 50;
                        }
                        case 17 -> {
                            inventory.minusAmount(selectedPositionInv);
                            coins += 100;
                        }
                    }
                }
            }

            if(isBuying) {
                switch (selectedPosShop) {
                    case 0 -> {
                        if(coins >= 2) {
                            if(inventory.addItemOrAddAmount(11)) {
                                coins -= 2;
                            }
                        }
                    }
                    case 1 -> {
                        if(coins >= 4) {
                            if(inventory.addItemOrAddAmount(20)) {
                                coins -= 4;
                            }
                        }
                    }
                    case 2 -> {
                        if(coins >= 10) {
                            if(inventory.addItemOrAddAmount(18)) {
                                coins -= 10;
                            }
                        }
                    }
                    case 3 -> {
                        if(coins >= 30) {
                            if(inventory.addItemOrAddAmount(19)) {
                                coins -= 30;
                            }
                        }
                    }
                    case 4 -> {
                        if(coins >= 800) {
                            if(inventory.itemExist(13) == -1) {
                                if (inventory.addItemOrAddAmount(13)) {
                                    inventory.remove(10);
                                    coins -= 800;
                                }
                            }
                        }
                    }
                }
            }
            keyH.spacePressed = false;
        }
        if(isBuying) {
            if (keyH.arrowLeftPressed) {
                if (selectedPosShop > 0) {
                    selectedPosShop--;
                } else {
                    selectedPosShop = 4;
                }
                keyH.arrowLeftPressed = false;
            }
            if (keyH.arrowRightPressed) {
                if (selectedPosShop < 4) {
                    selectedPosShop++;
                } else {
                    selectedPosShop = 0;
                }
                keyH.arrowRightPressed = false;
            }
        }
        flashlightOn = time.hour >= 21 || time.hour <= 7;

        /* GOD MODE START */

        if (selectedPositionInv <= inventory.size() && inventory.get(selectedPositionInv).isCountable) {
            if (keyH.plusPressed) {
                inventory.addAmount(selectedPositionInv);
                keyH.plusPressed = false;
            }
            if (keyH.minusPressed) {
                inventory.minusAmount(selectedPositionInv);
                keyH.minusPressed = false;
            }
        } else {
            if (keyH.plusPressed) {
                time.setTime(time.hour+1, time.minute);
                keyH.plusPressed = false;
            }
            if (keyH.minusPressed) {
                time.setTime(time.hour-1, time.minute);
                keyH.minusPressed = false;
            }
        }
        /* GOD MODE END */

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if (isFishing) {
            for (int i = 0; i < 2; i++) {
                g2.drawImage(fishing_rot[i], screenX, screenY + (i * gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
            if (fishing.bites) {
                g2.drawImage(fishing_rot[3], screenX, screenY + (2 * gp.tileSize), gp.tileSize, gp.tileSize, null);
            } else {
                g2.drawImage(fishing_rot[2], screenX, screenY + (2 * gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
