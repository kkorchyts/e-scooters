package com.kkorchyts.startegies.exceptions;

public class DiscounterError extends RuntimeException {
    public DiscounterError() {
    }

    public DiscounterError(String message) {
        super(message);
    }
}
