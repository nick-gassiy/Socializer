package com.android.badoonmysql.AppWindows;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.badoonmysql.DB.DBHandler;
import com.android.badoonmysql.DB.SharedPrefManager;
import com.android.badoonmysql.MainActivity;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingsWindowActivity extends AppCompatActivity {

    private Button signOutButton;
    private Button changePasswordButton;
    private Button cleanHistoryButton;
    private Button uploadPhotosButton;
    private EditText changePasswordEditText;
    private User user;
    private ArrayList<Uri> imageList = new ArrayList<>();
    private Uri imageUri;

    private static final int PICK_IMAGE = 1;

    private User mainUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_container);

        Intent givenIntent = getIntent();
        mainUser = givenIntent.getParcelableExtra("user");
        signOutButton = findViewById(R.id.signOut);                                                 // Выйти
        changePasswordButton = findViewById(R.id.changePassword);                                   // Сменить пароль
        uploadPhotosButton = findViewById(R.id.uploadPhotos);                                       // Добавить новые фотографии
        cleanHistoryButton = findViewById(R.id.cleanHistory);                                       // Очистить историю свайпов
        changePasswordEditText = findViewById(R.id.changePasswordEditText);                         // Новый пароль

        changePasswordButton.setOnClickListener(v -> {
            String password = changePasswordEditText.getText().toString();
            mainUser.setPassword(password);
            DBHandler.updateUser(SettingsWindowActivity.this, mainUser);
        });
        signOutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(SettingsWindowActivity.this, MainActivity.class));
        });
        cleanHistoryButton.setOnClickListener(v -> DBHandler.deleteSwipes(SettingsWindowActivity.this, Integer.parseInt(mainUser.getID())));
        uploadPhotosButton.setOnClickListener(v -> {
            /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent, PICK_IMAGE);*/
        });
    }

    private void loadPhotos() {
        //StorageReference userImagesStorageReference = FirebaseStorage.getInstance().getReference().child("users_images");
        for (int i = 0; i < imageList.size(); i++) {
            Uri individualImage = imageList.get(i);

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), individualImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //here you can choose quality factor in third parameter(ex. i chosen 25)
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] fileInBytes = baos.toByteArray();


            //StorageReference name = userImagesStorageReference.child(individualImage.getLastPathSegment() + (int)(Math.random() * 100));

            //name.putBytes(fileInBytes).addOnSuccessListener(taskSnapshot -> {
              // name.getDownloadUrl().addOnSuccessListener(uri -> {
              //    String url = String.valueOf(uri);
              //    storeLink(url);
              // });
           // });
        }
    }

    private void storeLink(String url) {
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mainUser.getID()).child("otherPhotos");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ImgLink", url);

        //databaseReference.push().setValue(hashMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {

                int countClipData = data.getClipData().getItemCount();
                int currentImageSelected = 0;

                while (currentImageSelected < countClipData) {
                    imageUri = data.getClipData().getItemAt(currentImageSelected).getUri();
                    imageList.add(imageUri);
                    currentImageSelected++;
                }

                loadPhotos();

            } else {
                //Toast.makeText(SettingsWindowActivity.this, "Выберите несколько фотографий", Toast.LENGTH_SHORT).show();
                imageUri = data.getData();
                imageList.add(imageUri);
                loadPhotos();
            }
        }
    }
}