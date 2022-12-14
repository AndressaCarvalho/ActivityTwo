package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.model.IToDoModel;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.model.UserModel;
import com.uniritter.cdm.activitytwo.presenter.HomePresenter;
import com.uniritter.cdm.activitytwo.presenter.HomePresenterContract;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomePresenterContract.View {
    private HomePresenterContract.Presenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCredentials", MODE_PRIVATE);
        String userNameEmail = sharedPreferences.getString("userName", "");

        UserModel user = getIntent().getParcelableExtra("userObject");

        TextView welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome " + userNameEmail + "!");

        this.homePresenter = new HomePresenter(this);

        findViewById(R.id.profile).setOnClickListener(view -> {
            this.homePresenter.getUser(user.getUserId());
        });

        findViewById(R.id.users).setOnClickListener(view -> {
            this.homePresenter.getUsers();
        });

        findViewById(R.id.posts).setOnClickListener(view -> {
            this.homePresenter.getPosts(user.getUserId());
        });

        findViewById(R.id.albums).setOnClickListener(view -> {
            this.homePresenter.getAlbums(user.getUserId());
        });

        findViewById(R.id.toDos).setOnClickListener(view -> {
            this.homePresenter.getToDos(user.getUserId());
        });
    }

    @Override
    public void onUserResult(IUserModel user) {
        if (user != null) {
            List<IUserModel> listUser = Collections.singletonList(user);

            Intent intent = new Intent(HomeActivity.this, UserDetailsActivity.class);
            intent.putExtra("userData", (Serializable) listUser);
            startActivity(intent);
        }
        else {
            Toast.makeText(HomeActivity.this, "No user found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUsersResult(List<IUserModel> users) {
        if (users != null) {
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
            intent.putExtra("userList", (Serializable) users);
            startActivity(intent);
        }
        else {
            Toast.makeText(HomeActivity.this, "No user found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPostsResult(List<IPostModel> posts) {
        if (posts != null) {
            Intent intent = new Intent(HomeActivity.this, PostActivity.class);
            intent.putExtra("postList", (Serializable) posts);
            startActivity(intent);
        }
        else {
            Toast.makeText(HomeActivity.this, "No post found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlbumsResult(List<IAlbumModel> albums) {
        if (albums != null) {
            Intent intent = new Intent(HomeActivity.this, AlbumActivity.class);
            intent.putExtra("albumList", (Serializable) albums);
            startActivity(intent);
        }
        else {
            Toast.makeText(HomeActivity.this, "No album found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onToDosResult(List<IToDoModel> toDos) {
        if (toDos != null) {
            Intent intent = new Intent(HomeActivity.this, ToDoActivity.class);
            intent.putExtra("toDoList", (Serializable) toDos);
            startActivity(intent);
        }
        else {
            Toast.makeText(HomeActivity.this, "No to do found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}