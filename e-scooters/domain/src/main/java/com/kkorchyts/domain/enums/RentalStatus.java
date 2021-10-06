package com.kkorchyts.domain.enums;

public enum RentalStatus {
    CANCELED(0, "CANCELED"), IN_PROGRESS(1, "IN_PROGRESS"), DONE(2, "DONE"),
    ;

    private int level;
    private String value;

    RentalStatus(int level, String value) {
        this.level = level;
        this.value = value;
    }


/*    public static RentalStatus valueOf(Integer level) {
        switch (level) {
            case 0:
                return CANCELED;
            case 1:
                return IN_PROGRESS;
            case 2:
                return DONE;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("technicalconditionlevel.notfound"));
        }
    }*/
}