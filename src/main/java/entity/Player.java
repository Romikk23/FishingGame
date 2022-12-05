package entity;
import animation.Animation;
import entity.processes.Fishing;
import item.Inventory;
import item.Item;
import main.GamePanel;
import main.KeyHandler;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    Tile[] fishing_rot;
    Fishing fishing;

    public Inventory inventory = new Inventory();
    public int selectedPositionInv;

    public boolean sleep = false;
    public boolean isFishing = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.fishing = new Fishing(this);
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2  - (gp.tileSize/2);
        fishing_rot = new Tile[4];
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize*8;
        worldY = gp.tileSize*22;
        speed = 4;
        SPEEDANIMATION = 15;
        selectedPositionInv = 1;
        inventory.add(new Item(10));
        inventory.add(new Item(11, 10));
        direction = "down";
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

            for (int i = 0; i < 4; i++) {
                fishing_rot[i] = new Tile();
                fishing_rot[i].image = ImageIO.read(getClass().getResourceAsStream("/textures/overlay/fishing_1/fishing_"+ (i+1) +".png"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
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
            if(isFishing) {
                isFishing = false;
                fishing.interrupt();
            }
        }
        if(keyH.selectItemPressed) {
            selectedPositionInv = keyH.selectedInventoryItem;
        }

        if(keyH.gPressed) {
            if(worldX/gp.tileSize == 21 && worldY/gp.tileSize == 29){
                sleep = true;
            }
        }

        if(keyH.spacePressed) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isFishing) {
                if ((worldX / gp.tileSize >= 7 && worldX / gp.tileSize <= 9) && worldY / gp.tileSize == 29) {
                    if (selectedPositionInv <= inventory.size() && (inventory.get(selectedPositionInv).id == 11)) {
                        spriteNum = 1;
                        inventory.minusAmount(selectedPositionInv);
                        isFishing = true;
                        fishing.startFishing(inventory.get(selectedPositionInv).id);
                    }
                }
            } else {
                if(fishing.bites){
                    int index = inventory.itemExist(12);
                    if(index == 0) {
                        inventory.add(new Item(12, 1));
                    } else {
                        inventory.addAmount(index);
                    }
                }
                fishing.interrupt();
                isFishing = false;
            }
        }

        /** GOD MODE START **/

        if(selectedPositionInv <= inventory.size() && inventory.get(selectedPositionInv).isCountable) {
            sc++;
            if (sc > 10) {
                if (keyH.plusPressed) {
                    inventory.addAmount(selectedPositionInv);
                }
                if (keyH.minusPressed) {
                    inventory.minusAmount(selectedPositionInv);
                }
                sc = 0;
            }
        }

        /** GOD MODE END **/

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if(isFishing){
            for(int i = 0; i < 2; i++) {
                g2.drawImage(fishing_rot[i].image, screenX, screenY+(i*gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
            if(fishing.bites){
                g2.drawImage(fishing_rot[3].image, screenX, screenY+(2*gp.tileSize), gp.tileSize, gp.tileSize, null);
            } else {
                g2.drawImage(fishing_rot[2].image, screenX, screenY+(2*gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
        }

    }
}
