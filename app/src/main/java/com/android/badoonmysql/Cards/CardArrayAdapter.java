package com.android.badoonmysql.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.badoonmysql.R;

import java.util.List;

public class CardArrayAdapter extends  ArrayAdapter{
    Context context;

    public CardArrayAdapter(Context context, int resourceID, List<Card> items) {
        super(context, resourceID, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Card cardItem = (Card) getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_suka, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.userName);
        //ImageView image = (ImageView) convertView.findViewById(R.id.userMainPhoto);

        name.setText(cardItem.getUserName());
        //Glide.with(getContext()).load(cardItem.getUserMainPhotoUrl()).into(image);

        return convertView;
    }
}
