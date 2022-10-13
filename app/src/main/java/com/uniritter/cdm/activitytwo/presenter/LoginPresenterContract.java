package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IUserModel;

public class LoginPresenterContract {
    public interface View {
        void onLoginCall(String userNameEmail, String userPassword);

        void onLoginResult(IUserModel user);

        Activity getActivity();
    }

    public interface Presenter {
        void doLogin(String userNameEmail, String password);
    }
}
