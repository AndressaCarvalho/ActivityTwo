package com.uniritter.cdm.activitytwo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniritter.cdm.activitytwo.model.IToDoModel;
import com.uniritter.cdm.activitytwo.helper.ToDoDatabaseHelper;
import com.uniritter.cdm.activitytwo.model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoDatabaseRepository {
    private List<IToDoModel> toDos;
    private Context context;
    private ToDoDatabaseHelper databaseHelper;
    private static ToDoDatabaseRepository instance;
    private SQLiteDatabase database;

    private ToDoDatabaseRepository(Context context) {
        super();
        this.context = context;
        toDos = new ArrayList<>();
        databaseHelper = new ToDoDatabaseHelper(context);
    }

    public static ToDoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ToDoDatabaseRepository(context);
        }
        return instance;
    }

    public List<IToDoModel> getToDosByUserId(int userId) {
        String[] columns = { databaseHelper.ID, databaseHelper.USER_ID, databaseHelper.TITLE, databaseHelper.COMPLETED };
        String[] conditionArgs = { Long.toString(userId) };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, databaseHelper.USER_ID + " = ?", conditionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                IToDoModel toDo = new ToDoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        (cursor.getInt(4) != 0));
                toDos.add(toDo);
            } while (cursor.moveToNext());
        }

        database.close();

        return toDos;
    }

    public List<IToDoModel> getAllToDos() {
        String[] columns = { databaseHelper.ID, databaseHelper.USER_ID, databaseHelper.TITLE, databaseHelper.COMPLETED };
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(databaseHelper.TABLE, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                IToDoModel toDo = new ToDoModel(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        (cursor.getInt(4) != 0));
                toDos.add(toDo);
            } while (cursor.moveToNext());
        }

        database.close();

        return toDos;
    }

    public boolean addToDo(int toDoUserId, String toDoTitle, boolean toDoCompleted){
        ContentValues values = new ContentValues();
        long result;

        database = databaseHelper.getWritableDatabase();
        values.put(databaseHelper.USER_ID, toDoUserId);
        values.put(databaseHelper.TITLE, toDoTitle);
        values.put(databaseHelper.COMPLETED, toDoCompleted);

        result = database.insert(databaseHelper.TABLE, null, values);

        database.close();

        if (result == -1)
            return false;
        else
            return true;
    }
}
