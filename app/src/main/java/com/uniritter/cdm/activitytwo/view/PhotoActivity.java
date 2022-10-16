package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IPhotoModel;
import com.uniritter.cdm.activitytwo.presenter.PhotoPresenterContract;

import java.util.List;

public class PhotoActivity extends AppCompatActivity implements PhotoPresenterContract.View {
    private PhotoPresenterContract.Presenter photoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    @Override
    public void onPhotosResult(List<IPhotoModel> photos) {
        if (photos != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}