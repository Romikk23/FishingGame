package entity.processes;

import entity.Player;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Fishing implements Runnable{
    Player player;
    public boolean bites;
    int time;

    public Fishing(Player player) {
        this.player = player;
        this.bites = false;
    }
    @Override
    public void run() {
        Random rand = new Random();
        time = rand.nextInt((30*1000 - 15*1000) + 1) + 15*1000;
        // sleep time
        bites = true;
        //sleep 15*1000
        bites = false;
    }
}
