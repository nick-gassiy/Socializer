package com.android.badoonmysql.AppWindows;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileWindowActivity extends Fragment {

    private String nameAge = "";
    private String path = "";
    private User mainUser;

    private CircleImageView circleImageView;
    private TextView nameAgeTextView;
    private ImageButton editButton;
    private ImageButton upperSettings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mainUser = (User) getArguments().get("user");
        }
        return inflater.inflate(R.layout.profile_container,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AsyncRoomFirebaseInfo().execute();

        circleImageView = getView().findViewById(R.id.profile_image);                   // Фото
        nameAgeTextView = getView().findViewById(R.id.nameAgeTextView);                 // Имя
        editButton = getView().findViewById(R.id.editButton);                           // Редактировать
        upperSettings = getView().findViewById(R.id.upperSettings);                     // Настройки

        upperSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), SettingsWindowActivity.class);
            intent.putExtra("user", mainUser);
            startActivity(intent);
        });
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), EditWindowActivity.class);
            intent.putExtra("user", mainUser);
            startActivity(intent);
        });
    }
    
    private void setViews() {
        //Picasso.get().load(path).into(circleImageView);
        nameAgeTextView.setText(nameAge);
    }

    private class AsyncRoomFirebaseInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            nameAge = mainUser.getName() + " " + mainUser.getBirthday();
            path = mainUser.getMainPhoto();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setViews();
        }
    }
}
