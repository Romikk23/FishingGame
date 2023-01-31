package main;

public class Log {
    GamePanel gp;

    public Log(GamePanel gp) {
        this.gp = gp;
    }

    public void log() {
        System.out.println("X: " + gp.player.worldX / gp.tileSize + " Y: " + gp.player.worldY / gp.tileSize);
        System.out.println("Hour: " + gp.dayCycle.time.hour + " Minute: " + gp.dayCycle.time.minute);
        System.out.println("Coins: " + gp.player.coins);
        System.out.println("Inventory: " + gp.player.inventory.toString());
        System.out.println("\n");
    }

}
