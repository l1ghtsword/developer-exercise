package net.gameslabs.exception;

public class AssignmentFailed extends RuntimeException {
    public AssignmentFailed(String info) {
        super(info);
    }
}
