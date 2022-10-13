package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniritter.cdm.activitytwo.model.IPhotoModel;
import com.uniritter.cdm.activitytwo.helper.PhotoDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoDatabaseRepository {
    private List<IPhotoModel> photos;
    private Context context;
    private PhotoDatabaseHelper databaseHelper;
    private static PhotoDatabaseRepository instance;
    private SQLiteDatabase database;

    private PhotoDatabaseRepository(Context context) {
        super();
        this.context = context;
        photos = new ArrayList<>();
        databaseHelper = new PhotoDatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        getAllPhotos();
    }

    public static PhotoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoDatabaseRepository(context);
        }
        return instance;
    }

    public List<IPhotoModel> getPhotosByAlbumId(int albumId) {
        List<IPhotoModel> photosList = new ArrayList<>();

        for(IPhotoModel p : photos) {
            if (albumId == p.getPhotoAlbumId()) {
                photosList.add(p);
            }
        }
        return photosList;
    }

    public void getAllPhotos() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.ALBUM_ID + ", " + databaseHelper.TITLE + ", " + databaseHelper.URL + ", " + databaseHelper.THUMBNAIL_URL + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
        do {
            IPhotoModel photo = new PhotoModel(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            photos.add(photo);
        } while (cursor.moveToNext());
    }

    public boolean addPhoto(int photoAlbumId, String photoTitle, String photoUrl, String photoThumbnailUrl){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(databaseHelper.ALBUM_ID, photoAlbumId);
        values.put(databaseHelper.TITLE, photoTitle);
        values.put(databaseHelper.URL, photoUrl);
        values.put(databaseHelper.THUMBNAIL_URL, photoThumbnailUrl);

        result = database.insert(databaseHelper.TABLE, null, values);
        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
