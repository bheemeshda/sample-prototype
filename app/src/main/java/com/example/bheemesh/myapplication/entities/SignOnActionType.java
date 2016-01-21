package com.example.bheemesh.myapplication.entities;

/**
 * Created by bheemesh on 4/1/16.
 */
public enum SignOnActionType {
    GO_TO_SIGN_IN(200, "sign_in"),
    GO_TO_SIGN_UP(102, "sign_up"),
    GO_TO_SIGN_UP_ABOUT_NEXT(102, "sign_up_about_next"),
    GO_TO_FORGOT_PASSWORD(101, "forgot_password"),
    GO_TO_PROFILE(101, "profile");

    private int index;
    private String name;

    SignOnActionType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static SignOnActionType fromName(String name) {
        for (SignOnActionType type : SignOnActionType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return GO_TO_SIGN_IN;
    }

    public static SignOnActionType fromIndex(int index) {
        for (SignOnActionType type : SignOnActionType.values()) {
            if (type.index == index) {
                return type;
            }
        }
        return GO_TO_SIGN_IN;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
