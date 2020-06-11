package ca.braelor.l1ghtsword.assignment.exception;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class PlayerInventoryFullError extends RuntimeException {
    public PlayerInventoryFullError() { super("Player inventory is full"); }
}
