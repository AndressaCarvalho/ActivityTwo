package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.AlbumRepository;

public class AlbumPresenter implements AlbumPresenterContract.Presenter {
    private AlbumPresenterContract.View albumView;
    private AlbumRepository repository;

    public AlbumPresenter(AlbumPresenterContract.View albumView) {
        this.albumView = albumView;
        this.repository = AlbumRepository.getInstance(albumView.getActivity());
    }

    @Override
    public void getAlbums(int userId) {
        albumView.onAlbumsResult(repository.getAlbumsByUserId(userId));
    }
}
