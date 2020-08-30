package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used to request the use of an item
 * Will throw a caught exception if item is not usable
 * <p>
 * This Event can report Items properties, however this is managed by
 * InventoryComponent via a listener normally, so its currently unused
 */

public class UsePlayerItemEvent extends PlayerEvent {
    private Item itemBeingUsed;
    private final ItemID itemIDBeingUsed;
    private final String itemUseProperties;

    public UsePlayerItemEvent(Player player, Item item) {
        super(player);
        this.itemBeingUsed = item;
        this.itemIDBeingUsed = itemBeingUsed.getItemID();
        this.itemUseProperties = itemBeingUsed.getUseProperties();

    }

    public Item getItem() {
        return this.itemBeingUsed;
    }

    public ItemID getItemID() {
        return this.itemIDBeingUsed;
    }

    public String getProperties() {
        return this.itemUseProperties;
    }
}
