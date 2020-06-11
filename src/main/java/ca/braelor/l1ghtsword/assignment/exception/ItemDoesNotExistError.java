package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class ItemDoesNotExistError extends RuntimeException {
    public ItemDoesNotExistError(Item i) { super("Player does not have " + i + " In their Inventory"); }
}
