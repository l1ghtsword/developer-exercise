package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class NotEnoughItemsInPlayerInventory extends RuntimeException {
    public NotEnoughItemsInPlayerInventory(Item i, int q) { super("Player does not have "+q+"x "+i+" In their Inventory"); }
}
