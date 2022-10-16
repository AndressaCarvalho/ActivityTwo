package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IUserModel;

import java.util.List;

public class UserPresenterContract {
    public interface View {
        void onUsersResult(List<IUserModel> users);

        Activity getActivity();
    }

    public interface Presenter {
        void getUsers();
    }
}
