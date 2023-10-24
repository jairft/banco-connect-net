package com.banco.connectnet.banksecureauth.exception;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String message) {
        super(message);
    }

    public CustomBadRequestException() {
    }
}