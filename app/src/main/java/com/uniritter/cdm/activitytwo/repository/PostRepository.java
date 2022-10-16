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

import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.model.PostModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<IPostModel> posts;
    private Context context;
    private static PostRepository instance;

    private PostRepository(Context context) {
        super();
        this.context = context;
        this.posts = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null, this, this);
        queue.add(jaRequest);
    }

    public static PostRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PostRepository(context);
        }
        return instance;
    }

    public List<IPostModel> getPostsByUserId(int userId) {
        List<IPostModel> postsList = new ArrayList<>();

        for(IPostModel p : this.posts) {
            if (userId == p.getPostUserId()) {
                postsList.add(p);
            }
        }

        return postsList;
    }

    public List<IPostModel> getAllPosts() {
        return this.posts;
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                this.posts.add(new PostModel(
                        json.getInt("id"),
                        json.getInt("userId"),
                        json.getString("title"),
                        json.getString("body")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("PostRepository", "Error! Returned message: " + error.getMessage());
    }
}
