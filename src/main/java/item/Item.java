package item;

import java.awt.image.BufferedImage;

public class Item {

    public int id;
    public BufferedImage image;
    public int amount;
    public boolean isCountable;

    public Item(int id, int amount) {
        this.id = id;
        if(amount == 0) {
            this.amount = 1;
            this.isCountable = false;
        } else {
            this.amount = amount;
            this.isCountable = true;
        }
    }
}
