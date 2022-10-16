package com.uniritter.cdm.activitytwo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBAppTwo";
    private static final Integer DB_VERSION = 1;
    public static final String TABLE = "photos";
    public static final String ID = "id";
    public static final String ALBUM_ID = "albumId";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String THUMBNAIL_URL = "thumbnailUrl";

    public PhotoDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "CREATE TABLE " + this.TABLE + " (" + this.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + this.ALBUM_ID + " INTEGER, " + this.TITLE + " TEXT, " + this.URL + " TEXT, " + this.THUMBNAIL_URL + " TEXT, FOREIGN KEY(" + this.ALBUM_ID + ") REFERENCES albums(" + this.ID + "));";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + this.TABLE);
        onCreate(sqLiteDatabase);
    }
}
