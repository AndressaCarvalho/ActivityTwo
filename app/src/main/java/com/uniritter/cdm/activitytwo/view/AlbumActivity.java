package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.AlbumAdapter;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;
import com.uniritter.cdm.activitytwo.presenter.AlbumPresenterContract;

import java.util.List;

public class AlbumActivity extends AppCompatActivity implements AlbumPresenterContract.View {
    private AlbumPresenterContract.Presenter albumPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        List<IAlbumModel> albums = (List<IAlbumModel>) getIntent().getSerializableExtra("albumList");

        AlbumAdapter adapter = new AlbumAdapter(albums);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewAlbums);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
    }

    @Override
    public void onAlbumsResult(List<IAlbumModel> albums) {
        if (albums != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}