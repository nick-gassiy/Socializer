package com.android.badoonmysql;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.AppWindows.MainWindow;
import com.android.badoonmysql.DB.Constants;
import com.android.badoonmysql.DB.RequestHandler;
import com.android.badoonmysql.DB.SharedPrefManager;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.Registration.RegistrationActivity;
import com.android.badoonmysql.Users.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            String email = SharedPrefManager.getInstance(this).getUserEmail();
            User user = new User();
            userLogin(email, user);
        } else
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }

    private void userLogin(String email, User user) {
        final String userMail = email;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        Utils.fillUsersFields(user, obj);

                        Toast.makeText(getApplicationContext(), "User login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, MainWindow.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mail", userMail);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
