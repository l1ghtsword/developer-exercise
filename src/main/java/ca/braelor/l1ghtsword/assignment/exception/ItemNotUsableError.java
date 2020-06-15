package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class ItemNotUsableError extends RuntimeException {
    public ItemNotUsableError(Item i) { super(i + " usable and is a runtime mistake!"); }
}
