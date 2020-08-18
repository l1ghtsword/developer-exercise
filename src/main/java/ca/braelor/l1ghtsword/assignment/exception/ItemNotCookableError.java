package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class ItemNotCookableError extends RuntimeException {
    public ItemNotCookableError(ItemID i) { super(i + " cannot be cooked and is a runtime mistake!"); }
}
