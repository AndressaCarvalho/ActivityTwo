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
    }

    public static PhotoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoDatabaseRepository(context);
        }
        return instance;
    }

    public List<IPhotoModel> getPhotosByAlbumId(int albumId) {
        String[] columns = { databaseHelper.ID, databaseHelper.ALBUM_ID, databaseHelper.TITLE, databaseHelper.URL, databaseHelper.THUMBNAIL_URL };
        String[] conditionArgs = { Long.toString(albumId) };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, databaseHelper.ALBUM_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
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

        database.close();

        return photos;
    }

    public List<IPhotoModel> getAllPhotos() {
        String[] columns = { databaseHelper.ID, databaseHelper.ALBUM_ID, databaseHelper.TITLE, databaseHelper.URL, databaseHelper.THUMBNAIL_URL };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
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

        database.close();

        return photos;
    }

    public boolean addPhoto(int photoAlbumId, String photoTitle, String photoUrl, String photoThumbnailUrl){
        ContentValues values = new ContentValues();
        long result;

        database = databaseHelper.getWritableDatabase();
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
