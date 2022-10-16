package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.helper.PostDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public class PostDatabaseRepository {
    private IPostModel post;
    private List<IPostModel> posts;
    private Context context;
    private PostDatabaseHelper databaseHelper;
    private static PostDatabaseRepository instance;
    private static UserDatabaseRepository userInstance;
    private SQLiteDatabase database;

    private PostDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.post = null;
        this.posts = new ArrayList<>();
        this.databaseHelper = new PostDatabaseHelper(context);
        this.userInstance = UserDatabaseRepository.getInstance(context);
    }

    public static PostDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PostDatabaseRepository(context);
        }
        return instance;
    }

    public IPostModel getPostById(int postId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.BODY };
        String[] conditionArgs = { Long.toString(postId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.post = new PostModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.post;
    }

    public List<IPostModel> getPostsByUserId(int postUserId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.BODY };
        String[] conditionArgs = { Long.toString(postUserId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.USER_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.post = new PostModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4));
                this.posts.add(post);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.posts;
    }

    public List<IPostModel> getAllPosts() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.BODY };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.post = new PostModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4));
                this.posts.add(post);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.posts;
    }

    public RequestHelper addPost(int postUserId, String postTitle, String postBody){
        if (postTitle != null && !TextUtils.isEmpty(postTitle) && postBody != null && !TextUtils.isEmpty(postBody)){
            if (postUserId != 0 && this.userInstance.getUserById(postUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                values.put(this.databaseHelper.USER_ID, postUserId);
                values.put(this.databaseHelper.TITLE, postTitle);
                values.put(this.databaseHelper.BODY, postBody);
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

    public RequestHelper updatePost(int postId, int postUserId, String postTitle, String postBody){
        if (postTitle != null && !TextUtils.isEmpty(postTitle) && postBody != null && !TextUtils.isEmpty(postBody)){
            if (postId != 0 && this.getPostById(postId) != null && postUserId != 0 && this.userInstance.getUserById(postUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(postId) };
                values.put(this.databaseHelper.TITLE, postTitle);
                values.put(this.databaseHelper.BODY, postBody);
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

    public RequestHelper deletePost(int postId, int postUserId){
        if (postId != 0 && this.getPostById(postId) != null && postUserId != 0 && this.userInstance.getUserById(postUserId) != null) {
            long result;

            String[] conditionArgs = { Long.toString(postId) };
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
