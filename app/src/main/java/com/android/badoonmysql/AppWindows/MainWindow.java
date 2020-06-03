package com.android.badoonmysql.AppWindows;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.android.badoonmysql.DB.DBHandler;
import com.android.badoonmysql.DB.SharedPrefManager;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainWindow extends AppCompatActivity {
    private ArrayList<User> users = new ArrayList<>();
    private User mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        Intent givenIntent = getIntent();
        mainUser = givenIntent.getParcelableExtra("user");

        new SympathiesLoader().execute();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            Bundle bundle = new Bundle();
            bundle.putParcelable("user", mainUser);

            switch (item.getItemId()) {
                case R.id.action_chat:
                    selectedFragment = new ChatWindowActivity();
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.action_profile:
                    selectedFragment = new ProfileWindowActivity();
                    selectedFragment.setArguments(bundle);
                    break;
                default:
                    selectedFragment = new MainWindowActivity();
                    selectedFragment.setArguments(bundle);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                    selectedFragment).commit();
            return true;
        });
    }

    private class SympathiesLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Загрузка массива новых симпатий
            DBHandler.loadUncheckedSympathies(MainWindow.this, users, Integer.parseInt(mainUser.getID()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainWindow.this, users.size() + " новый симпатий", Toast.LENGTH_SHORT).show();
        }
    }
}