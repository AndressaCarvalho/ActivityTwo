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
import com.uniritter.cdm.activitytwo.model.UserModel;
import com.uniritter.cdm.activitytwo.presenter.HomePresenter;
import com.uniritter.cdm.activitytwo.presenter.HomePresenterContract;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomePresenterContract.View {
    private HomePresenterContract.Presenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCredentials", MODE_PRIVATE);
        String userNameEmail = sharedPreferences.getString("userNameEmail", "");

        UserModel user = getIntent().getParcelableExtra("userObject");

        TextView welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome " + userNameEmail + "!");

        homePresenter = new HomePresenter(this);

        findViewById(R.id.posts).setOnClickListener(view -> {
            homePresenter.getPosts(user.getUserId());
        });

        findViewById(R.id.albums).setOnClickListener(view -> {
            homePresenter.getAlbums(user.getUserId());
        });

        findViewById(R.id.toDos).setOnClickListener(view -> {
            homePresenter.getToDos(user.getUserId());
        });
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