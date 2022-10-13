package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IPhotoModel;

import java.util.List;

public class PhotoPresenterContract {
    public interface View {
        void onPhotosResult(List<IPhotoModel> photos);

        Activity getActivity();
    }

    public interface Presenter {
        void getPhotos(int albumId);
    }
}
