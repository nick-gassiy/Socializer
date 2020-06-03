package com.android.badoonmysql.Registration;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.DB.Constants;
import com.android.badoonmysql.DB.RequestHandler;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.Login.SignInActivity;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationEmailActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button registerEmailButton;
    private Button logInBtn;
    private TextInputLayout emailTextInputLayout;
    private static final String SYMBOLS = "[!#$:%&*()_+=|<>?{}\\[\\]~]";

    boolean isExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);

        emailEditText = findViewById(R.id.registerEmailEditText);
        registerEmailButton = findViewById(R.id.registerEmailBtn);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        logInBtn = findViewById(R.id.logInBtn);

        Intent givenIntent = getIntent();   //Intent предыдущего класса
        User user = givenIntent.getParcelableExtra("user");
        Intent intent = new Intent(RegistrationEmailActivity.this, RegisterPasswordActivity.class);   //Intent в следующий класс
        registerEmailButton.setOnClickListener(v -> {
            String mail = emailEditText.getText().toString().replace(" ", "");

            nextAction(mail, user, intent);
        });
    }

    public void nextAction(String mail, User user, Intent intent) {
        final String userMail = mail;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EMAIL_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean tmp = Boolean.parseBoolean(obj.getString("error"));
                    if (tmp) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        isExist = true;
                        logInBtn.setVisibility(View.VISIBLE);

                        logInBtn.setOnClickListener(v -> {
                            Intent loginIntent = new Intent(RegistrationEmailActivity.this, SignInActivity.class);
                            loginIntent.putExtra("mail", mail);
                            startActivity(loginIntent);
                        });
                    } else {
                        isExist = false;

                        boolean check = Utils.isFieldNotValid(emailEditText.getText().toString().trim(), SYMBOLS);
                        boolean check2 = !(emailEditText.getText().toString().contains("@"));
                        boolean check3 = !(emailEditText.getText().toString().contains("."));

                        if (!check || check2 || check3) {
                            emailTextInputLayout.setError("Ошибка ввода email");
                        } else {
                            user.setMail(mail);
                            startActivity(Utils.goToNextActivity(user, intent));
                        }
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