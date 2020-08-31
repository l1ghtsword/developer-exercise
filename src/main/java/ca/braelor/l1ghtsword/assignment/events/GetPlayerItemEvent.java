package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event to check player inventory for a specified item
 * Will provide data such as a boolean if player does or doesn't have
 * the requested item, the itemstack quantity (default 0), etc
 * <p>
 * Will not be canceled if item is not in inventory and reports correct
 * quantities, bool response.
 */

public class GetPlayerItemEvent extends PlayerEvent {
    private Item item;
    private int totalInventoryItemAmount;
    private boolean hasItem;

    public GetPlayerItemEvent(Player player, Item item) {
        super(player);
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item collectedItem) {
        this.item = collectedItem;
    }

    public ItemID getItemID() {
        return this.item.getItemID();
    }

    public int getTotalQuantity() {
        return this.item.getQuantity();
    }

    public void setTotalQuantity(int totalInventoryItemAmount) {
        this.totalInventoryItemAmount = totalInventoryItemAmount;
    }

    public boolean hasItem() {
        return this.hasItem;
    }

    public void setHasItem(boolean doesPlayerHaveItem) {
        this.hasItem = doesPlayerHaveItem;
    }
}
