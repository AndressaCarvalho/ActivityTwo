package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements IUserModel, Parcelable {
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private AddressModel userAddress;
    private String userPhone;
    private String userWebsite;
    private CompanyModel userCompany;

    public UserModel(int userId, String userName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserModel(int userId, String userName, String userEmail, String userPassword, AddressModel userAddress, String phone, String userWebsite, CompanyModel userCompany) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userPhone = phone;
        this.userWebsite = userWebsite;
        this.userCompany = userCompany;
    }

    protected UserModel(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
        userAddress = in.readParcelable(AddressModel.class.getClassLoader());
        userPhone = in.readString();
        userWebsite = in.readString();
        userCompany = in.readParcelable(CompanyModel.class.getClassLoader());
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public AddressModel getUserAddress() { return this.userAddress; }

    @Override
    public String getUserPhone() { return this.userPhone; }

    @Override
    public String getUserWebsite() { return this.userWebsite; }

    @Override
    public CompanyModel getUserCompany() { return this.userCompany; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userPassword);
        parcel.writeParcelable(userAddress, i);
        parcel.writeString(userPhone);
        parcel.writeString(userWebsite);
        parcel.writeParcelable(userCompany, i);
    }
}

