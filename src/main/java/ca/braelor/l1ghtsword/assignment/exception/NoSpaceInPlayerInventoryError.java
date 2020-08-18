package ca.braelor.l1ghtsword.assignment.exception;

public class NoSpaceInPlayerInventoryError extends RuntimeException {
    public NoSpaceInPlayerInventoryError() { super("Player inventory is full"); }
}
