package com.uniritter.cdm.activitytwo.model;

public interface IUserModel {
    int getUserId();

    String getUserName();

    String getUserEmail();

    String getUserPassword();

    AddressModel getUserAddress();

    String getUserPhone();

    String getUserWebsite();

    CompanyModel getUserCompany();
}
