package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used by InventoryComponent to request an item be removed from
 * player inventory. Only provides information to requesting listener
 * <p>
 * Event is canceled by InventoryComponent on success of failure
 */

public class RemovePlayerItemEvent extends PlayerEvent {
    private final ItemID item;
    private int quantity;

    public RemovePlayerItemEvent(Player player, ItemID item) {
        super(player);
        this.item = item;
        this.quantity = 1;
    }

    public RemovePlayerItemEvent(Player player, ItemID item, int quantity) {
        super(player);
        this.item = item;
        this.quantity = quantity;
    }

    public ItemID getItem() {
        return this.item;
    }

    public int getQuantity() {
        return quantity;
    }
}
