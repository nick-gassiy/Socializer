package com.android.badoonmysql.Registration;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterNameActivity extends AppCompatActivity {

    private Button registerNameButton;
    private EditText nameEditText;
    private TextInputLayout nameTextInputLayout;
    private static final String SYMBOLS1 = "[!@#$:%&*()+=|<>?{}\\[\\]~]";
    private static final String SYMBOLS2 = "[0-9]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        nameEditText = findViewById(R.id.registerNameEditText);
        registerNameButton = findViewById(R.id.registerNameBtn);
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);

        Intent givenIntent = getIntent();   //Intent предыдущего класса
        User user = givenIntent.getParcelableExtra("user");
        Intent intent = new Intent(RegisterNameActivity.this, RegisterDateActivity.class);   //Intent в следующий класс

        registerNameButton.setOnClickListener(v -> {
            String tmp =  nameEditText.getText().toString().replace(" ", "");
            if (Utils.isFieldNotValid(tmp, SYMBOLS1, SYMBOLS2))
                nameTextInputLayout.setError("Ошибка ввода имени пользователя");
            else {
                user.setName(tmp);
                startActivity(Utils.goToNextActivity(user, intent));
            }
        });
    }
}
