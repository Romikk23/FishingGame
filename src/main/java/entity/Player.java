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

    public boolean sleep = false;
    public boolean animSave = false;
    public boolean isFishing = false;
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
        direction = "down";
    }

    public void setWorldCoord(int x, int y) {
        worldX = x;
        worldY = y;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/textures/player/player_right_2.png"));

            flashlight = ImageIO.read(getClass().getResourceAsStream("/textures/world/night/flashlight.png"));


            for (int i = 0; i < 4; i++) {
                fishing_rot[i] = ImageIO.read(getClass().getResourceAsStream("/textures/overlay/fishing_1/fishing_" + (i + 1) + ".png"));
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
                } else {
                    animSave = true;
                }
            }
            keyH.gPressed = false;
        }

        if (keyH.spacePressed) {
            if ((worldX / gp.tileSize >= 7 && worldX / gp.tileSize <= 9) && worldY / gp.tileSize == 29) {
                if (!isFishing) {
                    if (selectedPositionInv <= inventory.size() && (inventory.get(selectedPositionInv).id == 11)) {
                        spriteNum = 1;
                        isFishing = true;
                        fishing.startFishing(inventory.get(selectedPositionInv).id);
                        inventory.minusAmount(selectedPositionInv);
                    }
                } else {
                    if (fishing.bites) {
                        int index = inventory.itemExist(12);
                        if (index == 0) {
                            inventory.add(new Item(12, 1));
                        } else {
                            inventory.addAmount(index);
                        }
                    }
                    fishing.interrupt();
                    isFishing = false;
                }
            }
            keyH.spacePressed = false;
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

//        if(flashlightOn){
//            switch (direction) {
//                case "up" -> g2.drawImage(ImageUtils.rotate(flashlight, 180), screenX-gp.tileSize*2, screenY-gp.tileSize*2, gp.tileSize*5, gp.tileSize*5, null);
//                case "down" -> g2.drawImage(flashlight, screenX-gp.tileSize*2, screenY-gp.tileSize*2, gp.tileSize*5, gp.tileSize*5, null);
//                case "left" -> g2.drawImage(ImageUtils.rotate(flashlight, 90), screenX-gp.tileSize*2, screenY-gp.tileSize*2, gp.tileSize*5, gp.tileSize*5, null);
//                case "right" -> g2.drawImage(ImageUtils.rotate(flashlight, 270), screenX-gp.tileSize*2, screenY-gp.tileSize*2, gp.tileSize*5, gp.tileSize*5, null);
//            }
//        }

    }
}
