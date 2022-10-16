package com.uniritter.cdm.activitytwo.helper;

public class RequestHelper {
    public boolean isSuccess;

    public RequestType type;

    public RequestHelper(boolean isSuccess, RequestType type) {
        this.isSuccess = isSuccess;
        this.type = type;
    }
}


