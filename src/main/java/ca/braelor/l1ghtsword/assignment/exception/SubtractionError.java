package ca.braelor.l1ghtsword.assignment.exception;

public class SubtractionError extends RuntimeException {
    public SubtractionError() { super("Result cannot be less than 1"); }
}
