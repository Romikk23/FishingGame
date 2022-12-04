package main;

import entity.Entity;

public class Position {
    GamePanel gp;

    public Position(GamePanel gp) {
        this.gp = gp;
    }

    public void log(Entity entity){ //X9 Y22
        int entityX = entity.worldX/gp.tileSize;
        int entityY = entity.worldY/gp.tileSize;

        System.out.println("\nX: " + entityX +
                           "\nY: " + entityY);
    }

}
