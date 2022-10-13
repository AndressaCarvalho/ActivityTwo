package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IPostModel;

import java.util.List;

public class PostPresenterContract {
    public interface View {
        void onPostsResult(List<IPostModel> posts);

        Activity getActivity();
    }

    public interface Presenter {
        void getPosts(int userId);
    }
}
