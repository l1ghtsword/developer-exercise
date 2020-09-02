package ca.braelor.l1ghtsword.assignment.exception;

public class NotEnoughInventorySpaceError extends RuntimeException {
    public NotEnoughInventorySpaceError() {
        super("There is not enough room in player inventory");
    }
}
