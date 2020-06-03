package com.android.badoonmysql.DB;

import android.content.Intent;
import android.widget.Toast;
import com.android.badoonmysql.AppWindows.MainWindow;
import com.android.badoonmysql.AppWindows.MainWindowActivity;
import com.android.badoonmysql.Cards.Card;
import com.android.badoonmysql.Cards.Swipe;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.MainActivity;
import com.android.badoonmysql.Users.User;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import android.content.Context;
import com.google.android.gms.common.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHandler {

    private static String id;
    private static String name;
    private static String mail;
    private static String password;
    private static String gender;
    private static String bio;
    private static String city;
    private static String birthday;
    private static String mainPhotoPath;
    private static String latitude;
    private static String longitude;
    private static Response.Listener<String> listener;
    private static Response.ErrorListener errorListener;

    // Основные
    public static void addUserToDatabase(Context context, User user) {
        setValues(user);
        setListeners(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("mail", mail);
                params.put("password", password);
                params.put("gender", gender);
                params.put("bio", bio);
                params.put("city", city);
                params.put("birthday", birthday);
                params.put("main_photo_path", mainPhotoPath);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void updateUser(Context context, User user) {
        setValues(user);
        setListeners(context);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_UPDATE, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                setAllParams(params);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    private static void loadUserToArray(Context context, User user, ArrayList<User> users) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Utils.fillUsersFields(user, obj);
                    users.add(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", user.getID());
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void addSwipeToDatabase(Context context, int wasVisitedByID, int whoWasVisitedID, boolean isLeftSwipe) {
        setListeners(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_SWIPE, listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("was_visited_by", String.valueOf(wasVisitedByID));
                params.put("who_was_visited", String.valueOf(whoWasVisitedID));
                params.put("is_left_swipe", String.valueOf(isLeftSwipe));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void deleteSwipes(Context context, int id) {
        setListeners(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_SWIPES, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("was_visited_by", String.valueOf(id));
                params.put("who_was_visited", String.valueOf(id));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void addSympathyToDatabase(Context context, int user_1, int user_2) {
        setListeners(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_SYMPATHY, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_1", String.valueOf(user_1));
                params.put("user_2", String.valueOf(user_2));
                params.put("user_2_checked", "false");
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void loadUncheckedSympathies(Context context, ArrayList<User> users, int myId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_SYMPATHIES, response -> {
            try {
                JSONArray jsonObject = new JSONArray(response);

                for (int i = 0; i < jsonObject.length(); i++) {
                    User local = new User();
                    String s = jsonObject.getString(i);
                    JSONObject object = new JSONObject(s);
                    int id = object.getInt("user_1");
                    local.setID(String.valueOf(id));
                    loadUserToArray(context, local, users);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_2", String.valueOf(myId));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    // Вспомогательные
    private static void setValues(User user) {
        id = user.getID();
        name = user.getName();
        mail = user.getMail();
        password = user.getPassword();
        gender = user.getGender();
        bio = user.getBio();
        city = user.getCity();
        birthday = user.getBirthday();
        mainPhotoPath = user.getMainPhoto();
        latitude = String.valueOf(user.getLatitude());
        longitude = String.valueOf(user.getLongitude());
    }

    private static void setAllParams(Map<String, String> params) {
        params.put("id", id);
        params.put("name", name);
        params.put("mail", mail);
        params.put("password", password);
        params.put("gender", gender);
        params.put("bio", bio);
        params.put("city", city);
        params.put("birthday", birthday);
        params.put("main_photo_path", mainPhotoPath);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
    }

    private static void setListeners(Context context){
        listener = (response -> Toast.makeText(context, response, Toast.LENGTH_SHORT).show());
        errorListener = (error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
