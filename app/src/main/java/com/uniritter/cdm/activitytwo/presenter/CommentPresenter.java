package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.CommentRepository;

public class CommentPresenter implements CommentPresenterContract.Presenter {
    private CommentPresenterContract.View commentView;
    private CommentRepository repository;

    public CommentPresenter(CommentPresenterContract.View commentView) {
        this.commentView = commentView;
        this.repository = CommentRepository.getInstance(commentView.getActivity());
    }

    @Override
    public void getComments(int postId) {
        commentView.onCommentsResult(repository.getCommentsByPostId(postId));
    }
}
