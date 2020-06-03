package com.android.badoonmysql.Registration;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.badoonmysql.AppWindows.MainWindow;
import com.android.badoonmysql.DB.DBHandler;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.Permissions;
import com.android.badoonmysql.Users.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterPhotoActivity extends AppCompatActivity {

    private ImageButton addPhotosImageButton;
    private Button registrationNextButton;
    private final static int RC_IMAGE_PICKER = 17;
    private Uri selectedImageUri;

    private  byte[] fileInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_photo);

        Intent givenIntent = getIntent();   //Intent предыдущего класса
        User user = givenIntent.getParcelableExtra("user");

        user.setPermissions(Permissions.SIMPLE);

        registrationNextButton = findViewById(R.id.registrationNextBtn);
        addPhotosImageButton = findViewById(R.id.registerPhotoImgBtn);

        addPhotosImageButton.setOnClickListener(v -> {
            chooseImage();
        });

        registrationNextButton.setOnClickListener(v ->  {
            String mail = user.getMail();
            String password = user.getPassword();
            createAndAddUser(mail, password, user);
        });
    }

    private void createAndAddUser(String email, String password, User addUser) {

        Toast.makeText(RegisterPhotoActivity.this, "Подождите, идет обработка даных", Toast.LENGTH_LONG).show();
        DBHandler.addUserToDatabase(RegisterPhotoActivity.this, addUser);

        Toast.makeText(RegisterPhotoActivity.this, "Пользователь успешно создан", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterPhotoActivity.this, MainWindow.class);
        intent.putExtra("user", addUser);
        startActivity(intent);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), RC_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {

            selectedImageUri = data.getData();

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //here you can choose quality factor in third parameter(ex. i chosen 25)
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            fileInBytes = baos.toByteArray();

            addPhotosImageButton.setImageURI(selectedImageUri);
            //Продолжение
        }
    }
}