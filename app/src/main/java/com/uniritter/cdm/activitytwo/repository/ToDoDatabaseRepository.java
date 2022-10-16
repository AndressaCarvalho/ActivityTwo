package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.uniritter.cdm.activitytwo.helper.RequestHelper;
import com.uniritter.cdm.activitytwo.helper.RequestType;
import com.uniritter.cdm.activitytwo.model.IToDoModel;
import com.uniritter.cdm.activitytwo.helper.ToDoDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoDatabaseRepository {
    private IToDoModel toDo;
    private List<IToDoModel> toDos;
    private Context context;
    private ToDoDatabaseHelper databaseHelper;
    private static ToDoDatabaseRepository instance;
    private static UserDatabaseRepository userInstance;
    private SQLiteDatabase database;

    private ToDoDatabaseRepository(Context context) {
        super();
        this.context = context;
        this.toDo = null;
        this.toDos = new ArrayList<>();
        this.databaseHelper = new ToDoDatabaseHelper(context);
        this.userInstance = UserDatabaseRepository.getInstance(context);
    }

    public static ToDoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ToDoDatabaseRepository(context);
        }
        return instance;
    }

    public IToDoModel getToDoById(int toDoId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.COMPLETED };
        String[] conditionArgs = { Long.toString(toDoId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.toDo = new ToDoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        (cursor.getInt(4) != 0));
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.toDo;
    }

    public List<IToDoModel> getToDosByUserId(int toDoUserId) {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.COMPLETED };
        String[] conditionArgs = { Long.toString(toDoUserId) };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, this.databaseHelper.USER_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.toDo = new ToDoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        (cursor.getInt(4) != 0));
                this.toDos.add(toDo);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.toDos;
    }

    public List<IToDoModel> getAllToDos() {
        String[] columns = { this.databaseHelper.ID, this.databaseHelper.USER_ID, this.databaseHelper.TITLE, this.databaseHelper.COMPLETED };
        this.database = this.databaseHelper.getReadableDatabase();

        Cursor cursor = this.database.query(this.databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                this.toDo = new ToDoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        (cursor.getInt(4) != 0));
                this.toDos.add(toDo);
            } while (cursor.moveToNext());
        }

        this.database.close();

        return this.toDos;
    }

    public RequestHelper addToDo(int toDoUserId, String toDoTitle, boolean toDoCompleted){
        if (toDoTitle != null && !TextUtils.isEmpty(toDoTitle)){
            if (toDoUserId != 0 && this.userInstance.getUserById(toDoUserId) != null){
                ContentValues values = new ContentValues();
                long result;

                this.database = this.databaseHelper.getWritableDatabase();
                values.put(this.databaseHelper.USER_ID, toDoUserId);
                values.put(this.databaseHelper.TITLE, toDoTitle);
                values.put(this.databaseHelper.COMPLETED, toDoCompleted);

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

    public RequestHelper updateToDo(int toDoId, int toDoUserId, String toDoTitle, boolean toDoCompleted){
        if (toDoTitle != null && !TextUtils.isEmpty(toDoTitle)){
            if (toDoId != 0 && this.getToDoById(toDoId) != null && toDoUserId != 0 && this.userInstance.getUserById(toDoUserId) != null){
                ContentValues values = new ContentValues();
                long result;

                String[] conditionArgs = { Long.toString(toDoId) };
                values.put(this.databaseHelper.USER_ID, toDoUserId);
                values.put(this.databaseHelper.TITLE, toDoTitle);
                values.put(this.databaseHelper.COMPLETED, toDoCompleted);
                this.database = this.databaseHelper.getWritableDatabase();

                result = this.database.update(this.databaseHelper.TABLE,  values, this.databaseHelper.ID + " = ?", conditionArgs);

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

    public RequestHelper deleteToDo(int toDoId, int toDoUserId){
        if (toDoId != 0 && this.getToDoById(toDoId) != null && toDoUserId != 0 && this.userInstance.getUserById(toDoUserId) != null){
            long result;

            String[] conditionArgs = { Long.toString(toDoId) };
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
