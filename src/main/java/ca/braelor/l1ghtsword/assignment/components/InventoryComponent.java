package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.RemovePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.UsePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import ca.braelor.l1ghtsword.assignment.model.objects.Usable;
import ca.braelor.l1ghtsword.assignment.utils.util;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static ca.braelor.l1ghtsword.assignment.utils.util.isUsable;
import static net.gameslabs.model.objects.Assignment.log;

/**
 * Component that manages all details of player inventories.
 * where items are, changing those items, using items and removing those items.
 *
 * All operations preformed are just of ItemData to ItemSLot and nothing else.
 * Items keep track of their own quantities in the ItemData object and other item
 * details such as if they are usable are managed by other enum lists, Objects or components.
 *
 * Is capable of modifying operations based on item details such as stackable or usable.
 * This allows for flexible operation of inventory management and overall makes this
 * system more complex...
 *
 *
 * I spent a lot of time on this...
 */

public class InventoryComponent extends Component {

    private HashMap<Player, PlayerInventory> persistence;
    public InventoryComponent() { persistence = new HashMap<>(); }

    @Override
    public void onLoad() {
        registerEvent(GivePlayerItemEvent.class, this::onGivePlayerItem);
        registerEvent(GetPlayerItemEvent.class, this::onGetPlayerItem);
        registerEvent(RemovePlayerItemEvent.class, this::onRemovePlayerItem);
        registerEvent(UsePlayerItemEvent.class, this::onUsePlayerItem);
    }

    private void onGivePlayerItem(GivePlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = e.getQuantity();

        if(util.isStackable(i)) {
            if(inv.hasItem(i)) {
                if (!givePlayerItemAtItemStack(inv, i, q)) {
                    givePlayerItemAtEmptySlot(inv, i, q);
                }
            } else { givePlayerItemAtEmptySlot(inv, i, q); }
        } else {
            givePlayerToManyEmptySlots(inv,i,q);
        }
        e.setCancelled(true);
    }

    private void onGetPlayerItem(GetPlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = 0;

        if(inv.hasItem(i)) {
            e.setQuantity(getPlayerItemAmount(inv,i));
            e.setHasItem(true);
        } else { e.setHasItem(false); }
        e.setCancelled(true);
    }

    private void onRemovePlayerItem(RemovePlayerItemEvent e) {
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = e.getQuantity();

        try {
            if (inv.hasItem(i)) {
                if(!removePlayerItemStacks(inv,i,q)) { throw new NotEnoughItemsInPlayerInventory(i,q); }
            } else { throw new ItemDoesNotExistError(i); }
        }
        catch(ItemDoesNotExistError | NotEnoughItemsInPlayerInventory err) { log(err.getMessage()); }
        e.setCancelled(true);
    }

    private void onUsePlayerItem(UsePlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = e.getQuantity();

        if(inv.hasItem(i)) {
            if(isUsable(i)) {
                try { useItem(inv,i,1);
                } catch(ItemNotUsableError err) { log(err.getMessage()); }
            } else { log(e.getItem()+" is not an item that can be used"); }
        } else { log(e.getPlayer()+" does not have "+q+"x "+i+" to use!"); }
        e.setCancelled(true);
    }

    private boolean givePlayerItemAtItemStack(PlayerInventory inv, Item i, int q ) {
        try {
            inv.getItemDataAt(inv.getFirstItemSlot(i)).addQuantity(q);
            return true;
        }
        catch(ItemDoesNotExistError | AdditionError err){
            log(err.getMessage());
            return false;
        }
    }

    private void givePlayerItemAtEmptySlot(PlayerInventory inv, Item i, int q ) {
        try { inv.setItemAt(inv.getFirstItemSlot(Item.EMPTY),util.toItemData(i,q)); }
        catch(PlayerInventoryFullError err){ log(err.getMessage()); }
    }

    private void givePlayerToManyEmptySlots(PlayerInventory inv, Item i, int q ) {
        try {
            List<ItemSlot> islist = inv.getItemSlots(Item.EMPTY);
            if(islist.size() > q) {
                IntStream.range(0,q).forEach(is -> { inv.setItemAt(islist.get(is),util.toItemData(i,1)); });
            } else { throw new NotEnoughInventorySpaceError(); }
        }
        catch (PlayerInventoryFullError | NotEnoughInventorySpaceError err) { log(err.getMessage()); }
    }

    private int getPlayerItemAmount(PlayerInventory inv, Item i) {
        int q = 0;
            try {
                for (ItemSlot is : inv.getItemSlots(i)) {
                    if (inv.getItemDataAt(is).getItem().equals(i)) {
                        q += inv.getItemDataAt(is).getQuantity();
                    }
                }
            } catch (ItemDoesNotExistError err) { log(err.getMessage()); }
            return q;
    }

    private boolean removePlayerItemStacks( PlayerInventory inv, Item i, int q ) {
        List<ItemSlot> slots;
        try {
            slots = inv.getItemSlots(i);
            if(util.isStackable(i)){
                if(slots.size() == 1) {
                    int iq = inv.getItemDataAt(slots.get(0)).getQuantity();
                    if(iq > q) { inv.getItemDataAt(slots.get(0)).subQuantity(q); }
                    else if(iq == q) { inv.removeItem(slots.get(0)); }
                    else { throw new NotEnoughItemsInPlayerInventory(i,q); }

                } else {
                    if(getPlayerItemAmount(inv,i) >= q) {
                        for (ItemSlot slot : slots) {
                            if (q <= 0) { return true; }
                            int iq = inv.getItemDataAt(slot).getQuantity();
                            if (iq > q) {
                                inv.getItemDataAt(slots.get(0)).subQuantity(q);
                                q = 0;
                            } else {
                                inv.removeItem(slots.get(0));
                                q -= iq;
                            }
                        }
                    } else { throw new NotEnoughItemsInPlayerInventory(i,q); }
                }
            } else {
                if(slots.size() >= q) {
                    for( ItemSlot slot : slots) { inv.removeItem(slot); }
                } else { throw new NotEnoughItemsInPlayerInventory(i,q); }
            }
            return true;
        }
        catch(ItemDoesNotExistError | NotEnoughItemsInPlayerInventory | SubtractionError err){
            log(err.getMessage());
            return false;
        }
    }

    private void useItem(PlayerInventory inv, Item i, int q) {
        if(isUsable(i)){
            log(new Usable(i).getProperties());
            removePlayerItemStacks(inv,i,q);
        } else { throw new ItemNotUsableError(i); }
    }

    private PlayerInventory getInventory(Player p) {
        if (persistence.containsKey(p)) { return persistence.get(p); }
        return persistence.computeIfAbsent(p, pi -> new PlayerInventory());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
