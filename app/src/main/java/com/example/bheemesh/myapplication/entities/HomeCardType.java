package com.example.bheemesh.myapplication.entities;

/**
 * Created by bheemesh on 2/1/16.
 */
public enum HomeCardType {
    NORMAL(200, "normal");

    private int index;
    private String name;

    HomeCardType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static HomeCardType fromName(String name) {
        for (HomeCardType type : HomeCardType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NORMAL;
    }

    public static HomeCardType fromIndex(int index) {
        for (HomeCardType type : HomeCardType.values()) {
            if (type.index == index) {
                return type;
            }
        }
        return NORMAL;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
