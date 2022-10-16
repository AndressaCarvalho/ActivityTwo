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

import com.uniritter.cdm.activitytwo.model.IPhotoModel;
import com.uniritter.cdm.activitytwo.model.PhotoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<IPhotoModel> photos;
    private Context context;
    private static PhotoRepository instance;

    private PhotoRepository(Context context) {
        super();
        this.context = context;
        this.photos = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/photos",
                null, this, this);
        queue.add(jaRequest);
    }

    public static PhotoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoRepository(context);
        }
        return instance;
    }

    public List<IPhotoModel> getPhotosByAlbumId(int albumId) {
        List<IPhotoModel> photosList = new ArrayList<>();

        for(IPhotoModel p : this.photos) {
            if (albumId == p.getPhotoAlbumId()) {
                photosList.add(p);
            }
        }
        return photosList;
    }

    public List<IPhotoModel> getAllPhotos() {
        return this.photos;
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                this.photos.add(new PhotoModel(
                        json.getInt("id"),
                        json.getInt("albumId"),
                        json.getString("title"),
                        json.getString("url"),
                        json.getString("thumbnailUrl")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("PhotoRepository", "Error! Returned message: " + error.getMessage());
    }
}
