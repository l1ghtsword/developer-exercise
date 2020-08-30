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
    private ItemID itemID;
    private boolean hasItem;

    public GetPlayerItemEvent(Player player, Item item) {
        super(player);
        this.item = item;
        this.itemID = this.item.getItemID();
    }

    public Item getItem() {
        return this.item;
    }

    public ItemID getItemID() {
        return this.itemID;
    }

    public int getQuantity() {
        return this.item.getQuantity();
    }

    public boolean hasItem() {
        return this.hasItem;
    }

    public void setQuantity(int newQuantity) {
        this.item.setQuantity(newQuantity);
    }

    public void setHasItem(boolean doesPlayerHaveItem) {
        this.hasItem = doesPlayerHaveItem;
    }
}
