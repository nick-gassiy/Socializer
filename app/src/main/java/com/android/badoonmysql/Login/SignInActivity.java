package com.android.badoonmysql.Login;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.AppWindows.MainWindow;
import com.android.badoonmysql.DB.Constants;
import com.android.badoonmysql.DB.RequestHandler;
import com.android.badoonmysql.DB.SharedPrefManager;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText loginEmailEditText;
    private TextInputEditText loginPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent givenIntent = getIntent();

        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        Button logInButton = findViewById(R.id.logInActivityBtn);

        try {
            loginEmailEditText.setText(givenIntent.getStringExtra("mail"));
        } catch (Exception ex) {}

        logInButton.setOnClickListener(v -> {
            String email = loginEmailEditText.getText().toString().replace(" ", "");
            String password = loginPasswordEditText.getText().toString().replace(" ", "");

            User user = new User();
            userLogin(email, password, user);
        });
    }

    private void userLogin(String email, String password, User user) {
        final String userMail = email;
        final String userPassword = password;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                obj.getInt("id"),
                                obj.getString("mail"),
                                obj.getString("password"));
                        Utils.fillUsersFields(user, obj);

                        Toast.makeText(getApplicationContext(), "User login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignInActivity.this, MainWindow.class);
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
                params.put("password", userPassword);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
