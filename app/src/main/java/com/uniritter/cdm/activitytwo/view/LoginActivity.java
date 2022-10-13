package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.presenter.LoginPresenter;
import com.uniritter.cdm.activitytwo.presenter.LoginPresenterContract;
import com.uniritter.cdm.activitytwo.repository.AlbumRepository;
import com.uniritter.cdm.activitytwo.repository.CommentRepository;
import com.uniritter.cdm.activitytwo.repository.PhotoRepository;
import com.uniritter.cdm.activitytwo.repository.PostRepository;
import com.uniritter.cdm.activitytwo.repository.ToDoRepository;

public class LoginActivity extends AppCompatActivity implements LoginPresenterContract.View {
    private TextView userNameEmail;
    private TextView userPassword;
    private LoginPresenterContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEmail = findViewById(R.id.userNameEmail);
        userPassword = findViewById(R.id.userPassword);


        // Load all data
        PostRepository.getInstance(this);
        CommentRepository.getInstance(this);
        AlbumRepository.getInstance(this);
        PhotoRepository.getInstance(this);
        ToDoRepository.getInstance(this);


        loginPresenter = new LoginPresenter(this);

        findViewById(R.id.logIn).setOnClickListener(view -> {
            checkLoginCredentials(userNameEmail.getText().toString(), userNameEmail.getText().toString());

            onLoginCall(userNameEmail.getText().toString(), userPassword.getText().toString());
        });
    }

    @Override
    public void onLoginCall(String userNameEmail, String userPassword) {
        loginPresenter.doLogin(userNameEmail, userPassword);
    }

    @Override
    public void onLoginResult(IUserModel user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userObject", (Parcelable) user);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "Login FAILED!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginCredentials(String nameEmail, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginCredentials", MODE_PRIVATE);

        if (nameEmail != null && !TextUtils.isEmpty(nameEmail)
                && password != null && !TextUtils.isEmpty(password)) {
            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putString("userNameEmail", nameEmail);
            edit.putString("userPassword", password);

            edit.apply();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}