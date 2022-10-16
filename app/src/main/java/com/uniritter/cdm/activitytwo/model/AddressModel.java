package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressModel implements Parcelable {
    private String zipCode;
    private String street;
    private String suite;
    private GeoModel geo;

    public AddressModel(String zipCode, String street, String suite, GeoModel geo) {
        this.zipCode = zipCode;
        this.street = street;
        this.suite = suite;
        this.geo = geo;
    }

    protected AddressModel(Parcel in) {
        zipCode = in.readString();
        street = in.readString();
        suite = in.readString();
        geo = in.readParcelable(GeoModel.class.getClassLoader());
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return this.suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public GeoModel getGeo() {
        return this.geo;
    }

    public void setGeo(GeoModel geo) {
        this.geo = geo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(zipCode);
        parcel.writeString(street);
        parcel.writeString(suite);
        parcel.writeParcelable(geo, i);
    }
}
