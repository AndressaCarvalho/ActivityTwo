package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IAlbumModel;

import java.util.List;

public class AlbumPresenterContract {
    public interface View {
        void onAlbumsResult(List<IAlbumModel> albums);

        Activity getActivity();
    }

    public interface Presenter {
        void getAlbums(int userId);
    }
}
