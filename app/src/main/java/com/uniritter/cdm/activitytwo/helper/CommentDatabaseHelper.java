package com.uniritter.cdm.activitytwo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBAppTwo";
    private static final Integer DB_VERSION = 1;
    public static final String TABLE = "comments";
    public static final String ID = "id";
    public static final String POST_ID = "postId";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String BODY = "body";

    public CommentDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "CREATE TABLE " + this.TABLE + " (" + this.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + this.POST_ID + " INTEGER, " + this.NAME + " TEXT, " + this.EMAIL + " TEXT, " + this.BODY + " TEXT, FOREIGN KEY(" + this.POST_ID + ") REFERENCES posts(" + this.ID + "));";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + this.TABLE);
        onCreate(sqLiteDatabase);
    }
}
