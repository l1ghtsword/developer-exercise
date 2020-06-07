package net.gameslabs.model;
import java.util.*;

public class PlayerInventory {

    private Map<ItemSlot,Item> inventory;
    public PlayerInventory() {
        inventory = new EnumMap<>(ItemSlot.class);
    }

    public Item getAtSlot(ItemSlot is) { return inventory.getOrDefault(is, Item.EMPTY); }
    public void setAtSlot(ItemSlot is, Item i) {
        inventory.put(is, i);
    }
    public boolean hasItem(Item i) { return inventory.containsValue(i); }
    public List<ItemSlot> getItemSlots(Item i) {
        List<ItemSlot> ItemAtSlots = new ArrayList<ItemSlot>();
        for (Map.Entry<ItemSlot,Item> entry : inventory.entrySet()) {
            if (entry.getValue().equals(i)) {
                ItemAtSlots.add(entry.getKey());
            }
        }
        return ItemAtSlots;
    }
}

