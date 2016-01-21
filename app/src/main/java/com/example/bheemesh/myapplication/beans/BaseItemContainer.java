package com.example.bheemesh.myapplication.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bheemesh on 2/1/16.
 */
public class BaseItemContainer implements Serializable {
    private String id;
    private String displyName;
    private String collectionTitle;
    private String collectionSubTitle;
    private ArrayList<BaseItem> baseItems;

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplyName(String displyName) {
        this.displyName = displyName;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public void setCollectionSubTitle(String collectionSubTitle) {
        this.collectionSubTitle = collectionSubTitle;
    }

    public void setBaseItems(ArrayList<BaseItem> baseItems) {
        this.baseItems = baseItems;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getDisplyName() {
        return displyName;
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public String getCollectionSubTitle() {
        return collectionSubTitle;
    }

    public ArrayList<BaseItem> getBaseItems() {
        return baseItems;
    }
}
