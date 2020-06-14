package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.RemovePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import ca.braelor.l1ghtsword.assignment.model.enums.StackableItem;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static net.gameslabs.model.objects.Assignment.log;

public class InventoryComponent extends Component {

    private HashMap<Player, PlayerInventory> persistence;
    public InventoryComponent() { persistence = new HashMap<>(); }

    @Override
    public void onLoad() {
        registerEvent(GivePlayerItemEvent.class, this::onGivePlayerItem);
        registerEvent(GetPlayerItemEvent.class, this::onGetPlayerItem);
        registerEvent(RemovePlayerItemEvent.class, this::onRemovePlayerItem);
    }

    private void onGivePlayerItem(GivePlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = e.getQuantity();

        if(isStackable(i)) {
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

    public boolean givePlayerItemAtItemStack(PlayerInventory inv, Item i, int q ) {
        try {
            inv.getItemDataAt(inv.getFirstItemSlot(i)).addQuantity(q);
            return true;
        }
        catch(ItemDoesNotExistError | AdditionError err){
            log(err.getMessage());
            return false;
        }
    }

    public void givePlayerItemAtEmptySlot(PlayerInventory inv, Item i, int q ) {
        try { inv.setItemAt(inv.getFirstItemSlot(Item.EMPTY),toItemData(i,q)); }
        catch(PlayerInventoryFullError err){ log(err.getMessage()); }
    }

    public void givePlayerToManyEmptySlots(PlayerInventory inv, Item i, int q ) {
        try {
            List<ItemSlot> islist = inv.getItemSlots(Item.EMPTY);
            if(islist.size() > q) {
                IntStream.range(0,q).forEach(is -> { inv.setItemAt(islist.get(is),toItemData(i,1)); });
            } else { throw new NotEnoughInventorySpaceError(); }
        }
        catch (PlayerInventoryFullError | NotEnoughInventorySpaceError err) { log(err.getMessage()); }
    }

    public int getPlayerItemAmount(PlayerInventory inv, Item i) {
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

    public boolean removePlayerItemStacks( PlayerInventory inv, Item i, int q ) {
        List<ItemSlot> slots;
        try {
            slots = inv.getItemSlots(i);
            if(isStackable(i)){
                if(slots.size() == 1) {
                    int iq = inv.getItemDataAt(slots.get(0)).getQuantity();
                    if(iq > q) { inv.getItemDataAt(slots.get(0)).subQuantity(q); }
                    else if(iq == q) { inv.removeItem(slots.get(0)); }
                    else { throw new NotEnoughItemsInPlayerInventory(i,q); }

                } else {
                    if(getPlayerItemAmount(inv,i) >= q) {
                        for (ItemSlot slot : slots) {
                            //Explicit exit when remove amount complete
                            if (q <= 0) { return true; }
                            //get Item Stack Amount
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

    public static boolean isStackable (Item i) {
        for(StackableItem s : StackableItem.values()) {
            if(s.toString().equals(i.toString())) { return true; }
        }
        return false;
    }

    public ItemData toItemData(Item i, int q) { return new ItemData(i,q); }

    private PlayerInventory getInventory(Player p) {
        if (persistence.containsKey(p)) { return persistence.get(p); }
        return persistence.computeIfAbsent(p, pi -> new PlayerInventory());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
