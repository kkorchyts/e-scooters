package com.kkorchyts.domain.enums;

public enum PerUnitTime {
    MINUTES(0, "MINUTES"), HOURS(1, "HOURS"), DAYS(2, "DAYS");

    private int perUnitTimeId;
    private String value;

    PerUnitTime(int perUnitTimeId, String value) {
        this.perUnitTimeId = perUnitTimeId;
        this.value = value;
    }

/*    public static PerUnitTime valueOf(Integer perUnitTimeId) {
        switch (perUnitTimeId) {
            case 0:
                return MINUTES;
            case 1:
                return HOURS;
            case 2:
                return DAYS;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("perunittime.notfound"));
        }
    }*/
}
