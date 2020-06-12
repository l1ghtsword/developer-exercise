package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.AdditionError;
import ca.braelor.l1ghtsword.assignment.exception.ItemDoesNotExistError;
import ca.braelor.l1ghtsword.assignment.exception.NotEnoughInventorySpaceError;
import ca.braelor.l1ghtsword.assignment.exception.PlayerInventoryFullError;
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
    }

    private void onGivePlayerItem(GivePlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = e.getQuantity();

        //Is the item given stackable?
        if(isStackable(i)) {
            //yes, do they already has an item stack?
            if(inv.hasItem(i)) {
                //try to add item to slot containing the same itemstack
                if (!givePlayerItemAtItemStack(inv, i, q)) {
                    //if it fails throw an error but proceed to give item to first empty slot
                    givePlayerItemAtEmptySlot(inv, i, q);
                }
            //No, Give item to first available slot
            } else { givePlayerItemAtEmptySlot(inv, i, q); }
        } else {
            //No, item is not stackable, check for emptySpace and attempt to fill empty inventory slots
            //If player has not space or not enough space, no items are given.
            givePlayerToManyEmptySlots(inv,i,q);
        }
        //player items have been given or cannot be given, kill the event
        e.setCancelled(true);
    }

    private void onGetPlayerItem(GetPlayerItemEvent e){
        PlayerInventory inv = this.getInventory(e.getPlayer());
        Item i = e.getItem();
        int q = 0;

        try{
            for(ItemSlot is : (inv.getItemSlots(i))){
                if (inv.getItemDataAt(is).getItem().equals(i)) {
                    q += inv.getItemDataAt(is).getQuantity();
                }
            }
            e.setQuantity(q);
            e.setHasItem(true);
        }
        catch(ItemDoesNotExistError err) { e.setHasItem(false); }
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
