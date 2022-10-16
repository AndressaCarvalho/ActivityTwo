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
import com.uniritter.cdm.activitytwo.repository.UserRepository;

public class LoginActivity extends AppCompatActivity implements LoginPresenterContract.View {
    private TextView userNameEmail;
    private TextView userPassword;
    private LoginPresenterContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userNameEmail = findViewById(R.id.userNameEmail);
        this.userPassword = findViewById(R.id.userPassword);


        // Load all data
        UserRepository.getInstance(this);
        PostRepository.getInstance(this);
        CommentRepository.getInstance(this);
        AlbumRepository.getInstance(this);
        PhotoRepository.getInstance(this);
        ToDoRepository.getInstance(this);


        this.loginPresenter = new LoginPresenter(this);

        findViewById(R.id.logIn).setOnClickListener(view -> {
            onLoginCall(this.userNameEmail.getText().toString(), this.userPassword.getText().toString());
        });
    }

    @Override
    public void onLoginCall(String userNameEmail, String userPassword) {
        this.loginPresenter.doLogin(userNameEmail, userPassword);
    }

    @Override
    public void onLoginResult(IUserModel user) {
        if (user != null) {
            this.checkLoginCredentials(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserPassword());

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userObject", (Parcelable) user);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "Login FAILED!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginCredentials(int id, String name, String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginCredentials", MODE_PRIVATE);

        if (id != 0
                && name != null && !TextUtils.isEmpty(name)
                && email != null && !TextUtils.isEmpty(email)
                && password != null && !TextUtils.isEmpty(password)) {
            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putInt("userId", id);
            edit.putString("userName", name);
            edit.putString("userEmail", email);
            edit.putString("userPassword", password);

            edit.apply();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}