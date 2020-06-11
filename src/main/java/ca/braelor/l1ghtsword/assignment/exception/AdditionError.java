package ca.braelor.l1ghtsword.assignment.exception;

public class AdditionError extends RuntimeException {
    public AdditionError() { super(("Result cannot exceed " + Integer.MAX_VALUE)); }
}
