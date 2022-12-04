package item;

import java.awt.image.BufferedImage;

public class Item {

    public int id;
    public BufferedImage image;
    public int amount;
    public boolean isCountable;

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
        this.isCountable = true;
    }

    public Item(int id) {
        this.id = id;
        this.amount = 1;
        this.isCountable = false;
    }

    public Item() {

    }
}
