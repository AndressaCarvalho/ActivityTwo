package com.uniritter.cdm.activitytwo.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.uniritter.cdm.activitytwo.model.AddressModel;
import com.uniritter.cdm.activitytwo.model.CompanyModel;
import com.uniritter.cdm.activitytwo.model.GeoModel;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Listener<JSONArray>, Response.ErrorListener {
    private List<IUserModel> users;
    private Context context;
    private static UserRepository instance;

    private UserRepository(Context context) {
        super();
        this.context = context;
        users = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/users",
                null, this, this);
        queue.add(jaRequest);
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public List<IUserModel> getAllUsers() {
        return users;
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

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                users.add(new UserModel(
                        json.getInt("id"),
                        json.getString("username"),
                        json.getString("email"),
                        json.getString("name"),
                        new AddressModel(json.getJSONObject("address").getString("zipcode"),
                                json.getJSONObject("address").getString("street"),
                                json.getJSONObject("address").getString("suite"),
                                new GeoModel(json.getJSONObject("address").getJSONObject("geo").getString("lat"), json.getJSONObject("address").getJSONObject("geo").getString("lng"))),
                        json.getString("phone"),
                        json.getString("website"),
                        new CompanyModel(json.getJSONObject("company").getString("name"), json.getJSONObject("company").getString("catchPhrase"), json.getJSONObject("company").getString("bs"))
                        ));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("UserRepository", "Error! Returned message: " + error.getMessage());
    }
}
