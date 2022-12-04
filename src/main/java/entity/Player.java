package entity;
import animation.Animation;
import item.Inventory;
import item.Item;
import main.GamePanel;
import main.KeyHandler;

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

    public Inventory inventory = new Inventory();
    public int selectedPositionInv;
    public boolean sleep = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2  - (gp.tileSize/2);

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
        inventory.add(new Item(11, 1));
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
        }
        if(keyH.selectItemPressed) {
            selectedPositionInv = keyH.selectedInventoryItem;
        }

        if(keyH.gPressed) {
            if(worldX/gp.tileSize == 21 && worldY/gp.tileSize == 29){
                sleep = true;
            }
        }

        /** GOD MODE START **/

        if(selectedPositionInv <= inventory.size() && inventory.get(selectedPositionInv).isCountable) {
            sc++;
            if (sc > 10) {
                if (selectedPositionInv <= inventory.size()) {
                    if (keyH.plusPressed) {
                        inventory.addAmount(selectedPositionInv);
                    }
                    if (keyH.minusPressed) {
                        inventory.minusAmount(selectedPositionInv);
                    }
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
    }
}
