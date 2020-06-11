package ca.braelor.l1ghtsword.assignment.exception;

public class CannotBeZeroError extends RuntimeException {
    public CannotBeZeroError() { super("Value cannot be 0"); }
}
