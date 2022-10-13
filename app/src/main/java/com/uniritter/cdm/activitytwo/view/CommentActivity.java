package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.ICommentModel;
import com.uniritter.cdm.activitytwo.presenter.CommentPresenterContract;

import java.util.List;

public class CommentActivity extends AppCompatActivity implements CommentPresenterContract.View {
    private CommentPresenterContract.Presenter commentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }

    @Override
    public void onCommentsResult(List<ICommentModel> comments) {
        if (comments != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}