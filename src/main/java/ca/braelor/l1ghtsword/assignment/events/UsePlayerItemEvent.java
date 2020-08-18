package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotUsableError;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.UsableData;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used to request the use of an item
 * Will throw a caught exception if item is not usable
 *
 * This Event can report Items properties, however this is managed by
 * InventoryComponent via a listener normally, so its currently unused
 */

public class UsePlayerItemEvent extends PlayerEvent {
    private final ItemID i;
    private final String properties;

    public UsePlayerItemEvent(Player player, ItemID item) {
        super(player);
        this.i = item;
        this.properties = initProperties(item);
    }

    public ItemID getItem() { return this.i; }
    public int getQuantity() { return 1; }
    public String getProperties() { return this.properties; }


    private String initProperties(ItemID item){
        try {
            UsableData u = new UsableData(item);
            return u.getProperties();
        } catch(ItemNotUsableError err) {
            this.setCancelled(true);
            return err.getMessage();
        }
    }
}
