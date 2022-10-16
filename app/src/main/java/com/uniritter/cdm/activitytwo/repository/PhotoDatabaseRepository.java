package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.IPhotoModel;
import com.uniritter.cdm.activitytwo.helper.PhotoDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoDatabaseRepository {
    private IPhotoModel photo;
    private List<IPhotoModel> photos;
    private Context context;
    private PhotoDatabaseHelper databaseHelper;
    private static PhotoDatabaseRepository instance;
    private static UserDatabaseRepository userInstance;
    private static AlbumDatabaseRepository albumInstance;
    private SQLiteDatabase database;

    private PhotoDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.photo = null;
        this.photos = new ArrayList<>();
        this.databaseHelper = new PhotoDatabaseHelper(context);
        this.userInstance = UserDatabaseRepository.getInstance(context);
        this.albumInstance = AlbumDatabaseRepository.getInstance(context);
    }

    public static PhotoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoDatabaseRepository(context);
        }
        return instance;
    }

    public IPhotoModel getPhotoById(int photoId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.ALBUM_ID, this.databaseHelper.TITLE, this.databaseHelper.URL, this.databaseHelper.THUMBNAIL_URL };
        String[] conditionArgs = { Long.toString(photoId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.photo = new PhotoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.photo;
    }

    public List<IPhotoModel> getPhotosByAlbumId(int albumId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.ALBUM_ID, this.databaseHelper.TITLE, this.databaseHelper.URL, this.databaseHelper.THUMBNAIL_URL };
        String[] conditionArgs = { Long.toString(albumId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ALBUM_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.photo = new PhotoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                this.photos.add(photo);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.photos;
    }

    public List<IPhotoModel> getAllPhotos() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.ALBUM_ID, this.databaseHelper.TITLE, this.databaseHelper.URL, this.databaseHelper.THUMBNAIL_URL };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.photo = new PhotoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                this.photos.add(photo);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.photos;
    }

    public RequestHelper addPhoto(int photoAlbumId, int photoAlbumUserId, String photoTitle, String photoUrl, String photoThumbnailUrl){
        if (photoTitle != null && !TextUtils.isEmpty(photoTitle) && photoUrl != null && !TextUtils.isEmpty(photoUrl) && photoThumbnailUrl != null && !TextUtils.isEmpty(photoThumbnailUrl)){
            if (photoAlbumId != 0 && this.albumInstance.getAlbumById(photoAlbumId) != null && photoAlbumUserId != 0 && this.userInstance.getUserById(photoAlbumUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                values.put(this.databaseHelper.ALBUM_ID, photoAlbumId);
                values.put(this.databaseHelper.TITLE, photoTitle);
                values.put(this.databaseHelper.URL, photoUrl);
                values.put(this.databaseHelper.THUMBNAIL_URL, photoThumbnailUrl);
                this.database = this.databaseHelper.getWritableDatabase();

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

    public RequestHelper updatePhoto(int photoId, int photoAlbumId, int photoAlbumUserId, String photoTitle, String photoUrl, String photoThumbnailUrl){
        if (photoTitle != null && !TextUtils.isEmpty(photoTitle) && photoUrl != null && !TextUtils.isEmpty(photoUrl) && photoThumbnailUrl != null && !TextUtils.isEmpty(photoThumbnailUrl)){
            if (photoId != 0 && this.getPhotoById(photoId) != null && photoAlbumId != 0 && this.albumInstance.getAlbumById(photoAlbumId) != null && photoAlbumUserId != 0 && this.userInstance.getUserById(photoAlbumUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(photoId) };
                values.put(this.databaseHelper.ALBUM_ID, photoAlbumId);
                values.put(this.databaseHelper.TITLE, photoTitle);
                values.put(this.databaseHelper.URL, photoUrl);
                values.put(this.databaseHelper.THUMBNAIL_URL, photoThumbnailUrl);
                this.database = this.databaseHelper.getWritableDatabase();

                result = this.database.update(this.databaseHelper.TABLE, values, this.databaseHelper.ID + " = ?", conditionArgs);

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

    public RequestHelper deletePhoto(int photoId, int photoAlbumId, int photoAlbumUserId){
        if (photoId != 0 && this.getPhotoById(photoId) != null && photoAlbumId != 0 && this.albumInstance.getAlbumById(photoAlbumId) != null && photoAlbumUserId != 0 && this.userInstance.getUserById(photoAlbumUserId) != null) {
            long result;

            String[] conditionArgs = { Long.toString(photoId) };
            this.database = this.databaseHelper.getWritableDatabase();

            result = this.database.delete(this.databaseHelper.TABLE, this.databaseHelper.ID + " = ?", conditionArgs);

            database.close();

            if (result == -1)
                return new RequestHelper(false, RequestType.BadRequest);
            else
                return new RequestHelper(true, RequestType.OK);
        }

        return new RequestHelper(true, RequestType.NotFound);
    }
}
