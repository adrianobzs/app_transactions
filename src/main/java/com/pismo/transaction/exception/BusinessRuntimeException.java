package com.pismo.transaction.exception;

public class BusinessRuntimeException extends RuntimeException {
    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
