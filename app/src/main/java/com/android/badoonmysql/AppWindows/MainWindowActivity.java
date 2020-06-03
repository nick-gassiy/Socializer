package com.android.badoonmysql.AppWindows;

import android.content.Intent;
import com.android.badoonmysql.Cards.Swipe;
import com.android.badoonmysql.DB.DBHandler;
import com.android.badoonmysql.Helpers.OwnParser;
import com.android.badoonmysql.MainActivity;
import com.android.badoonmysql.R;
import android.content.Context;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import com.android.badoonmysql.Cards.Card;
import com.android.badoonmysql.Cards.CardArrayAdapter;
import com.android.badoonmysql.DB.Constants;
import com.android.badoonmysql.DB.RequestHandler;
import com.android.badoonmysql.Helpers.Utils;
import com.android.badoonmysql.Users.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainWindowActivity extends Fragment {

    private User mainUser;
    private EditText areaSize;
    private Button testButton;
    private Button confirmCity;
    private AutoCompleteTextView selectCity;
    private static final ArrayList<String> cities = new ArrayList<>();

    private CardArrayAdapter arrayAdapter;
    private List<Card> rowItems;

    private Response.Listener<String> listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null)
            mainUser = (User) getArguments().get("user");
        return inflater.inflate(R.layout.main_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        areaSize = getActivity().findViewById(R.id.areaSize);           // Искомая зона в координатах
        testButton = getActivity().findViewById(R.id.test);             // Кнопка загрузки по координатам
        confirmCity = getActivity().findViewById(R.id.confirmCity);     // Кнопка выбора города
        selectCity = getActivity().findViewById(R.id.selectCity);       // Выбранный город
        rowItems = new ArrayList<Card>();                               // Список карточек
        parseCities(); // Потом убрать

        testButton.setOnClickListener(v -> loadByCoordinates());
        confirmCity.setOnClickListener(v -> loadByCity());
    }

    private void loadByCity() {
        loadCards();
        getUserForGeo(MainWindowActivity.this.getContext());
    }

    private void loadByCoordinates() {
        loadCards();
        getUserForGeo(MainWindowActivity.this.getContext(), mainUser, Integer.parseInt(areaSize.getText().toString()));
    }

    private void getUserForGeo(Context context, User user, int radius) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_BY_COORDINATES, this::setResponseListener,
                error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("latitude", user.getLatitude() + "");
                params.put("longitude", user.getLongitude() + "");
                params.put("radius", radius + "");
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
    
    private void getUserForGeo(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_BY_CITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setResponseListener(response);
            }
        },error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String readyCity = selectCity.getText().toString();
                params.put("city", readyCity);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void setResponseListener(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray jsonArray = obj.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                User tmp = new User();
                Utils.fillUsersFields(tmp, jsonArray.getJSONObject(i));
                if (!tmp.getMail().equals(mainUser.getMail()))
                    wasVisitedCheck(tmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void wasVisitedCheck(User user) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_SWIPES, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);

                /*boolean a = jsonObject.getInt("was_visited_by") == Integer.parseInt(mainUser.getID());
                boolean b = jsonObject.getInt("who_was_visited") != Integer.parseInt(user.getID());

                if (a && b) {
                    Card item = new Card(user.getID(), user.getName());
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }*/
                //Обдумать
            } catch (JSONException e) {
                Card item = new Card(user.getID(), user.getName());
                rowItems.add(item);
                arrayAdapter.notifyDataSetChanged();
            }

        }, error -> Toast.makeText(MainWindowActivity.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("was_visited_by", mainUser.getID());
                params.put("who_was_visited", user.getID());
                return params;
            }
        };
        RequestHandler.getInstance(MainWindowActivity.this.getContext()).addToRequestQueue(stringRequest);
    }

    private void loadCards() {
        arrayAdapter = new CardArrayAdapter(this.getActivity(), R.layout.test_suka, rowItems);
        final SwipeFlingAdapterView flingContainer = getActivity().findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
                if (rowItems.size() == 0) {
                    Toast.makeText(MainWindowActivity.this.getContext(),
                            "Вы посетили всех пользователей или у вас отключен GPS", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String id = obj.getUserID();
                DBHandler.addSwipeToDatabase(MainWindowActivity.this.getContext(), Integer.parseInt(mainUser.getID()), Integer.parseInt(id), true);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Swipe swipe = new Swipe();
                Card obj = (Card) dataObject;
                String id = obj.getUserID();
                DBHandler.addSwipeToDatabase(MainWindowActivity.this.getContext(), Integer.parseInt(mainUser.getID()), Integer.parseInt(id), false);
                ifSympathy(swipe, Integer.parseInt(id));
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {}

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                //view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainWindowActivity.this.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseCities() {
        cities.clear();
        OwnParser.getCitiesFromXML(MainWindowActivity.this.getContext(), cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainWindowActivity.this.getActivity(), android.R.layout.simple_list_item_1, cities);
        selectCity.setAdapter(adapter);
    }

    private void ifSympathy(Swipe swipe, int thisID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_SWIPES, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Utils.fillSwipesFields(swipe, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (swipe.getId() != 0) {
                DBHandler.addSympathyToDatabase(MainWindowActivity.this.getContext(), swipe.getWhoWasVisited(), swipe.getWasVisitedBy());
                Toast.makeText(MainWindowActivity.this.getContext(), "Взаимная симпатия", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("was_visited_by", String.valueOf(thisID));
                params.put("who_was_visited", String.valueOf(mainUser.getID()));
                return params;
            }
        };
        RequestHandler.getInstance(MainWindowActivity.this.getContext()).addToRequestQueue(stringRequest);
    }
}