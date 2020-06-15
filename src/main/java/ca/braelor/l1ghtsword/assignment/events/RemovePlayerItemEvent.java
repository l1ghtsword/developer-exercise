package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used by InventoryComponent to request an item be removed from
 * player inventory. Only provides information to requesting listener
 *
 * Event is canceled by InventoryComponent on success of failure
 */

public class RemovePlayerItemEvent extends PlayerEvent {
    private final Item i;
    private int q;

    public RemovePlayerItemEvent(Player player, Item item) {
        super(player);
        this.i = item;
        this.q = 1;
    }

    public RemovePlayerItemEvent(Player player, Item item, int quantity) {
        super(player);
        this.i = item;
        this.q = quantity;
    }

    public Item getItem() { return this.i; }
    public int getQuantity() { return q; }
}
