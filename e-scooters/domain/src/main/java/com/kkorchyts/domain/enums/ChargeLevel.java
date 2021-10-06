package com.kkorchyts.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum ChargeLevel {
    EMPTY(0, "EMPTY"), LOW(1, "LOW"), MIDDLE(2, "MIDDLE"), HIGH(3, "HIGH"), FULL(4, "FULL");

    static List<ChargeLevel> goodLevel = Arrays.asList(ChargeLevel.MIDDLE, ChargeLevel.HIGH, ChargeLevel.FULL);
    static List<ChargeLevel> badLevel = Arrays.asList(ChargeLevel.EMPTY, ChargeLevel.LOW);

    private int level;
    private String value;

    ChargeLevel(int level, String value) {
        this.level = level;
        this.value = value;
    }

/*    public static ChargeLevel valueOf(Integer level) {
        switch (level) {
            case 0:
                return EMPTY;
            case 1:
                return LOW;
            case 2:
                return MIDDLE;
            case 3:
                return HIGH;
            case 4:
                return FULL;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("changelevel.notfound"));
        }
    }*/

    public static List<ChargeLevel> getGoodLevel() {
        return goodLevel;
    }

    public static List<ChargeLevel> getBadLevel() {
        return badLevel;
    }
}
