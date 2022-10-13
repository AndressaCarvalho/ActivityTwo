package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniritter.cdm.activitytwo.helper.AlbumDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.AlbumModel;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumDatabaseRepository {
    private List<IAlbumModel> albums;
    private Context context;
    private AlbumDatabaseHelper databaseHelper;
    private static AlbumDatabaseRepository instance;
    private SQLiteDatabase database;

    private AlbumDatabaseRepository(Context context) {
        super();
        this.context = context;
        albums = new ArrayList<>();
        databaseHelper = new AlbumDatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        getAllAlbums();
    }

    public static AlbumDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumDatabaseRepository(context);
        }
        return instance;
    }

    public List<IAlbumModel> getAlbumsByUserId(int userId) {
        List<IAlbumModel> albumsList = new ArrayList<>();

        for(IAlbumModel a : albums) {
            if (userId == a.getAlbumUserId()) {
                albumsList.add(a);
            }
        }
        return albumsList;
    }

    public void getAllAlbums() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.USER_ID + ", " + databaseHelper.TITLE + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
        do {
            IAlbumModel album = new AlbumModel(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3));
            albums.add(album);
        } while (cursor.moveToNext());
    }

    public boolean addAlbum(int albumUserId, String albumTitle){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(databaseHelper.USER_ID, albumUserId);
        values.put(databaseHelper.TITLE, albumTitle);

        result = database.insert(databaseHelper.TABLE, null, values);
        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
