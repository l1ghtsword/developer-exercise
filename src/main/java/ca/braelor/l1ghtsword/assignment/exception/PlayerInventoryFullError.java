package ca.braelor.l1ghtsword.assignment.exception;

public class PlayerInventoryFullError extends RuntimeException {
    public PlayerInventoryFullError() { super("Player inventory is full"); }
}
