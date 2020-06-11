package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GiveItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.ItemDoesNotExistError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.StackableItem;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;

import java.util.HashMap;

import static net.gameslabs.model.objects.Assignment.log;

public class InventoryComponent extends Component {

    private HashMap<Player, PlayerInventory> persistence;
    public InventoryComponent() { persistence = new HashMap<>(); }

    @Override
    public void onLoad() {
        //EX: registerEvent(GetXPForLevelEvent.class, this::onGetXPForLevel);
        registerEvent(GiveItemEvent.class, this::onGivePlayerItem);

    }
    //EX: private void onGetXPForLevel(GetXPForLevelEvent e) { e.setXp(e.getLevel() * XP_STEP); }
    private void onGivePlayerItem(GiveItemEvent e){
        PlayerInventory inv = persistence.get(e.getPlayer());
        Item i = e.getItem();
        ItemData id;
        int q;

        if(isStackable(i)) {
            if(inv.hasItem(i)) {
                try { id = inv.getItemDataAt(inv.getFirstItemSlot(i)); }
                catch(ItemDoesNotExistError err){
                    log(err.getMessage());
                    if(inv.hasItem(Item.EMPTY)) {
                        //find an empty slot instead!
                    }
                }

            }
            //inv.getItemDataAt(try {inv.getFirstItemSlot(e.getItem())} catch {});
        }
    };

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
