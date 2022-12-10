package item;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> inventory = new ArrayList<>();

    // COUNT START FROM 1 TO SIX !!!!!!!!!!!!!!

    public boolean addAmount(int index) {

        if (itemExistNotFullFirst(inventory.get(index - 1).id) != 0) {
            inventory.get(itemExistNotFullFirst(inventory.get(index - 1).id) - 1).amount++;
            return true;
        } else if (inventory.size() < 6) {
            inventory.add(new Item(inventory.get(index - 1).id, 1));
            return true;
        }

        return false;
    }

    public void addAmount(int index, int k) {
        for (int i = 0; i < k; i++) {
            addAmount(index);
        }
    }

    public boolean minusAmount(int index) {
        int newIndex = itemExist(inventory.get(index - 1).id);
        if (newIndex != 0 && inventory.get(newIndex - 1).amount > 1) {
            inventory.get(newIndex - 1).amount--;
            return true;
        } else if (inventory.get(newIndex - 1).amount == 1) {
            inventory.remove(newIndex - 1);
            return true;
        }

        return false;
    }

    public int itemExist(int id) {
        int index = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).id == id) {
                index = i;
            }
        }
        return index + 1;
    }

    private int itemExistNotFullFirst(int id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).id == id && inventory.get(i).amount < 20) {
                return i + 1;
            }
        }
        return 0;
    }

    public boolean add(Item item) {
        if (inventory.size() < 6) {
            inventory.add(item);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(int itemId) {
        if(itemExist(itemId) != 0) {
            inventory.remove(itemExist(itemId));
            return true;
        }
        return false;
    }

    public boolean addItemOrAddAmount(int itemId) {
        if (itemExist(itemId) != 0) {
            return addAmount(itemExist(itemId));
        } else {
            return add(new Item(itemId, 1));
        }
    }

    public int size() {
        return inventory.size();
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public Item get(int index) {
        if (exist(index)) {
            return inventory.get(index - 1);
        } else {
            return null;
        }
    }

    private boolean exist(int index) {
        return inventory.size() >= index && inventory.size() <= 6;
    }


}
