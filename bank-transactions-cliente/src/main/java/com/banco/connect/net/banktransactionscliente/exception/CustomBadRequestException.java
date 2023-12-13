package com.banco.connect.net.banktransactionscliente.exception;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String message) {
        super(message);
    }

    public CustomBadRequestException() {
    }

    public CustomBadRequestException(Throwable cause) {
        super(cause);
    }
}