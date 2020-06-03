package com.android.badoonmysql.Registration;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.widget.TextView;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisterDateActivity extends AppCompatActivity {

    private TextView dateTextView;
    private TextView datePicker;
    private TextView ageTextView;
    private Button registerDateButton;
    private String userName;
    private TextView textViewMessage;
    Calendar dateAndTime= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_date);

        dateTextView = findViewById(R.id.dayTextView);
        ageTextView = findViewById(R.id.ageTV);
        registerDateButton = findViewById(R.id.registerDateBtn);
        datePicker = findViewById(R.id.datePicker);
        textViewMessage = findViewById(R.id.whatIsYourDateTV);

        Intent givenIntent = getIntent();   //Intent предыдущего класса
        User user = givenIntent.getParcelableExtra("user");
        userName = user.getName();
        textViewMessage.setText("Привет, " + userName + "! Когда у вас день рождения");
        Intent intent = new Intent(RegisterDateActivity.this, RegistrationEmailActivity.class);   //Intent в следующий класс
        registerDateButton.setOnClickListener(v -> {
            Calendar minAdultAge = new GregorianCalendar();
            minAdultAge.add(Calendar.YEAR, -18);
            if (minAdultAge.before(dateAndTime)) {
                ageTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.errorRed));
            } else {
                ageTextView.setVisibility(View.GONE);
                user.setBirthday(dateTextView.getText().toString());
                startActivity(Utils.goToNextActivity(user, intent));
            }
        });
    }

    public void onclick(View view) {
        setDate();
        datePicker.setVisibility(View.GONE);
    }

    private void setDate() {
        new DatePickerDialog(RegisterDateActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        dateTextView.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
};

