package com.uniritter.cdm.activitytwo.model;

public class GeoModel {
    private String lat;
    private String lng;

    public GeoModel(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
