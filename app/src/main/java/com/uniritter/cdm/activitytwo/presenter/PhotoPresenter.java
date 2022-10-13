package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.PhotoRepository;

public class PhotoPresenter implements PhotoPresenterContract.Presenter {
    private PhotoPresenterContract.View photoView;
    private PhotoRepository repository;

    public PhotoPresenter(PhotoPresenterContract.View photoView) {
        this.photoView = photoView;
        this.repository = PhotoRepository.getInstance(photoView.getActivity());
    }

    @Override
    public void getPhotos(int albumId) {
        photoView.onPhotosResult(repository.getPhotosByAlbumId(albumId));
    }
}
