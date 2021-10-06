package com.kkorchyts.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum TechnicalConditionLevel {
    DAMAGED(0, "DAMAGED"), FRAGLE(1, "FRAGLE"), NORMAL(2, "NORMAL"), FINE(3, "FINE"), GREAT(4, "GREAT");

    private int level;
    private String value;

    private static List<TechnicalConditionLevel> goodLevel = Arrays.asList(TechnicalConditionLevel.NORMAL, TechnicalConditionLevel.FINE, TechnicalConditionLevel.GREAT);
    private static List<TechnicalConditionLevel> badLevel = Arrays.asList(TechnicalConditionLevel.DAMAGED, TechnicalConditionLevel.FRAGLE);

    TechnicalConditionLevel(int level, String value) {
        this.level = level;
        this.value = value;
    }


/*    public static TechnicalConditionLevel valueOf(Integer level) {
        switch (level) {
            case 0:
                return DAMAGED;
            case 1:
                return FRAGLE;
            case 2:
                return NORMAL;
            case 3:
                return FINE;
            case 4:
                return GREAT;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("technicalconditionlevel.notfound"));
        }
    }*/

    public static List<TechnicalConditionLevel> getGoodLevel() {
        return goodLevel;
    }

    public static List<TechnicalConditionLevel> getBadLevel() {
        return badLevel;
    }

}
