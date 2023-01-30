package main;

import crypt.Crypt;
import entity.Player;
import item.Item;
import world.Time;

import java.io.*;
import java.util.Objects;

public class Save {
    GamePanel gp;
    Player player;
    Time time = Time.getInstance();

    public Save(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public Save(Player player) {
        this.player = player;
    }

    public boolean getSave() {
        boolean success = false;
        try {
            InputStream is = getClass().getResourceAsStream("/saves/save.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            String line = br.readLine();
            while (line != null) {
                line = Crypt.decrypt(stringToByte(line));

                switch (Objects.requireNonNull(line).substring(0, 4)) {
                    case "time" -> {
                        String[] numbers = line.substring(6).split(" ");
                        gp.time.setTime(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
                        success = true;
                    }
                    case "coor" -> {
                        String[] numbers = line.substring(6).split(" ");
                        player.setWorldCoord(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
                        success = true;
                    }
                    case "inve" -> {
                        String[] numbers = line.substring(6).split(" ");
                        player.inventory.add(new Item(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
                        success = true;
                    }

                    case "coin" -> {
                        String[] numbers = line.substring(6).split(" ");
                        player.coins = Integer.parseInt(numbers[0]);
                        success = true;
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    public void update() {
        if (time.minute == 30 || time.minute == 0) {
            makeSave();
        }
    }

    public void makeSave() {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/saves/save.txt");
            String str;
            str = byteToString(Objects.requireNonNull(Crypt.encrypt("time: " + time.hour + " " + time.minute)));
            myWriter.write(str + "\n");

            str = byteToString(Objects.requireNonNull(Crypt.encrypt("coor: " + player.worldX + " " + player.worldY)));
            myWriter.write(str + "\n");

            str = byteToString(Objects.requireNonNull(Crypt.encrypt("coin: " + player.coins)));
            myWriter.write(str + "\n");

            for (int i = 1; i < player.inventory.size() + 1; i++) {
                str = byteToString(Objects.requireNonNull(Crypt.encrypt("inve: " + player.inventory.get(i).id + " " + player.inventory.get(i).amount)));
                myWriter.write(str + "\n");
            }
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String byteToString(byte[] byteArray) {
        StringBuilder str = new StringBuilder();
        for (byte value : byteArray)
            str.append(" ").append(value);

        return str.substring(1);
    }

    private byte[] stringToByte(String s) {
        String[] byteArray = s.split(" ");
        byte[] b = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            b[i] = Byte.parseByte(byteArray[i]);
        }
        return b;
    }
}
