package com.example.bheemesh.myapplication.beans;

import android.os.Parcelable;

import com.example.bheemesh.myapplication.entities.NewsCardType;

import java.io.Serializable;

/**
 * Created by bheemesh on 28/11/15.
 *
 * @author bheemesh
 */
public class BaseItem implements Serializable, Entity<String> {
    private String id;
    private String name;
    private String subName;
    private String description;
    private String imageUrl;

    private Location location;
    private String locationId;
    private String catKey;
    private String rating;
    private long timeAccess = 0;
    private boolean isFavorite;
    private long favouriteAddedTime;
    private NewsCardType cardType;

    // setter methods

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setCatKey(String catKey) {
        this.catKey = catKey;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTimeAccess(long timeAccess) {
        this.timeAccess = timeAccess;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setFavouriteAddedTime(long favouriteAddedTime) {
        this.favouriteAddedTime = favouriteAddedTime;
    }

    public void setCardType(NewsCardType cardType) {
        this.cardType = cardType;
    }
    // getter methods

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubName() {
        return subName;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getCatKey() {
        return catKey;
    }

    public String getRating() {
        return rating;
    }

    public long getTimeAccess() {
        return timeAccess;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public long getFavouriteAddedTime() {
        return favouriteAddedTime;
    }

    public NewsCardType getCardType() {
        return cardType;
    }

    @Override
    public boolean equals(Object o) {
        return this.id.equals(o);
    }
}
