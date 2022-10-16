package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoModel implements Parcelable {
    private String lat;
    private String lng;

    public GeoModel(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected GeoModel(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<GeoModel> CREATOR = new Creator<GeoModel>() {
        @Override
        public GeoModel createFromParcel(Parcel in) {
            return new GeoModel(in);
        }

        @Override
        public GeoModel[] newArray(int size) {
            return new GeoModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lat);
        parcel.writeString(lng);
    }
}
