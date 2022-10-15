package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.helper.UserDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepository {
    private List<IUserModel> users;
    private Context context;
    private UserDatabaseHelper databaseHelper;
    private static UserDatabaseRepository instance;
    private SQLiteDatabase database;

    private UserDatabaseRepository(Context context) {
        super();
        this.context = context;
        users = new ArrayList<>();
        databaseHelper = new UserDatabaseHelper(context);
    }

    public static UserDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabaseRepository(context);
        }
        return instance;
    }

    public IUserModel getUserByUsernameOrEmail(String userNameEmail, String userPassword) {
        IUserModel user = null;

        String[] columns = { databaseHelper.ID, databaseHelper.USER_NAME, databaseHelper.EMAIL, databaseHelper.PASSWORD };
        String[] conditionArgs = { userNameEmail, userNameEmail, userPassword };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, "(" + databaseHelper.USER_NAME + " = ? OR " + databaseHelper.EMAIL + " = ?) AND " + databaseHelper.PASSWORD + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                user = new UserModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
            } while (cursor.moveToNext());
        }

        database.close();

        return user;
    }

    public List<IUserModel> getAllUsers() {
        String[] columns = { databaseHelper.ID, databaseHelper.USER_NAME, databaseHelper.EMAIL, databaseHelper.PASSWORD };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                IUserModel user = new UserModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                users.add(user);
            } while (cursor.moveToNext());
        }

        database.close();

        return users;
    }

    public IUserModel validateCredentials(String userNameEmail, String userPassword) {
        IUserModel user = null;

        if (userNameEmail != null && !TextUtils.isEmpty(userNameEmail) && userPassword != null && !TextUtils.isEmpty(userPassword)) {
            user = getUserByUsernameOrEmail(userNameEmail, userPassword);
        }

        return user;
    }

    public boolean addUser(String userName, String userEmail, String userPassword){
        ContentValues values = new ContentValues();
        long result;

        database = databaseHelper.getWritableDatabase();
        values.put(databaseHelper.USER_NAME, userName);
        values.put(databaseHelper.EMAIL, userEmail);
        values.put(databaseHelper.PASSWORD, userPassword);

        result = database.insert(databaseHelper.TABLE, null, values);

        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
