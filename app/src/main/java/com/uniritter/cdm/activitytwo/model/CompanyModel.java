package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyModel implements Parcelable {
    private String name;
    private String catchPhrase;
    private String bs;

    public CompanyModel(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    protected CompanyModel(Parcel in) {
        name = in.readString();
        catchPhrase = in.readString();
        bs = in.readString();
    }

    public static final Creator<CompanyModel> CREATOR = new Creator<CompanyModel>() {
        @Override
        public CompanyModel createFromParcel(Parcel in) {
            return new CompanyModel(in);
        }

        @Override
        public CompanyModel[] newArray(int size) {
            return new CompanyModel[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return this.catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return this.bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(catchPhrase);
        parcel.writeString(bs);
    }
}
