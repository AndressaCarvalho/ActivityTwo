package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.CommentAdapter;
import com.uniritter.cdm.activitytwo.model.ICommentModel;
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.presenter.CommentPresenter;
import com.uniritter.cdm.activitytwo.presenter.CommentPresenterContract;

import java.util.List;

public class PostDetailsActivity extends AppCompatActivity implements CommentPresenterContract.View {
    private CommentPresenterContract.Presenter commentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        IPostModel post = (IPostModel) getIntent().getParcelableExtra("postObject");

        TextView postTitle = findViewById(R.id.postTitle);
        postTitle.setText(post.getPostTitle());

        TextView postBody = findViewById(R.id.postBody);
        postBody.setText(post.getPostBody());

        commentPresenter = new CommentPresenter(this);
        commentPresenter.getComments(post.getPostId());
    }

    @Override
    public void onCommentsResult(List<ICommentModel> comments) {
        if (comments != null) {
            CommentAdapter adapter = new CommentAdapter(comments);
            RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewComments);
            rv.setHasFixedSize(true);

            rv.setAdapter(adapter);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}