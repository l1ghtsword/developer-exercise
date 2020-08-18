package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class NotEnoughItemsInPlayerInventory extends RuntimeException {
    public NotEnoughItemsInPlayerInventory(ItemID i, int q) { super("Player does not have "+q+"x "+i+" In their Inventory"); }
}
