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

import com.uniritter.cdm.activitytwo.model.AlbumModel;
import com.uniritter.cdm.activitytwo.model.IAlbumModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<IAlbumModel> albums;
    private Context context;
    private static AlbumRepository instance;

    private AlbumRepository(Context context) {
        super();
        this.context = context;
        this.albums = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums",
                null, this, this);
        queue.add(jaRequest);
    }

    public static AlbumRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumRepository(context);
        }
        return instance;
    }

    public List<IAlbumModel> getAlbumsByUserId(int userId) {
        List<IAlbumModel> albumsList = new ArrayList<>();

        for(IAlbumModel a : this.albums) {
            if (userId == a.getAlbumUserId()) {
                albumsList.add(a);
            }
        }
        return albumsList;
    }

    public List<IAlbumModel> getAllAlbums() {
        return this.albums;
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                this.albums.add(new AlbumModel(
                        json.getInt("id"),
                        json.getInt("userId"),
                        json.getString("title")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("AlbumRepository", "Error! Returned message: " + error.getMessage());
    }
}
