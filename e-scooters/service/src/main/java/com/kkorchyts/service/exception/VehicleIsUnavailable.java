package com.kkorchyts.service.exception;

public class VehicleIsUnavailable extends RuntimeException {
    public VehicleIsUnavailable() {
        super();
    }

    public VehicleIsUnavailable(String message) {
        super(message);
    }
}
