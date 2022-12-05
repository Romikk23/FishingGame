package entity.processes;

import entity.Player;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Fishing implements Runnable{
    Player player;
    public boolean bites;
    int time;
    Thread fishingThread;

    public Fishing(Player player) {
        this.player = player;
        this.bites = false;
    }
    public void startFishing() {
        fishingThread = new Thread(this);
        fishingThread.start();
    }

    @Override
    public void run() {
        try {
            if(!bites) {
                Random rand = new Random();
                time = rand.nextInt((30 * 1000 - 15 * 1000) + 1) + 15 * 1000;
                Thread.sleep(2000);
                bites = true;
                Thread.sleep(5000);
                bites = false;
                restart();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        bites = false;
        fishingThread.interrupt();
    }

    private void restart() {
        fishingThread.start();
    }
}
