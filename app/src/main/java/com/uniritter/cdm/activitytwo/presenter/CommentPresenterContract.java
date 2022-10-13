package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.ICommentModel;

import java.util.List;

public class CommentPresenterContract {
    public interface View {
        void onCommentsResult(List<ICommentModel> comments);

        Activity getActivity();
    }

    public interface Presenter {
        void getComments(int postId);
    }
}
