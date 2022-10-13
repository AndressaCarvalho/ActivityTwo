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
        database = databaseHelper.getWritableDatabase();
        getAllToDos();
    }

    public static ToDoDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ToDoDatabaseRepository(context);
        }
        return instance;
    }

    public List<IToDoModel> getToDosByUserId(int userId) {
        List<IToDoModel> toDosList = new ArrayList<>();

        for(IToDoModel td : toDos) {
            if (userId == td.getToDoUserId()) {
                toDosList.add(td);
            }
        }
        return toDosList;
    }

    public void getAllToDos() {
        String stm = "SELECT " + databaseHelper.ID + ", " + databaseHelper.USER_ID + ", " + databaseHelper.TITLE + ", " + databaseHelper.COMPLETED + " FROM " + databaseHelper.TABLE + ";";
        Cursor cursor = database.rawQuery(stm, null);
        cursor.moveToFirst();
        do {
            IToDoModel toDo = new ToDoModel(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    (cursor.getInt(4) != 0));
            toDos.add(toDo);
        } while (cursor.moveToNext());
    }

    public boolean addToDo(int toDoUserId, String toDoTitle, boolean toDoCompleted){
        ContentValues values;
        long result;

        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
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
