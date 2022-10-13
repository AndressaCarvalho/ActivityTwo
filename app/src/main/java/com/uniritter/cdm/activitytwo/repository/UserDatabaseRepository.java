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
        database = databaseHelper.getWritableDatabase();
        getAllUsers();
    }

    public static UserDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabaseRepository(context);
        }
        return instance;
    }

    public IUserModel getUserByUsernameOrEmail(String userNameEmail) {
        IUserModel user = null;

        for(IUserModel u : users) {
            if (userNameEmail.equals(u.getUserName()) || userNameEmail.equals(u.getUserEmail())) {
                user = u;
            }
        }
        return user;
    }

    public void getAllUsers() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.USER_NAME + ", " + databaseHelper.EMAIL + ", " + databaseHelper.PASSWORD + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
        do {
            IUserModel user = new UserModel(
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            users.add(user);
        } while (cursor.moveToNext());
    }

    public IUserModel validateCredentials(String userNameEmail, String userPassword) {
        IUserModel user = null;

        if (userNameEmail == null || TextUtils.isEmpty(userNameEmail) || userPassword == null || TextUtils.isEmpty(userPassword)) {
            return null;
        } else {
            user = getUserByUsernameOrEmail(userNameEmail);
            if (userPassword.equals(user.getUserPassword())) {
                return user;
            }
        }

        return null;
    }

    public boolean addUser(String userName, String userEmail, String userPassword){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
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
