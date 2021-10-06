package com.kkorchyts.domain.enums.address;

import java.util.ResourceBundle;

public enum Cities {
    MINSK(0, "Minsk"), SLONIM(1, "Slonim");

    private int code;
    private String value;

    Cities(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public Cities valueOf(int code) {
        switch (code) {
            case 0:
                return Cities.MINSK;
            case 1:
                return Cities.SLONIM;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("city.notfound"));
        }
    }
}
