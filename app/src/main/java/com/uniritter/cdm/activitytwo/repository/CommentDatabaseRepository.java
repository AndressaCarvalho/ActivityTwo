package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.CommentDatabaseHelper;
import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.CommentModel;
import com.uniritter.cdm.activitytwo.model.ICommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentDatabaseRepository {
    private ICommentModel comment;
    private List<ICommentModel> comments;
    private Context context;
    private CommentDatabaseHelper databaseHelper;
    private static CommentDatabaseRepository instance;
    private static UserDatabaseRepository userInstance;
    private static PostDatabaseRepository postInstance;
    private SQLiteDatabase database;

    private CommentDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.comment = null;
        this.comments = new ArrayList<>();
        this.databaseHelper = new CommentDatabaseHelper(context);
        this.userInstance = UserDatabaseRepository.getInstance(context);
        this.postInstance = PostDatabaseRepository.getInstance(context);
    }

    public static CommentDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommentDatabaseRepository(context);
        }
        return instance;
    }

    public ICommentModel getCommentById(int commentId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.POST_ID, this.databaseHelper.NAME, this.databaseHelper.EMAIL, this.databaseHelper.BODY };
        String[] conditionArgs = { Long.toString(commentId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.comment = new CommentModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.comment;
    }

    public List<ICommentModel> getCommentsByPostId(int commentPostId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.POST_ID, this.databaseHelper.NAME, this.databaseHelper.EMAIL, this.databaseHelper.BODY };
        String[] conditionArgs = { Long.toString(commentPostId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.POST_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.comment = new CommentModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                this.comments.add(comment);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.comments;
    }

    public List<ICommentModel> getAllComments() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.POST_ID, this.databaseHelper.NAME, this.databaseHelper.EMAIL, this.databaseHelper.BODY };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.comment = new CommentModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                this.comments.add(comment);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.comments;
    }

    public RequestHelper addComment(int commentPostId, int commentPostUserId, String commentName, String commentEmail, String commentBody){
        if (commentName != null && !TextUtils.isEmpty(commentName) && commentEmail != null && !TextUtils.isEmpty(commentEmail) && commentBody != null && !TextUtils.isEmpty(commentBody)){
            if (commentPostId != 0 && this.postInstance.getPostById(commentPostId) != null && commentPostUserId != 0 && this.userInstance.getUserById(commentPostUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                values.put(this.databaseHelper.POST_ID, commentPostId);
                values.put(this.databaseHelper.NAME, commentName);
                values.put(this.databaseHelper.EMAIL, commentEmail);
                values.put(this.databaseHelper.BODY, commentBody);
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

    public RequestHelper updateComment(int commentId, int commentPostId, int commentPostUserId, String commentName, String commentEmail, String commentBody){
        if (commentName != null && !TextUtils.isEmpty(commentName) && commentEmail != null && !TextUtils.isEmpty(commentEmail) && commentBody != null && !TextUtils.isEmpty(commentBody)){
            if (commentId != 0 && this.getCommentById(commentId) != null && commentPostId != 0 && this.postInstance.getPostById(commentPostId) != null && commentPostUserId != 0 && this.userInstance.getUserById(commentPostUserId) != null) {
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(commentId) };
                values.put(this.databaseHelper.NAME, commentName);
                values.put(this.databaseHelper.EMAIL, commentEmail);
                values.put(this.databaseHelper.BODY, commentBody);
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

    public RequestHelper deleteComment(int commentId, int commentPostId, int commentPostUserId){
        if (commentId != 0 && this.getCommentById(commentId) != null && commentPostId != 0 && this.postInstance.getPostById(commentPostId) != null && commentPostUserId != 0 && this.userInstance.getUserById(commentPostUserId) != null) {
            long result;

            String[] conditionArgs = { Long.toString(commentId) };
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
