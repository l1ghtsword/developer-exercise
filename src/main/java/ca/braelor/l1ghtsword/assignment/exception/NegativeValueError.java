package ca.braelor.l1ghtsword.assignment.exception;

public class NegativeValueError extends RuntimeException {
    public NegativeValueError() {
        super("Value added cannot be a negative integer");
    }
}
