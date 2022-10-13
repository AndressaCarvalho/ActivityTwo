package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.UserRepository;

public class LoginPresenter implements LoginPresenterContract.Presenter {
    private LoginPresenterContract.View loginView;
    private UserRepository repository;

    public LoginPresenter(LoginPresenterContract.View loginView) {
        this.loginView = loginView;
        this.repository = UserRepository.getInstance(loginView.getActivity());
    }

    @Override
    public void doLogin(String userNameEmail, String userPassword) {
        loginView.onLoginResult(repository.validateCredentials(userNameEmail, userPassword));
    }
}
