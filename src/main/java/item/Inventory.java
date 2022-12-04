package item;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> inventory = new ArrayList<>();

    // COUNT START FROM 1 TO SIX !!!!!!!!!!!!!!

    public boolean addAmount(int index){
        if(exist(index)){
            if(inventory.get(index-1).amount < 20){
                inventory.get(index-1).amount++;
                return true;
            }
        }
        return false;
    }

    public boolean minusAmount(int index){
        if(exist(index)){
            if(inventory.get(index-1).amount > 1){
                inventory.get(index-1).amount--;
                return true;
            }
            if(inventory.get(index-1).amount == 1){
                inventory.remove(index-1);
                return true;
            }
        }
        return false;
    }






    public boolean add(Item item){
        if(inventory.size() <= 6){
            inventory.add(item);
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return inventory.size();
    }

    public boolean isEmpty(){
        return inventory.isEmpty();
    }

    public boolean remove(int index){
        if(exist(index)){
            inventory.remove(index-1);
            return true;
        } else {
            return false;
        }
    }

    public Item get(int index){
        if(exist(index)){
            return inventory.get(index-1);
        } else {
            return null;
        }
    }

    private boolean exist(int index){
        if(inventory.size() >= index && inventory.size() <= 6){
            return true;
        } else {
            return false;
        }
    }
}
