package com.android.badoonmysql.AppWindows;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import com.android.badoonmysql.DB.DBHandler;
import com.android.badoonmysql.Helpers.OwnParser;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.Location.Coordinates;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.google.android.gms.location.*;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditWindowActivity extends AppCompatActivity {

    private TextInputEditText aboutTextInputEditText;
    private ImageView enterBioCircleImageView;
    private Button setGeoData;
    private Button setCity;
    private AutoCompleteTextView selectCityTest;

    private static ArrayList<String> cities = new ArrayList<>();
    private User mainUser;

    private int PERMISSION_ID = 44;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_container);

        Intent givenIntent = getIntent();
        mainUser = givenIntent.getParcelableExtra("user");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        aboutTextInputEditText = findViewById(R.id.aboutTextInputEditText);                         // Текст "О себе"
        enterBioCircleImageView = findViewById(R.id.enterBioCircleImageView);                       // Подтвердить "О себе"
        setGeoData = findViewById(R.id.setGeoData);
        setCity = findViewById(R.id.setCity);
        selectCityTest = findViewById(R.id.selectCityTest);

        cities.clear();
        OwnParser.getCitiesFromXML(EditWindowActivity.this, cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditWindowActivity.this, android.R.layout.simple_list_item_1, cities);
        selectCityTest.setAdapter(adapter);

        enterBioCircleImageView.setOnClickListener(v -> saveUsersBio());
        setGeoData.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(EditWindowActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                Coordinates coordinates = new Coordinates(fusedLocationProviderClient);
                coordinates.setCoordinates(EditWindowActivity.this,  mainUser);
                DBHandler.updateUser(EditWindowActivity.this, mainUser);
            } else {
                ActivityCompat.requestPermissions(EditWindowActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
            }
        });
        setCity.setOnClickListener(v -> {
            String city = selectCityTest.getText().toString();
            mainUser.setCity(city);
            DBHandler.updateUser(EditWindowActivity.this, mainUser);
        });
    }

    private void saveUsersBio() {
        String text = aboutTextInputEditText.getText().toString();
        mainUser.setBio(text);
        DBHandler.updateUser(EditWindowActivity.this, mainUser);
    }
}