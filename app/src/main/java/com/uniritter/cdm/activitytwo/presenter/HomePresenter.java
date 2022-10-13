package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.AlbumRepository;
import com.uniritter.cdm.activitytwo.repository.PostRepository;
import com.uniritter.cdm.activitytwo.repository.ToDoRepository;

public class HomePresenter implements HomePresenterContract.Presenter {
    private HomePresenterContract.View homeView;
    private PostRepository repositoryPost;
    private AlbumRepository repositoryAlbum;
    private ToDoRepository repositoryToDo;

    public HomePresenter(HomePresenterContract.View homeView) {
        this.homeView = homeView;
        this.repositoryPost = PostRepository.getInstance(homeView.getActivity());
        this.repositoryAlbum = AlbumRepository.getInstance(homeView.getActivity());
        this.repositoryToDo = ToDoRepository.getInstance(homeView.getActivity());
    }

    @Override
    public void getPosts(int userId) {
        homeView.onPostsResult(repositoryPost.getPostsByUserId(userId));
    }

    @Override
    public void getAlbums(int userId) {
        homeView.onAlbumsResult(repositoryAlbum.getAlbumsByUserId(userId));
    }

    @Override
    public void getToDos(int userId) {
        homeView.onToDosResult(repositoryToDo.getToDosByUserId(userId));
    }
}
