package com.kkorchyts.domain.enums.address;

import java.util.ResourceBundle;

public enum Streets {
    SCORINA(0, "Scorina av."), VICTORY(1, "Victory sq."), LENIN(2, "Lenana av.");

    private int code;
    private String value;

    Streets(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public Streets valueOf(int code) {
        switch (code) {
            case 0:
                return Streets.SCORINA;
            case 1:
                return Streets.VICTORY;
            case 2:
                return Streets.LENIN;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("street.notfound"));
        }
    }
}