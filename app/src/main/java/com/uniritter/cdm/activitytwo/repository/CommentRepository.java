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

import com.uniritter.cdm.activitytwo.model.CommentModel;
import com.uniritter.cdm.activitytwo.model.ICommentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<ICommentModel> comments;
    private Context context;
    private static CommentRepository instance;

    private CommentRepository(Context context) {
        super();
        this.context = context;
        this.comments = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/comments",
                null, this, this);
        queue.add(jaRequest);
    }

    public static CommentRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommentRepository(context);
        }
        return instance;
    }

    public List<ICommentModel> getAllComments() {
        return this.comments;
    }

    public List<ICommentModel> getCommentsByPostId(int postId) {
        List<ICommentModel> commentsList = new ArrayList<>();

        for(ICommentModel c : this.comments) {
            if (postId == c.getCommentPostId()) {
                commentsList.add(c);
            }
        }

        return commentsList;
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                this.comments.add(new CommentModel(
                        json.getInt("id"),
                        json.getInt("postId"),
                        json.getString("name"),
                        json.getString("email"),
                        json.getString("body")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("CommentRepository", "Error! Returned message: " + error.getMessage());
    }
}
