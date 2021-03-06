package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event to attempt to place an itemstack in a players inventory
 * Event will be canceled is unsuccessful and inventoryComponent will
 * throw a caught exception with an appropriate error message.
 *
 * Provides 2 constructors, with and without specific quantity.
 * isStackable should be called before sending event to prevent errors,
 * default itemstack quantity is 1.
 */

public class GivePlayerItemEvent extends PlayerEvent {
    private final Item i;
    private int q;

    public GivePlayerItemEvent(Player player, Item item) {
        super(player);
        this.i = item;
        this.q = 1;
    }

    public GivePlayerItemEvent(Player player, Item item, int quantity) {
        super(player);
        this.i = item;
        this.q = quantity;
    }

    public Item getItem() { return this.i; }
    public int getQuantity() { return q; }
}
