package com.delaroystudios.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Uvi {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("date_iso")
    @Expose
    private String dateIso;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("value")
    @Expose
    private Double value;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDateIso() {
        return dateIso;
    }

    public void setDateIso(String dateIso) {
        this.dateIso = dateIso;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
