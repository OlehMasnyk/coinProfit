package com.example.coinprofit.telegram.exception;

public class MessageProcessException extends RuntimeException {

    public MessageProcessException() {
    }

    public MessageProcessException(String message) {
        super(message);
    }

    public MessageProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
