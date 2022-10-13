package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IAlbumModel;
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.model.IToDoModel;

import java.util.List;

public class HomePresenterContract {
    public interface View {
        void onPostsResult(List<IPostModel> posts);

        void onAlbumsResult(List<IAlbumModel> albums);

        void onToDosResult(List<IToDoModel> toDos);

        Activity getActivity();
    }

    public interface Presenter {
        void getPosts(int userId);

        void getAlbums(int userId);

        void getToDos(int userId);
    }
}
