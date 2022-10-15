package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniritter.cdm.activitytwo.helper.CommentDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.CommentModel;
import com.uniritter.cdm.activitytwo.model.ICommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentDatabaseRepository {
    private List<ICommentModel> comments;
    private Context context;
    private CommentDatabaseHelper databaseHelper;
    private static CommentDatabaseRepository instance;
    private SQLiteDatabase database;

    private CommentDatabaseRepository(Context context) {
        super();
        this.context = context;
        comments = new ArrayList<>();
        databaseHelper = new CommentDatabaseHelper(context);
    }

    public static CommentDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommentDatabaseRepository(context);
        }
        return instance;
    }

    public List<ICommentModel> getCommentsByPostId(int postId) {
        String[] columns = { databaseHelper.ID, databaseHelper.POST_ID, databaseHelper.NAME, databaseHelper.EMAIL, databaseHelper.BODY };
        String[] conditionArgs = { Long.toString(postId) };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, databaseHelper.POST_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ICommentModel comment = new CommentModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                comments.add(comment);
            } while (cursor.moveToNext());
        }

        database.close();

        return comments;
    }

    public List<ICommentModel> getAllComments() {
        String[] columns = { databaseHelper.ID, databaseHelper.POST_ID, databaseHelper.NAME, databaseHelper.EMAIL, databaseHelper.BODY };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ICommentModel comment = new CommentModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                comments.add(comment);
            } while (cursor.moveToNext());
        }

        database.close();

        return comments;
    }

    public boolean addComment(int commentPostId, String commentName, String commentEmail, String commentBody){
        ContentValues values = new ContentValues();
        long result;

        database = databaseHelper.getWritableDatabase();
        values.put(databaseHelper.POST_ID, commentPostId);
        values.put(databaseHelper.NAME, commentName);
        values.put(databaseHelper.EMAIL, commentEmail);
        values.put(databaseHelper.BODY, commentBody);

        result = database.insert(databaseHelper.TABLE, null, values);

        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
