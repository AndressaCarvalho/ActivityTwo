package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.helper.UserDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepository {
    private IUserModel user;
    private List<IUserModel> users;
    private Context context;
    private UserDatabaseHelper databaseHelper;
    private static UserDatabaseRepository instance;
    private SQLiteDatabase database;

    private UserDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.user = null;
        this.users = new ArrayList<>();
        this.databaseHelper = new UserDatabaseHelper(context);
    }

    public static UserDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabaseRepository(context);
        }
        return instance;
    }

    public IUserModel getUserById(int userId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USERNAME, this.databaseHelper.EMAIL, this.databaseHelper.PASSWORD };
        String[] conditionArgs = { Long.toString(userId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.user = new UserModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.user;
    }

    public IUserModel getUserByUsernameOrEmail(String userNameEmail) {
        String[] columns = { databaseHelper.ID, databaseHelper.USERNAME, databaseHelper.EMAIL, databaseHelper.PASSWORD };
        String[] conditionArgs = { userNameEmail, userNameEmail };
        database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(databaseHelper.TABLE, columns, databaseHelper.USERNAME + " = ? OR " + databaseHelper.EMAIL + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.user = new UserModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
            } while (cursor.moveToNext());
        }

        database.close();

        return this.user;
    }

    public List<IUserModel> getAllUsers() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USERNAME, this.databaseHelper.EMAIL, this.databaseHelper.PASSWORD };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.user = new UserModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                this.users.add(user);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.users;
    }

    public IUserModel validateCredentials(String userNameEmail, String userPassword) {
        IUserModel u = null;

        if (userNameEmail != null && !TextUtils.isEmpty(userNameEmail) && userPassword != null && !TextUtils.isEmpty(userPassword)) {
            this.getUserByUsernameOrEmail(userNameEmail);

            if (userPassword.equals(this.user.getUserPassword())) {
                u = this.user;
            }
        }

        return u;
    }

    public RequestHelper addUser(String userName, String userEmail, String userPassword, String userPasswordConfirm){
        if (userName != null && !TextUtils.isEmpty(userName) && userEmail != null && !TextUtils.isEmpty(userEmail) && userPassword != null && !TextUtils.isEmpty(userPassword) && userPasswordConfirm != null && !TextUtils.isEmpty(userPasswordConfirm)) {
            if (userPassword.equals(userPasswordConfirm)){
                if (this.getUserByUsernameOrEmail(userName) == null && this.getUserByUsernameOrEmail(userEmail) == null) {
                    ContentValues values = new ContentValues();
                    long result;

                    this.database = this.databaseHelper.getWritableDatabase();
                    values.put(this.databaseHelper.USERNAME, userName);
                    values.put(this.databaseHelper.EMAIL, userEmail);
                    values.put(this.databaseHelper.PASSWORD, userPassword);

                    result = this.database.insert(this.databaseHelper.TABLE, null, values);

                    this.database.close();

                    if (result == -1)
                        return new RequestHelper(false, RequestType.BadRequest);
                    else
                        return new RequestHelper(true, RequestType.OK);
                }

                return new RequestHelper(true, RequestType.Found);
            }

            return new RequestHelper(true, RequestType.Conflict);
        }

        return new RequestHelper(true, RequestType.NotAcceptable);
    }

    public RequestHelper updateUser(int userId, String userName, String userEmail, String userPassword){
        if (userName != null && !TextUtils.isEmpty(userName) && userEmail != null && !TextUtils.isEmpty(userEmail) && userPassword != null && !TextUtils.isEmpty(userPassword)) {
            if (userId != 0 && this.getUserById(userId) != null) {
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(userId) };
                values.put(this.databaseHelper.USERNAME, userName);
                values.put(this.databaseHelper.EMAIL, userEmail);
                values.put(this.databaseHelper.PASSWORD, userPassword);
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

    public RequestHelper deleteUser(int userId){
        if (userId != 0 && this.getUserById(userId) != null) {
            long result;

            String[] conditionArgs = { Long.toString(userId) };
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
