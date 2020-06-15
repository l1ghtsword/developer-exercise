package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used to request an item be cooked by player.
 * This event will not inform player success of failure, that is
 * managed by CookingComponent listeners, which will also cancel this event
 *
 * Only provides player and item, one way response to listener
 */

public class PlayerCookingEvent extends PlayerEvent {
    private final Item i;

    public PlayerCookingEvent(Player player, Item item) {
        super(player);
        this.i = item;
    }

    public Item getItem() { return this.i; }
}
