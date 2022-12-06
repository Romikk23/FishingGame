package entity.processes;

import entity.Player;
import java.util.Random;

public class Fishing implements Runnable{
    Player player;
    public boolean bites;
    int idBite;
    int idCatch;

    int time;
    Thread fishingThread;

    public Fishing(Player player) {
        this.player = player;
        this.bites = false;
    }
    public void startFishing(int idBite) {
        fishingThread = new Thread(this);
        fishingThread.start();
        this.idBite = idBite;
        idCatch = 0;
    }

    @Override
    public void run() {
        try {
            if(!bites) {
                Random rand = new Random();
                switch (idBite) {
                    case 11 -> time = rand.nextInt((30 * 1000 - 15 * 1000) + 1) + 15 * 1000;
                    default -> time = 1000;
                }
                Thread.sleep(time);
                bites = true;
                idCatch = 12;
                Thread.sleep(4000);
                bites = false;
                restart();
            }
        } catch (InterruptedException ignored) {

        }
    }

    public void interrupt() {
        bites = false;
        if(fishingThread.isAlive()) {
            fishingThread.interrupt();
        }
    }

    private void restart() {
        fishingThread.start();
    }
}
