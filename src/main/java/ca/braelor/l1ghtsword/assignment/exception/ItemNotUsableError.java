package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class ItemNotUsableError extends RuntimeException {
    public ItemNotUsableError(ItemID i) { super(i + " usable and is a runtime mistake!"); }
}
