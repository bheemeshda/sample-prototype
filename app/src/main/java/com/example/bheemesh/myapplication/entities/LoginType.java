package com.example.bheemesh.myapplication.entities;

/**
 * Enum for different types of login
 *
 * @author bheemesh
 */
public enum LoginType {
    NONE("NONE"),
    EMAIL("EMAIL"),
    PHONE("PHONE"),
    GOOGLE("GOOGLE"),
    FACEBOOK("FACEBOOK");

    private final String value;

    private LoginType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LoginType fromValue(String value) {
        for (LoginType type : LoginType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return NONE;
    }
}
