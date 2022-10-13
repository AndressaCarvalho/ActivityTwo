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
        database = databaseHelper.getWritableDatabase();
        getAllComments();
    }

    public static CommentDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommentDatabaseRepository(context);
        }
        return instance;
    }

    public List<ICommentModel> getCommentsByPostId(int postId) {
        List<ICommentModel> commentsList = new ArrayList<>();

        for(ICommentModel c : comments) {
            if (postId == c.getCommentPostId()) {
                commentsList.add(c);
            }
        }

        return commentsList;
    }

    public void getAllComments() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.POST_ID + ", " + databaseHelper.NAME + ", " + databaseHelper.EMAIL + ", " + databaseHelper.BODY + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
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

    public boolean addComment(int commentPostId, String commentName, String commentEmail, String commentBody){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
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
