package com.example.pmsu_projekat.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.RestaurantListAdapter;

import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ListView mListView = (ListView) findViewById(R.id.listview_restaurants);

        ArrayList<String> restaurants = new ArrayList<String>();
        restaurants.add(new String("Kod Zokija"));
        restaurants.add(new String("Fruskogorac"));
        restaurants.add(new String("Piazza"));

        RestaurantListAdapter adapter = new RestaurantListAdapter(this, R.layout.layout_restaurant, restaurants);
        mListView.setAdapter(adapter);
    }
}
