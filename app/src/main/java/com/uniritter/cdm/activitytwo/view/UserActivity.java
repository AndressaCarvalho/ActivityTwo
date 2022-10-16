package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.UserAdapter;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.presenter.UserPresenterContract;

import java.util.List;

public class UserActivity extends AppCompatActivity implements UserPresenterContract.View {
    private UserPresenterContract.Presenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        List<IUserModel> users = (List<IUserModel>) getIntent().getSerializableExtra("userList");

        UserAdapter adapter = new UserAdapter(users);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
    }

    @Override
    public void onUsersResult(List<IUserModel> users) {
        if (users != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}