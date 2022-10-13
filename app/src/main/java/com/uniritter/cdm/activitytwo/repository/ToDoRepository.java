package com.uniritter.cdm.activitytwo.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.uniritter.cdm.activitytwo.model.IToDoModel;
import com.uniritter.cdm.activitytwo.model.ToDoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToDoRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<IToDoModel> toDos;
    private Context context;
    private static ToDoRepository instance;

    private ToDoRepository(Context context) {
        super();
        this.context = context;
        toDos = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos",
                null, this, this);
        queue.add(jaRequest);
    }

    public static ToDoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ToDoRepository(context);
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

    public List<IToDoModel> getAllToDos() {
        return toDos;
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                toDos.add(new ToDoModel(
                        json.getInt("id"),
                        json.getInt("userId"),
                        json.getString("title"),
                        json.getBoolean("completed")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("ToDoRepository", "Error! Returned message: " + error.getMessage());
    }
}
