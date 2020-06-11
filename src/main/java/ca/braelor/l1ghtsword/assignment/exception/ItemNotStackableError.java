package ca.braelor.l1ghtsword.assignment.exception;

public class ItemNotStackableError extends RuntimeException {
    public ItemNotStackableError() { super("This item is not stackable"); }
}
