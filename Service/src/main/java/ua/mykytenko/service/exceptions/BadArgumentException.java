package ua.mykytenko.service.exceptions;

public class BadArgumentException extends RuntimeException{
    public BadArgumentException(String message) {
        super(message);
    }
}
