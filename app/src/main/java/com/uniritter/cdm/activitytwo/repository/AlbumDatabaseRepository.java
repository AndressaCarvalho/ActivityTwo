package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.AlbumDatabaseHelper;
import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.AlbumModel;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumDatabaseRepository {
    private IAlbumModel album;
    private List<IAlbumModel> albums;
    private Context context;
    private AlbumDatabaseHelper databaseHelper;
    private static AlbumDatabaseRepository instance;
    private static UserDatabaseRepository userInstance;
    private SQLiteDatabase database;

    private AlbumDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.album = null;
        this.albums = new ArrayList<>();
        this.databaseHelper = new AlbumDatabaseHelper(context);
        this.userInstance = UserDatabaseRepository.getInstance(context);
    }

    public static AlbumDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumDatabaseRepository(context);
        }
        return instance;
    }

    public IAlbumModel getAlbumById(int albumId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE };
        String[] conditionArgs = { Long.toString(albumId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.album = new AlbumModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.album;
    }

    public List<IAlbumModel> getAlbumsByUserId(int albumUserId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE };
        String[] conditionArgs = { Long.toString(albumUserId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.USER_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.album = new AlbumModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3));
                this.albums.add(album);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.albums;
    }

    public List<IAlbumModel> getAllAlbums() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.album = new AlbumModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3));
                this.albums.add(album);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.albums;
    }

    public RequestHelper addAlbum(int albumUserId, String albumTitle){
        if (albumTitle != null && !TextUtils.isEmpty(albumTitle)){
            if (albumUserId != 0 && this.userInstance.getUserById(albumUserId) != null){
                ContentValues values = new ContentValues();
                long result;

                this.database = this.databaseHelper.getWritableDatabase();
                values.put(this.databaseHelper.USER_ID, albumUserId);
                values.put(this.databaseHelper.TITLE, albumTitle);

                result = this.database.insert(this.databaseHelper.TABLE, null, values);

                this.database.close();

                if (result == -1)
                    return new RequestHelper(false, RequestType.BadRequest);
                else
                    return new RequestHelper(true, RequestType.OK);
            }

            return new RequestHelper(true, RequestType.NotFound);
        }

        return new RequestHelper(true, RequestType.NotAcceptable);
    }

    public RequestHelper updateAlbum(int albumId, int albumUserId, String albumTitle){
        if (albumTitle != null && !TextUtils.isEmpty(albumTitle)){
            if (albumId != 0 && this.getAlbumById(albumId) != null && albumUserId != 0 && this.userInstance.getUserById(albumUserId) != null){
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(albumId) };
                values.put(this.databaseHelper.USER_ID, albumUserId);
                values.put(this.databaseHelper.TITLE, albumTitle);
                this.database = this.databaseHelper.getWritableDatabase();

                result = this.database.update(databaseHelper.TABLE, values, this.databaseHelper.ID + " = ?", conditionArgs);

                this.database.close();

                if (result == -1)
                    return new RequestHelper(false, RequestType.BadRequest);
                else
                    return new RequestHelper(true, RequestType.OK);
            }

            return new RequestHelper(true, RequestType.NotFound);
        }

        return new RequestHelper(true, RequestType.NotAcceptable);
    }

    public RequestHelper deleteAlbum(int albumId, int albumUserId){
        if (albumId != 0 && this.getAlbumById(albumId) != null && albumUserId != 0 && this.userInstance.getUserById(albumUserId) != null){
            long result;

            String[] conditionArgs = { Long.toString(albumId) };
            this.database = this.databaseHelper.getReadableDatabase();

            result = this.database.delete(this.databaseHelper.TABLE, this.databaseHelper.ID + " = ?", conditionArgs);

            this.database.close();

            if (result == -1)
                return new RequestHelper(false, RequestType.BadRequest);
            else
                return new RequestHelper(true, RequestType.OK);
        }

        return new RequestHelper(true, RequestType.NotFound);
    }
}
