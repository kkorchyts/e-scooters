package com.kkorchyts.domain.enums;

public enum UserRole {
    ADMIN(0, "ADMIN"), USER(1, "USER");

    private int index;
    private String value;

    UserRole(int index, String value) {
        this.index = index;
        this.value = value;
    }

/*    public static UserRole valueOf(Integer index) {
        switch (index) {
            case 0:
                return ADMIN;
            case 1:
                return USER;
            default:
                //TODO завести специальные ошибки
                ResourceBundle resourceBundle = ResourceBundle.getBundle("model_messages");
                throw new RuntimeException(resourceBundle.getString("userrole.notfound"));
        }
    }*/

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }


}
