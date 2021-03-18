package com.delaroystudios.weatherapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class UviDb implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("lat")
    public double lat;
    @SerializedName("lon")
    public double lon;
    @SerializedName("value")
    public double value;

    public UviDb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
