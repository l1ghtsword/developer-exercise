package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event to check player inventory for a specified item
 * Will provide data such as a boolean if player does or doesn't have
 * the requested item, the itemstack quantity (default 0), etc
 *
 * Will not be canceled if item is not in inventory and reports correct
 * quantities, bool response.
 */

public class GetPlayerItemEvent extends PlayerEvent {
    private final Item i;
    private int q;
    private boolean has;

    public GetPlayerItemEvent(Player player, Item item) {
        super(player);
        this.i = item;
    }

    public Item getItem() { return this.i; }
    public int getQuantity() { return q; }
    public boolean hasItem() { return this.has; }
    public void setQuantity(int nq) { this.q = nq; }
    public void setHasItem(boolean set) { this.has = set; }
}
