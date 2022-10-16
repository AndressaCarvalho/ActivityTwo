package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.AlbumRepository;
import com.uniritter.cdm.activitytwo.repository.PostRepository;
import com.uniritter.cdm.activitytwo.repository.ToDoRepository;
import com.uniritter.cdm.activitytwo.repository.UserRepository;

public class HomePresenter implements HomePresenterContract.Presenter {
    private HomePresenterContract.View homeView;
    private UserRepository repositoryUser;
    private PostRepository repositoryPost;
    private AlbumRepository repositoryAlbum;
    private ToDoRepository repositoryToDo;

    public HomePresenter(HomePresenterContract.View homeView) {
        this.homeView = homeView;
        this.repositoryUser = UserRepository.getInstance(homeView.getActivity());
        this.repositoryPost = PostRepository.getInstance(homeView.getActivity());
        this.repositoryAlbum = AlbumRepository.getInstance(homeView.getActivity());
        this.repositoryToDo = ToDoRepository.getInstance(homeView.getActivity());
    }

    @Override
    public void getUser(int userId) {
        this.homeView.onUserResult(this.repositoryUser.getUserById(userId));
    }

    @Override
    public void getUsers() {
        this.homeView.onUsersResult(this.repositoryUser.getAllUsers());
    }

    @Override
    public void getPosts(int userId) {
        this.homeView.onPostsResult(this.repositoryPost.getPostsByUserId(userId));
    }

    @Override
    public void getAlbums(int userId) {
        this.homeView.onAlbumsResult(this.repositoryAlbum.getAlbumsByUserId(userId));
    }

    @Override
    public void getToDos(int userId) {
        this.homeView.onToDosResult(this.repositoryToDo.getToDosByUserId(userId));
    }
}
