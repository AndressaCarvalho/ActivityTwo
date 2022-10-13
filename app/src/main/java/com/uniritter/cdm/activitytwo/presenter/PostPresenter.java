package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.PostRepository;

public class PostPresenter implements PostPresenterContract.Presenter {
    private PostPresenterContract.View postView;
    private PostRepository repository;

    public PostPresenter(PostPresenterContract.View postView) {
        this.postView = postView;
        this.repository = PostRepository.getInstance(postView.getActivity());
    }

    @Override
    public void getPosts(int userId) {
        postView.onPostsResult(repository.getPostsByUserId(userId));
    }
}
