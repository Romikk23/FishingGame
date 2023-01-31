package item;

import java.awt.image.BufferedImage;

public class Item {

    public int id;
    public BufferedImage image;
    public int amount;
    public boolean isCountable;

    public Item(int id, int amount) {
        this.id = id;
        if(id == 10 || id == 13) {
            this.amount = 0;
            this.isCountable = false;
        } else {
            this.amount = amount;
            this.isCountable = true;
        }
    }
}
