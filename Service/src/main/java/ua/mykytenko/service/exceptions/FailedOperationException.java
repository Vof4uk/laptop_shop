package ua.mykytenko.service.exceptions;

public class FailedOperationException extends RuntimeException {
    public FailedOperationException(String message) {
        super(message);
    }
}
