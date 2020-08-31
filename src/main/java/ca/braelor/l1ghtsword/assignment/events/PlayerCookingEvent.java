package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used to request an item be cooked by player.
 * This event will not inform player success of failure, that is
 * managed by CookingComponent listeners, which will also cancel this event
 * <p>
 * Only provides player and item, one way response to listener
 */

public class PlayerCookingEvent extends PlayerEvent {
    private Item item;
    private final ItemID itemID;

    public PlayerCookingEvent(Player player, Item item) {
        super(player);
        this.item = item;
        this.itemID = item.getItemID();
    }

    public Item getItem() {
        return this.item;
    }
    public ItemID getItemID() {
        return this.itemID;
    }
}
