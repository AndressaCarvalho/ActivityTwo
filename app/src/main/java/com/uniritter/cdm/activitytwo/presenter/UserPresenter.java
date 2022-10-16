package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.UserRepository;

public class UserPresenter implements UserPresenterContract.Presenter {
    private UserPresenterContract.View userView;
    private UserRepository repository;

    public UserPresenter(UserPresenterContract.View userView) {
        this.userView = userView;
        this.repository = UserRepository.getInstance(userView.getActivity());
    }

    @Override
    public void getUsers() {
        this.userView.onUsersResult(this.repository.getAllUsers());
    }
}
