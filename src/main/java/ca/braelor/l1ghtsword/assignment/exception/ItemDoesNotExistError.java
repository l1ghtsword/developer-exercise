package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class ItemDoesNotExistError extends RuntimeException {
    public ItemDoesNotExistError(ItemID i) { super("Player does not have " + i + " In their Inventory"); }
}
