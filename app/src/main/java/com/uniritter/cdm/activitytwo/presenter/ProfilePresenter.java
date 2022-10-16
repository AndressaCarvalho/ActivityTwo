package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.UserRepository;

public class ProfilePresenter implements ProfilePresenterContract.Presenter {
    private ProfilePresenterContract.View profileView;
    private UserRepository repository;

    public ProfilePresenter(ProfilePresenterContract.View profileView) {
        this.profileView = profileView;
        this.repository = UserRepository.getInstance(profileView.getActivity());
    }

    @Override
    public void getUser(int userId) {
        this.profileView.onUserResult(this.repository.getUserById(userId));
    }
}
