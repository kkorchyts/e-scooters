package com.kkorchyts.domain.enums.address;

import java.util.ResourceBundle;

public enum Districts {
    CENTRAL(0, "Central's"), OCTOBER(1, "October's");

    private int code;
    private String value;

    Districts(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public Districts valueOf(int code) {
        switch (code) {
            case 0:
                return Districts.CENTRAL;
            case 1:
                return Districts.OCTOBER;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("district.notfound"));
        }
    }
}
