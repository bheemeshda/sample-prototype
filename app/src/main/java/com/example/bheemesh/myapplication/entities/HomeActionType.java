package com.example.bheemesh.myapplication.entities;

/**
 * Created by bheemesh on 4/1/16.
 */
public enum HomeActionType {
    DETAILS(200, "normal"),
    UPDATEUI(102, "update_ui"),
    CATEGORYLIST(101, "category_list"),
    NONCLICKABLE(103, "not_clickable");

    private int index;
    private String name;

    HomeActionType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static HomeActionType fromName(String name) {
        for (HomeActionType type : HomeActionType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NONCLICKABLE;
    }

    public static HomeActionType fromIndex(int index) {
        for (HomeActionType type : HomeActionType.values()) {
            if (type.index == index) {
                return type;
            }
        }
        return NONCLICKABLE;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
