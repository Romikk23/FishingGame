package entity.processes;

import entity.Player;

import java.util.Random;

public class Fishing implements Runnable {
    Player player;
    public boolean bites;
    int idBite;
    public int idCatch;
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
            if (!bites) {
                int time = getTime();
                Thread.sleep(time);
                bites = true;
                idCatch = getCatch(idBite);
                Thread.sleep(3000);
                bites = false;
                player.isFishing = false;
            }
        } catch (InterruptedException ignored) {

        }
    }


    public void interrupt() {
        bites = false;
        if (fishingThread.isAlive()) {
            fishingThread.interrupt();
        }
    }

    private int getTime() {
        int ropeId = 10;
        int time;
        Random rand = new Random();
        for (int i = 0; i < player.inventory.size(); i++) {
            if (player.inventory.get(i + 1).id == 10 || player.inventory.get(i + 1).id == 13) {
                ropeId = player.inventory.get(i + 1).id;
            }
        }

        switch (ropeId) {
            case 10 -> time = rand.nextInt((40 * 1000 - 30 * 1000) + 1) + 30 * 1000;
            case 13 -> time = rand.nextInt((30 * 1000 - 20 * 1000) + 1) + 20 * 1000;
            default -> time = rand.nextInt((40 * 1000 - 30 * 1000) + 1) + 30 * 1000;
        }
        return time;
    }

    private int getCatch(int idBite) {
        int idCatch;
        Random rand = new Random();
        int percent = rand.nextInt((100 - 1) + 1) + 1;
        switch (idBite) {
            case 11 -> {
                if (percent <= 95) {
                    idCatch = 12;
                } else {
                    idCatch = 14;
                }
            }
            case 20 -> {
                if (percent <= 35) {
                    idCatch = 12;
                } else if (percent <= 95) {
                    idCatch = 14;
                } else {
                    idCatch = 16;
                }
            }
            case 18 -> {
                if (percent <= 5) {
                    idCatch = 12;
                } else if (percent <= 35) {
                    idCatch = 14;
                } else if (percent <= 95) {
                    idCatch = 16;
                } else {
                    idCatch = 15;
                }
            }
            case 19 -> {
                if (percent <= 10) {
                    idCatch = 14;
                } else if (percent <= 65) {
                    idCatch = 16;
                } else if (percent <= 98) {
                    idCatch = 15;
                } else {
                    idCatch = 17;
                }
            }
            default -> idCatch = 12;
        }
        return idCatch;
    }
}
