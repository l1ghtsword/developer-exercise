package net.gameslabs.model.objects;
import net.gameslabs.exception.AssignmentFailed;
import net.gameslabs.model.enums.Item;
import net.gameslabs.model.enums.ItemSlot;

import java.util.*;

public class PlayerInventory {

    private Map<ItemSlot,ItemData> inventory;

    public PlayerInventory() {
        //Initialize inventory obj with all inventory slots set to Item.Empty
        this.inventory = new EnumMap<>(ItemSlot.class);
        EnumSet.allOf(ItemSlot.class).forEach(is -> this.inventory.put(is,new ItemData(Item.EMPTY)));
    }

    //getters
    public Item getItem(ItemSlot is) { return this.inventory.get(is).getItem(); }
    public int getItemQuantity(ItemSlot is) { return this.inventory.get(is).getQuantity(); }
    public ItemData getItemData(ItemSlot is) { return this.inventory.get(is); }
    public List<ItemSlot> getItems(Item i) {
        //Return List of all Inventory slots containing specified item (Should only be used with hasItem() or may throw exception)
        List<ItemSlot> ItemAtSlots = new ArrayList<ItemSlot>();
        for (Map.Entry<ItemSlot, ItemData> entry : this.inventory.entrySet()) {
            if (entry.getValue().getItem().equals(i)) { ItemAtSlots.add(entry.getKey()); }
        }
        if (!ItemAtSlots.isEmpty()) { return ItemAtSlots; }
        throw new AssignmentFailed("GET_ITEM_SLOT_ERROR - Player does not have item in inventory, use hasItem first!");
    }

    //setters
    public void setItem(ItemSlot is, Item i) { this.inventory.put(is,new ItemData(i)); }
    public void setItem(ItemSlot is, Item i, int a) { this.inventory.put(is,new ItemData(i,a)); }
    public void setItemData(ItemSlot is, ItemData i) { this.inventory.put(is, i); }

    //Remove only 1 item
    public void removeItemAt(ItemSlot is, int q) {
        ItemData test = this.inventory.get(is);
        if(test.isStackable()) {
            if(test.getQuantity() <= (test.getQuantity() - q )) { this.inventory.put(is, new ItemData(Item.EMPTY)); }
            else { test.subQuantity(1); }
        }
        else {
            if(!test.getItem().equals(Item.EMPTY)) { this.inventory.put(is, new ItemData(Item.EMPTY)); }
        }
    }

    /*remove many items
    public void removeItem(int q) {
        ItemData test = this.inventory.get(is);
        if(test.isStackable()) {
            if(test.getQuantity() <= 1) { this.inventory.put(is, new ItemData(Item.EMPTY)); }
            else { test.subQuantity(1); }
        }
        else {

            if(!test.getItem().equals(Item.EMPTY)) {
                this.inventory.put(is, new ItemData(Item.EMPTY));
            }
        }
    }*/

    //Switch items from and to specified slots
    public void switchItem(ItemSlot moveFrom, ItemSlot moveTo) {
        Item temp = this.getItem(moveTo);
        this.setItem(moveTo,this.getItem(moveFrom));
        this.setItem(moveFrom, temp);
    }
    //IMPORTANT - Check is player has item in any inventory returns bool
    public boolean hasItem(Item i) {
        for (Map.Entry<ItemSlot, ItemData> entry : this.inventory.entrySet()) {
            return entry.getValue().getItem().equals(i);
        }
        return false;
    }
    //IMPORTANT - Compare and confirm if Item exists at specified slot
    public boolean hasItemAtSLot(ItemSlot is, Item i) { return this.inventory.get(is).getItem().equals(i); }
}

