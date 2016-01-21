package com.example.bheemesh.myapplication.beans;

import java.io.Serializable;

/**
 * Created by bheemesh on 14/1/16.
 *
 * @author bheemesh
 */
public class Location implements Serializable, Entity<String> {
    private String id;
    private String country;
    private String state;
    private String district;
    private String taluk;
    private String latitude;
    private String longitude;
    private String landMark;

    //--------- setter methods-----
    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    //---------- getter methods --------
    @Override
    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getTaluk() {
        return taluk;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLandMark() {
        return landMark;
    }
}
