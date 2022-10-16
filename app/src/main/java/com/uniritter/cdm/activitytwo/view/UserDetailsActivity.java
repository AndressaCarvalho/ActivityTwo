package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.ProfileAdapter;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.presenter.ProfilePresenterContract;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements ProfilePresenterContract.View {
    private ProfilePresenterContract.Presenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        List<IUserModel> user = (List<IUserModel>) getIntent().getSerializableExtra("userData");

        TextView userNameTitle = findViewById(R.id.userNameTitle);
        for (IUserModel u : user) {
            userNameTitle.setText(u.getUserName());
        }

        ProfileAdapter adapter = new ProfileAdapter(user);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewUser);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
    }

    @Override
    public void onUserResult(IUserModel user) {
        if (user != null){

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}