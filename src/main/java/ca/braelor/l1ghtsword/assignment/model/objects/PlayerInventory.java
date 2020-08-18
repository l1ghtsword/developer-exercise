package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.exception.ItemDoesNotExistError;
import ca.braelor.l1ghtsword.assignment.exception.NoSpaceInPlayerInventoryError;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import ca.braelor.l1ghtsword.assignment.model.objects.items.Empty;

import java.util.*;

/**
 * Instanced Obj representing a players inventory
 * This object manages items and item slots, it is unaware of ItemData being moved
 * appropriate checks must be made at a higher level when preforming operations effecting inventory
 * <p>
 * Inventory will always contain 36 inventory slots as the constructor initializes a new player inventory
 * instance with 36 slots containing Item.EMPTY (no item). This is done to prevent runtime issues with null slots
 * My reasoning for this approach as opposed to use of computeIfAbsent is real world functionality.
 * When displaying a player inventory, eventually you will need to display each slot, Null is not a valid Item
 * but EMPTY is, therefore i felt it appropriate to run this once on obj init as it will inevitable be required.
 */

public class PlayerInventory {
    private Map<ItemSlot, Item> playerInventory;

    public PlayerInventory() {
        //Initialize inventory obj with all inventory slots set to Item.Empty
        this.playerInventory = new EnumMap<>(ItemSlot.class);
        EnumSet.allOf(ItemSlot.class).forEach(is -> this.playerInventory.put(is, new Empty()));
    }

    //get item at inventory slot
    public Item getItemAt(ItemSlot thisItemSlot) {
        return this.playerInventory.get(thisItemSlot);
    }

    //Get First inventory slot containing Item
    public ItemSlot getFirstItemSlot(ItemID thisItemID) {
        for (Map.Entry<ItemSlot, Item> is : this.playerInventory.entrySet()) {
            if (is.getValue().getItemID().equals(thisItemID)) {
                return is.getKey();
            }
        }
        if (thisItemID.equals(ItemID.EMPTY)) {
            throw new NoSpaceInPlayerInventoryError();
        } else {
            throw new ItemDoesNotExistError(thisItemID);
        }
    }

    //get item slots for specified item
    public List<ItemSlot> getItemSlots(ItemID thisItemID) {
        List<ItemSlot> ItemAtSlots = new ArrayList<ItemSlot>();
        this.playerInventory.forEach((is, id) -> {
            if (id.getItemID().equals(thisItemID)) {
                ItemAtSlots.add(is);
            }
        });
        if (!ItemAtSlots.isEmpty()) {
            return ItemAtSlots;
        } else if (thisItemID.equals(ItemID.EMPTY)) {
            throw new NoSpaceInPlayerInventoryError();
        } else {
            throw new ItemDoesNotExistError(thisItemID);
        }
    }


    //Check if player has item at specified slot
    public boolean hasItemDataAt(ItemSlot thisItemSlot, Item thisItem) {
        return this.getItemAt(thisItemSlot).equals(thisItem);
    }

    //Check if player has item (will return on first instance, wont always check full array)
    //Did not simply use "if(this.inventory.containsValue(id)) { return true;}" as quantity may be more then 1
    //meaning the object does not match even if the name does match. Must compare names.
    public boolean hasItem(ItemID thisItemID) {
        for (Map.Entry<ItemSlot, Item> e : this.playerInventory.entrySet()) {
            if (e.getValue().getItemID().equals(thisItemID)) {
                return true;
            }
        }
        return false;
    }

    //Set Item at slot (effectively replace)
    public void setItemAt(ItemSlot thisItemSlot, Item thisItem) {
        this.playerInventory.put(thisItemSlot, thisItem);
    }

    //Set ItemData at slot to Item.EMPTY (Effectively delete)
    public void removeItem(ItemSlot thisItemSlot) {
        this.setItemAt(thisItemSlot, new Empty());
    }

    //UNUSED - Switch items from and to specified slots
    public void switchItemSlots(ItemSlot moveItemFromSlot, ItemSlot moveItemToSlot) {
        Item temp = this.getItemAt(moveItemToSlot);
        this.setItemAt(moveItemToSlot, this.getItemAt(moveItemFromSlot));
        this.setItemAt(moveItemFromSlot, temp);
    }

    //UNUSED - Combine itemstacks. Must be used after appropriate checks as no exceptions are thrown
    //Realistically, if i implemented a player inventory move event i would pair this with switch Items
    // to test if stackable and run this instead of switchItemSlots...
    public void combineItemStack(ItemSlot moveItemFromSlot, ItemSlot moveItemToSlot) {
        this.getItemAt(moveItemToSlot).addQuantity(getItemAt(moveItemFromSlot).getQuantity());
        this.removeItem(moveItemFromSlot);
    }
}

