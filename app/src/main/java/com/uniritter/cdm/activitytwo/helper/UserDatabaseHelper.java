package com.uniritter.cdm.activitytwo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBAppTwo";
    private static final Integer DB_VERSION = 1;
    public static final String TABLE = "users";
    public static final String ID = "id";
    public static final String USER_NAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public UserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT UNIQUE, " + EMAIL + " TEXT UNIQUE, " + PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
