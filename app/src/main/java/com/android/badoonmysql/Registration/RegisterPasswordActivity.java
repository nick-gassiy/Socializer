package com.android.badoonmysql.Registration;

import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterPasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerPasswordButton;
    private String password;
    private String confirmPassword;
    private static final String SYMBOLS = "[!#$:%&*()_+=|<>?{}\\[\\]~]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        passwordEditText = findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.registerConfirmPasswordEditText);
        registerPasswordButton = findViewById(R.id.registerPasswordBtn);

        TextInputLayout confirmPasswordInputLayout = findViewById(R.id.confirmPasswordTextInput);


        Intent givenIntent = getIntent();   //Intent предыдущего класса
        User user = givenIntent.getParcelableExtra("user");
        Intent intent = new Intent(RegisterPasswordActivity.this, RegisterPhotoActivity.class);
        registerPasswordButton.setOnClickListener(v -> {
            password = passwordEditText.getText().toString().trim();
            confirmPassword = confirmPasswordEditText.getText().toString().trim();
            if ((!(Utils.isFieldNotValid(password, SYMBOLS))) && (!(Utils.isFieldNotValid(confirmPassword, SYMBOLS))))
                confirmPasswordInputLayout.setError("Некоректный пароль");
            else if ( !(password.equals(confirmPassword)))
                confirmPasswordInputLayout.setError("Пароли не совпадают");
             else if (((passwordEditText.length() < 7) && (confirmPasswordEditText.length() < 7)))
                confirmPasswordInputLayout.setError("Пароль должен содержать более 7 символов");
             else {
                user.setPassword(passwordEditText.getText().toString().replace(" ", ""));
                startActivity(Utils.goToNextActivity(user, intent));
            }
        });
    }
}
