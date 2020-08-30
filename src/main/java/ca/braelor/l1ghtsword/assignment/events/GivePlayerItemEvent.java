package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event to attempt to place an itemstack in a players inventory
 * Event will be canceled is unsuccessful and inventoryComponent will
 * throw a caught exception with an appropriate error message.
 * <p>
 * Provides 2 constructors, with and without specific quantity.
 * isStackable should be called before sending event to prevent errors,
 * default itemstack quantity is 1.
 */

public class GivePlayerItemEvent extends PlayerEvent {
    private Item giveThisItem;
    private final ItemID giveThisItemID;
    private int giveThisQuantity;

    public GivePlayerItemEvent(Player player, Item item) {
        super(player);
        this.giveThisItem = item;
        this.giveThisItemID = item.getItemID();
        this.giveThisQuantity = item.getQuantity();
    }

    public GivePlayerItemEvent(Player player, Item item, int quantity) {
        super(player);
        this.giveThisItem = item;
        this.giveThisItemID = item.getItemID();
        this.giveThisQuantity = quantity;
    }

    public Item getItemBeingGiven() {
        return this.giveThisItem;
    }

    public ItemID getItemIDBeingGiven() {
        return this.giveThisItemID;
    }

    public int getQuantityBeingGiven() {
        return this.giveThisQuantity;
    }
}
