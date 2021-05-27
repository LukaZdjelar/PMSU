package com.example.pmsu_projekat.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pmsu_projekat.MainActivity;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.RestaurantListAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_restaurants);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_restaurants);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_restaurants);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(RestaurantsActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        ListView mListView = (ListView) findViewById(R.id.listview_restaurants);

        ArrayList<String> restaurants = new ArrayList<String>();
        restaurants.add(new String("Kod Zokija"));
        restaurants.add(new String("Fruskogorac"));
        restaurants.add(new String("Mafia spaghetti"));
        restaurants.add(new String("Debeli"));
        restaurants.add(new String("Cika Pera"));
        restaurants.add(new String("Mexico burrito"));
        restaurants.add(new String("Bobi Velemajstor"));
        restaurants.add(new String("Kralj Petar I"));
        restaurants.add(new String("Kyoto Sushi"));
        restaurants.add(new String("Njegusi"));
        restaurants.add(new String("Terra Incognita"));
        restaurants.add(new String("Piletina ispod saca"));
        restaurants.add(new String("Sarajevski cevap"));
        restaurants.add(new String("Taki Giros"));
        restaurants.add(new String("Biser"));
        restaurants.add(new String("Neapolis"));

        RestaurantListAdapter adapter = new RestaurantListAdapter(this, R.layout.layout_restaurant, restaurants);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
