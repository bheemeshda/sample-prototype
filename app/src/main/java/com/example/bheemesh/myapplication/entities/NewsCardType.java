package com.example.bheemesh.myapplication.entities;

/**
 * Created by bheemesh on 2/1/16.
 */
public enum NewsCardType {
    NORMAL(200, "normal"),
    WITH_CAT_LOCATION(201, "with_cat_location");

    private int index;
    private String name;

    NewsCardType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static NewsCardType fromName(String name) {
        for (NewsCardType type : NewsCardType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NORMAL;
    }

    public static NewsCardType fromIndex(int index) {
        for (NewsCardType type : NewsCardType.values()) {
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
