package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.PhotoAdapter;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;
import com.uniritter.cdm.activitytwo.model.IPhotoModel;
import com.uniritter.cdm.activitytwo.presenter.PhotoPresenter;
import com.uniritter.cdm.activitytwo.presenter.PhotoPresenterContract;

import java.util.List;

public class AlbumDetailsActivity extends AppCompatActivity implements PhotoPresenterContract.View {
    private PhotoPresenterContract.Presenter photoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        IAlbumModel album = (IAlbumModel) getIntent().getParcelableExtra("albumObject");

        TextView albumTitle = findViewById(R.id.albumTitle);
        albumTitle.setText(album.getAlbumTitle());

        photoPresenter = new PhotoPresenter(this);
        photoPresenter.getPhotos(album.getAlbumId());
    }

    @Override
    public void onPhotosResult(List<IPhotoModel> photos) {
        if (photos != null) {
            PhotoAdapter adapter = new PhotoAdapter(photos);
            RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewPhotos);
            rv.setHasFixedSize(true);

            rv.setAdapter(adapter);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}