package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IUserModel;

public class ProfilePresenterContract {
    public interface View {
        void onUserResult(IUserModel user);

        Activity getActivity();
    }

    public interface Presenter {
        void getUser(int userId);
    }
}
