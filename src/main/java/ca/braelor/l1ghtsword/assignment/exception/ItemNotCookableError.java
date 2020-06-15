package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class ItemNotCookableError extends RuntimeException {
    public ItemNotCookableError(Item i) { super(i + " cannot be cooked and is a runtime mistake!"); }
}
