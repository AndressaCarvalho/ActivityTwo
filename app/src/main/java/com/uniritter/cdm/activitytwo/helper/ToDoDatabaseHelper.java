package com.uniritter.cdm.activitytwo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBAppTwo";
    private static final Integer DB_VERSION = 1;
    public static final String TABLE = "toDos";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String COMPLETED = "completed";

    public ToDoDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "CREATE TABLE " + this.TABLE + " (" + this.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + this.USER_ID + " INTEGER, " + this.TITLE + " TEXT, " + this.COMPLETED + " INTEGER, FOREIGN KEY(" + this.USER_ID + ") REFERENCES users(" + this.ID + "));";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + this.TABLE);
        onCreate(sqLiteDatabase);
    }
}
