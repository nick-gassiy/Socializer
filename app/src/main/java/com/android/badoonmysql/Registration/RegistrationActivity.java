package com.android.badoonmysql.Registration;

import android.Manifest;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.Login.SignInActivity;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        CircleImageView maleImageView = findViewById(R.id.maleImgBtn);
        CircleImageView femaleImageView = findViewById(R.id.femaleImgBtn);
        Button loginButton = findViewById(R.id.logInBtn);

        //Переходы по страницам
        maleImageView.setOnClickListener(v -> nextActivity("Male"));
        femaleImageView.setOnClickListener(v -> nextActivity("Female"));
        loginButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(RegistrationActivity.this, SignInActivity.class);
            startActivity(newIntent);
        });

    }

    private void nextActivity(String gender) {
        Intent intent = new Intent(RegistrationActivity.this, RegisterNameActivity.class);
        User user = new User();
        user.setGender(gender);
        startActivity(Utils.goToNextActivity(user, intent));
    }
}
