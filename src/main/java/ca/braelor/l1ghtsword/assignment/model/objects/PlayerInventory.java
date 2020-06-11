package ca.braelor.l1ghtsword.assignment.model.objects;
import ca.braelor.l1ghtsword.assignment.exception.ItemDoesNotExistError;
import ca.braelor.l1ghtsword.assignment.exception.PlayerInventoryFullError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;

import java.util.*;

public class PlayerInventory {

    private Map<ItemSlot,ItemData> inventory;

    public PlayerInventory() {
        //Initialize inventory obj with all inventory slots set to Item.Empty
        this.inventory = new EnumMap<>(ItemSlot.class);
        EnumSet.allOf(ItemSlot.class).forEach(is -> this.inventory.put(is,new ItemData(Item.EMPTY)));
    }

    //get item at inventory slot
    public ItemData getItemDataAt(ItemSlot is) { return this.inventory.get(is); }

    //Get First inventory slot containing Item
    public ItemSlot getFirstItemSlot(Item i) {
        for (Map.Entry<ItemSlot, ItemData> is : this.inventory.entrySet()) {
            if (is.getValue().getItem().equals(i)) { return is.getKey(); }
        }
        if(i.equals(Item.EMPTY)) {throw new PlayerInventoryFullError();
        } else { throw new ItemDoesNotExistError(i); }
    }

    //get item slots for specified item
    public List<ItemSlot> getItemSlots(Item i) {
        List<ItemSlot> ItemDataAtSlots = new ArrayList<ItemSlot>();
        //for (Map.Entry<ItemSlot, ItemData> is : this.inventory.entrySet()) {
        this.inventory.forEach((is,id) ->{ if (id.getItem().equals(i)) { ItemDataAtSlots.add(is); } } );
        if(ItemDataAtSlots.isEmpty()) {
            if(i.equals(Item.EMPTY)) {throw new PlayerInventoryFullError();
            } else { throw new ItemDoesNotExistError(i); }
        }
        return ItemDataAtSlots;
    }

    //Check if player has item at specified slot
    public boolean hasItemDataAt(ItemSlot is, ItemData id){ return this.getItemDataAt(is).equals(id); }

    //Check if player has item (will return on first instance, wont always check full array)
    //Did not simply use "if(this.inventory.containsValue(id)) { return true;}" as quantity may be more then 1
    //meaning the object does not match even if the name does match. Must compare names.
    public boolean hasItem (Item i) {
        for (Map.Entry<ItemSlot, ItemData> e : this.inventory.entrySet()) {
            if (e.getValue().getItem().equals(i)) { return true; }
        }
        return false;
    }

    //Set ItemData at slot (effectively replace)
    public void setItemAt(ItemSlot is, ItemData id) { this.inventory.put(is,id); }

    //Set ItemData at slot to Item.EMPTY (Effectively delete)
    public void removeItem(ItemSlot is) { this.setItemAt(is,new ItemData(Item.EMPTY)); }

    //Switch items from and to specified slots
    public void switchItemSlots(ItemSlot moveFrom, ItemSlot moveTo) {
        ItemData temp = this.getItemDataAt(moveTo);
        this.setItemAt(moveTo,this.getItemDataAt(moveFrom));
        this.setItemAt(moveFrom, temp);
    }

    public void combineItemStack(ItemSlot moveFrom, ItemSlot moveTo) {
        this.getItemDataAt(moveTo).addQuantity(getItemDataAt(moveFrom).getQuantity());
        this.removeItem(moveFrom);
    }
}

