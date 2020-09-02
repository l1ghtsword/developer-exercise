package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.RemovePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.UsePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;

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
        Item item = event.getItem();

        //Does not have Item
        if (!inventory.hasItem(item.getItemID())) {
            event.setHasItem(false);
            event.setTotalQuantity(0);
            event.getItem().setQuantity(0);
            return;
        }

        //has item
        try {
            event.setTotalQuantity(getPlayerInventoryTotalItemCount(inventory, item.getItemID()));
            event.getItem().setQuantity(event.getTotalQuantity());
        } catch (ItemDoesNotExistError | NoSpaceInPlayerInventoryError error) {
            log(error.getMessage());
        }
        event.setHasItem(true);
    }

    private void onRemovePlayerItem(RemovePlayerItemEvent event) {
        PlayerInventory inventory = this.getInventory(event.getPlayer());
        ItemID item = event.getItem();
        int quantity = event.getQuantity();

        try {
            if (inventory.hasItem(item)) {
                if (!removePlayerItemStacks(inventory, item, quantity)) {
                    throw new NotEnoughItemsInPlayerInventory(item, quantity);
                }
            } else {
                throw new ItemDoesNotExistError(item);
            }
        } catch (ItemDoesNotExistError | NotEnoughItemsInPlayerInventory err) {
            log(err.getMessage());
        }
        event.setCancelled(true);
    }

    private void onUsePlayerItem(UsePlayerItemEvent event) {
        PlayerInventory inventory = this.getInventory(event.getPlayer());
        Item item = event.getItem();

        if (inventory.hasItem(item.getItemID())) {
            if (item.isUsable()) {
                try {
                    useItem(inventory, item);
                } catch (ItemNotUsableError err) {
                    log(err.getMessage());
                }
            } else {
                log(event.getItem() + " is not an item that can be used");
            }
        } else {
            log(event.getPlayer() + " does not have any " + item.getItemID() + " to use!");
        }
        event.setCancelled(true);
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
        } catch (NoSpaceInPlayerInventoryError | NotEnoughInventorySpaceError error) {
            log(error.getMessage());
        }
    }

    private int getPlayerInventoryTotalItemCount(PlayerInventory inventory, ItemID itemID) {
        int quantityTotalCount = 0;
        try {
            for (ItemSlot is : inventory.getItemSlots(itemID)) {
                if (inventory.getItemAt(is).getItemID().equals(itemID)) {
                    quantityTotalCount += inventory.getItemAt(is).getQuantity();
                }
            }
        } catch (ItemDoesNotExistError error) {
            log(error.getMessage());
        }
        return quantityTotalCount;
    }

    private boolean removePlayerItemStacks(PlayerInventory inventory, ItemID itemID, int removeQuantity) {
        List<ItemSlot> slots;
        int currentItemSlotQuantity;
        try {
            slots = inventory.getItemSlots(itemID);

            if ((!slots.isEmpty()) && !(getPlayerInventoryTotalItemCount(inventory, itemID) < removeQuantity))
                for (ItemSlot slot : slots) {
                    //Exit loop as amount has been removed. (complete check)
                    if (removeQuantity <= 0) {
                        return true;
                    }

                    //set current itemstack amount in array (Run after completed check to save processing)
                    currentItemSlotQuantity = inventory.getItemAt(slot).getQuantity();

                    //Item stack has enough to complete remove transaction, end loop here
                    if (currentItemSlotQuantity > removeQuantity) {
                        inventory.getItemAt(slot).subQuantity(removeQuantity);
                        return true;
                    }
                    //delete itemstack, keep going
                    else {
                        inventory.removeItem(slot);
                    }
                    removeQuantity -= currentItemSlotQuantity;
                }
            else {
                throw new NotEnoughItemsInPlayerInventory(itemID, removeQuantity);
            }
        } catch (ItemDoesNotExistError | NotEnoughItemsInPlayerInventory | SubtractionError err) {
            log(err.getMessage());
        }
        return false;
    }

    private void useItem(PlayerInventory inventory, Item item) {
        if (item.isUsable()) {
            log(item.getUseProperties());
            removePlayerItemStacks(inventory, item.getItemID(), 1);
        } else {
            throw new ItemNotUsableError(item.getItemID());
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
