package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.PostAdapter;
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.presenter.PostPresenterContract;

import java.util.List;

public class PostActivity extends AppCompatActivity implements PostPresenterContract.View {
    private PostPresenterContract.Presenter postPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        List<IPostModel> posts = (List<IPostModel>) getIntent().getSerializableExtra("postList");

        PostAdapter adapter = new PostAdapter(posts);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewPosts);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
    }

    @Override
    public void onPostsResult(List<IPostModel> posts) {
        if (posts != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}