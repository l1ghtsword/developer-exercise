package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.RemovePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.UsePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import ca.braelor.l1ghtsword.assignment.model.UsableData;
import ca.braelor.l1ghtsword.assignment.utils.util;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static net.gameslabs.model.objects.Assignment.log;

/**
 * Component that manages all details of player inventoryentories.
 * where items are, changing those items, using items and removing those items.
 * <p>
 * All operations preformed are just of ItemData to ItemSLot and nothing else.
 * Items keep track of their own quantities in the ItemData object and other item
 * details such as if they are usable are managed by other enum lists, Objects or components.
 * <p>
 * Is capable of modifying operations based on item details such as stackable or usable.
 * This allows for flexible operation of inventoryentory management and overall makes this
 * system more complex...
 * <p>
 * <p>
 * I spent a lot of time on this...
 */

public class InventoryComponent extends Component {

    private HashMap<Player, PlayerInventory> persistence;

    public InventoryComponent() {
        persistence = new HashMap<>();
    }

    @Override
    public void onLoad() {
        registerEvent(GivePlayerItemEvent.class, this::onGivePlayerItem);
        registerEvent(GetPlayerItemEvent.class, this::onGetPlayerItem);
        registerEvent(RemovePlayerItemEvent.class, this::onRemovePlayerItem);
        registerEvent(UsePlayerItemEvent.class, this::onUsePlayerItem);
    }

    private void onGivePlayerItem(GivePlayerItemEvent event) {
        PlayerInventory thisPlayerInventory = this.getInventory(event.getPlayer());
        ItemID giveThisItemID = event.getItemIDBeingGiven();
        int giveThisQuantity = event.getQuantityBeingGiven();
        Item giveThisItem = event.getItemBeingGiven();

        //is it stackable?
        if (giveThisItem.isStackable()) {
            //do they already have some?
            if (thisPlayerInventory.hasItem(giveThisItemID)) {
                //Can i add to it? (will also attempt to complete operation)
                if (!addToItemStackAtItemSlot(thisPlayerInventory, giveThisItemID, giveThisQuantity)) {
                    //nope, add to empty slot instead
                    givePlayerItemAtEmptySlot(thisPlayerInventory, giveThisItem);
                }
            } else {
                //don't have it, add to empty slot
                givePlayerItemAtEmptySlot(thisPlayerInventory, giveThisItem);
            }
        } else {
            //not stackable, try and fill inventory
            givePlayerToManyEmptySlots(thisPlayerInventory, giveThisItem, giveThisQuantity);
        }
        event.setCancelled(true);
    }

    private void onGetPlayerItem(GetPlayerItemEvent event) {
        PlayerInventory inventory = this.getInventory(event.getPlayer());
        ItemID itemID = event.getItem();

        if (inventory.hasItem(itemID)) {
            event.setQuantity(getPlayerItemAmount(inventory, itemID));
            event.setHasItem(true);
        } else {
            event.setHasItem(false);
        }
        event.setCancelled(true);
    }

    private void onRemovePlayerItem(RemovePlayerItemEvent e) {
        PlayerInventory inventory = this.getInventory(e.getPlayer());
        ItemID i = e.getItem();
        int q = e.getQuantity();

        try {
            if (inventory.hasItem(i)) {
                if (!removePlayerItemStacks(inventory, i, q)) {
                    throw new NotEnoughItemsInPlayerInventory(i, q);
                }
            } else {
                throw new ItemDoesNotExistError(i);
            }
        } catch (ItemDoesNotExistError | NotEnoughItemsInPlayerInventory err) {
            log(err.getMessage());
        }
        e.setCancelled(true);
    }

    private void onUsePlayerItem(UsePlayerItemEvent e) {
        PlayerInventory inventory = this.getInventory(e.getPlayer());
        ItemID i = e.getItem();
        int q = e.getQuantity();

        if (inventory.hasItem(i)) {
            if (isUsable(i)) {
                try {
                    useItem(inventory, i, 1);
                } catch (ItemNotUsableError err) {
                    log(err.getMessage());
                }
            } else {
                log(e.getItem() + " is not an item that can be used");
            }
        } else {
            log(e.getPlayer() + " does not have " + q + "x " + i + " to use!");
        }
        e.setCancelled(true);
    }

    private boolean addToItemStackAtItemSlot(PlayerInventory inventory, ItemID itemID, int quantity) {
        try {
            inventory.getItemAt(inventory.getFirstItemSlot(itemID)).addQuantity(quantity);
            //Item could be added
            return true;
        } catch (ItemDoesNotExistError | AdditionError error) {
            //Item couldn't be added
            return false;
        }
    }

    private void givePlayerItemAtEmptySlot(PlayerInventory inventory, Item item) {
        try {
            inventory.setItemAt(inventory.getFirstItemSlot(ItemID.EMPTY), (item));
        } catch (NoSpaceInPlayerInventoryError err) {
            log(err.getMessage());
        }
    }

    private void givePlayerToManyEmptySlots(PlayerInventory inventory, Item item, int quantity) {
        try {
            List<ItemSlot> inventorySlotlist = inventory.getItemSlots(ItemID.EMPTY);
            //do they have enough empty slots?
            if (inventorySlotlist.size() > quantity) {
                IntStream.range(0, quantity).forEach(is -> {
                    inventory.setItemAt(inventorySlotlist.get(is), item.createNewInstanceOf(item));
                });
                //no, throw an exception.
            } else {
                throw new NotEnoughInventorySpaceError();
            }
        } catch (NoSpaceInPlayerInventoryError | NotEnoughInventorySpaceError err) {
            log(err.getMessage());
        }
    }

    private int getPlayerItemAmount(PlayerInventory inventory, ItemID i) {
        int q = 0;
        try {
            for (ItemSlot is : inventory.getItemSlots(i)) {
                if (inventory.getItemDataAt(is).getItem().equals(i)) {
                    q += inventory.getItemDataAt(is).getQuantity();
                }
            }
        } catch (ItemDoesNotExistError err) {
            log(err.getMessage());
        }
        return q;
    }

    private boolean removePlayerItemStacks(PlayerInventory inventory, ItemID i, int q) {
        List<ItemSlot> slots;
        try {
            slots = inventory.getItemSlots(i);
            if (util.isStackable(i)) {
                if (slots.size() == 1) {
                    int iq = inventory.getItemDataAt(slots.get(0)).getQuantity();
                    if (iq > q) {
                        inventory.getItemDataAt(slots.get(0)).subQuantity(q);
                    } else if (iq == q) {
                        inventory.removeItem(slots.get(0));
                    } else {
                        throw new NotEnoughItemsInPlayerInventory(i, q);
                    }

                } else {
                    if (getPlayerItemAmount(inventory, i) >= q) {
                        for (ItemSlot slot : slots) {
                            if (q <= 0) {
                                return true;
                            }
                            int iq = inventory.getItemDataAt(slot).getQuantity();
                            if (iq > q) {
                                inventory.getItemDataAt(slots.get(0)).subQuantity(q);
                                q = 0;
                            } else {
                                inventory.removeItem(slots.get(0));
                                q -= iq;
                            }
                        }
                    } else {
                        throw new NotEnoughItemsInPlayerInventory(i, q);
                    }
                }
            } else {
                if (slots.size() >= q) {
                    for (ItemSlot slot : slots) {
                        inventory.removeItem(slot);
                    }
                } else {
                    throw new NotEnoughItemsInPlayerInventory(i, q);
                }
            }
            return true;
        } catch (ItemDoesNotExistError | NotEnoughItemsInPlayerInventory | SubtractionError err) {
            log(err.getMessage());
            return false;
        }
    }

    private void useItem(PlayerInventory inventory, ItemID i, int q) {
        if (isUsable(i)) {
            log(new UsableData(i).getProperties());
            removePlayerItemStacks(inventory, i, q);
        } else {
            throw new ItemNotUsableError(i);
        }
    }

    private PlayerInventory getInventory(Player p) {
        if (persistence.containsKey(p)) {
            return persistence.get(p);
        }
        return persistence.computeIfAbsent(p, pi -> new PlayerInventory());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
