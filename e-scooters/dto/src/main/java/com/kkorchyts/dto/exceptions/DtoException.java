package com.kkorchyts.dto.exceptions;

public class DtoException extends RuntimeException {
    public DtoException() {
    }

    public DtoException(String message) {
        super(message);
    }
}
