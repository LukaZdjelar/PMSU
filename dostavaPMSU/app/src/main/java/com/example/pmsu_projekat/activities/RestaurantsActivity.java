package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.RestaurantListAdapter;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.service.SellerServiceAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantsActivity extends AppCompatActivity {

    static final String TAG = RestaurantsActivity.class.getSimpleName();
    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    static final String BASE_URL = "http://192.168.0.13:8080/";
    private ListView mListView;
    List<Seller> sellers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        mListView = (ListView) findViewById(R.id.listview_restaurants);

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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RestaurantsActivity.this, ArticlesActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", sellers.get(position).getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getRestaurants();
    }

    private void getRestaurants(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        SellerServiceAPI sellerServiceAPI = retrofit.create(SellerServiceAPI.class);
        Call<List<Seller>> call = sellerServiceAPI.getAll();

        call.enqueue(new Callback<List<Seller>>() {
            @Override
            public void onResponse(Call<List<Seller>> call, Response<List<Seller>> response) {

                Log.d("DataCheck",new Gson().toJson(response.body()));

                sellers = response.body();

                mListView.setAdapter(new RestaurantListAdapter(getApplicationContext(), R.layout.layout_restaurant, (ArrayList<Seller>) sellers));
            }

            @Override
            public void onFailure(Call<List<Seller>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
