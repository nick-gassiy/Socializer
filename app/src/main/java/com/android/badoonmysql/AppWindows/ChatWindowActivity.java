package com.android.badoonmysql.AppWindows;

import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.android.badoonmysql.R;
import com.android.badoonmysql.Users.User;

public class ChatWindowActivity extends Fragment {

    private User mainUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mainUser = (User) getArguments().get("user");
        }
        return inflater.inflate(R.layout.chat_container,container,false);
    }

}
