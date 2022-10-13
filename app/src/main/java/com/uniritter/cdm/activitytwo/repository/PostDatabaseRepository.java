package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.helper.PostDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public class PostDatabaseRepository {
    private List<IPostModel> posts;
    private Context context;
    private PostDatabaseHelper databaseHelper;
    private static PostDatabaseRepository instance;
    private SQLiteDatabase database;

    private PostDatabaseRepository(Context context) {
        super();
        this.context = context;
        posts = new ArrayList<>();
        databaseHelper = new PostDatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        getAllPosts();
    }

    public static PostDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PostDatabaseRepository(context);
        }
        return instance;
    }

    public List<IPostModel> getPostsByUserId(int userId) {
        List<IPostModel> postsList = new ArrayList<>();

        for(IPostModel p : posts) {
            if (userId == p.getPostUserId()) {
                postsList.add(p);
            }
        }

        return postsList;
    }

    public void getAllPosts() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.USER_ID + ", " + databaseHelper.TITLE + ", " + databaseHelper.BODY + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
        do {
            IPostModel post = new PostModel(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4));
            posts.add(post);
        } while (cursor.moveToNext());
    }

    public boolean addPost(int postUserId, String postTitle, String postBody){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(databaseHelper.USER_ID, postUserId);
        values.put(databaseHelper.TITLE, postTitle);
        values.put(databaseHelper.BODY, postBody);

        result = database.insert(databaseHelper.TABLE, null, values);
        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
