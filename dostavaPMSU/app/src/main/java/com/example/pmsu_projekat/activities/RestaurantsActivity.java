package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.RestaurantListAdapter;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.service.SellerServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantsActivity extends AppCompatActivity {

    static final String TAG = RestaurantsActivity.class.getSimpleName();
    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    private ListView mListView;
    List<Seller> sellers = new ArrayList<>();
    String role;
    Long claim_id;
    Long customer_id;
    Long seller_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        SharedPreferences sp = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);

        JWT jwt = new JWT(sp.getString(LoginActivity.token, ""));
        Claim claimRole = jwt.getClaim("role");
        role = claimRole.asString();
        Claim claimId = jwt.getClaim("id");
        claim_id = claimId.asLong();
        if (role.equals("ROLE_KUPAC")){
            customer_id=claim_id;
        }if (role.equals("ROLE_PRODAVAC")){
            seller_id=claim_id;
        }

        listView();
        toolbarAndDrawer();
    }

    @Override
    public void onResume(){
        super.onResume();
        getRestaurants();
    }

    private void getRestaurants(){
        SharedPreferences preferences = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);
        String token = preferences.getString(LoginActivity.token, "");

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(LocalHost.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SellerServiceAPI sellerServiceAPI = retrofit.create(SellerServiceAPI.class);
        Call<List<Seller>> call = sellerServiceAPI.getAll();

        call.enqueue(new Callback<List<Seller>>() {
            @Override
            public void onResponse(Call<List<Seller>> call, Response<List<Seller>> response) {

                //Log.d("DataCheck",new Gson().toJson(response.body()));

                sellers = response.body();

                mListView.setAdapter(new RestaurantListAdapter(getApplicationContext(), R.layout.layout_restaurant, (ArrayList<Seller>) sellers));
            }

            @Override
            public void onFailure(Call<List<Seller>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void listView(){
        mListView = (ListView) findViewById(R.id.listview_restaurants);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RestaurantsActivity.this, RestaurantActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id", sellers.get(position).getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void toolbarAndDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_restaurants);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_restaurants);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_restaurants);
        Menu menu =navigationView.getMenu();
        if (role.equals("ROLE_PRODAVAC")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.prodavnice_menu);
            menu.removeItem(R.id.korisnici_menu);
        }
        if (role.equals("ROLE_KUPAC")){
            menu.removeItem(R.id.kreiraj_artikal_menu);
            menu.removeItem(R.id.korisnici_menu);
        }
        if (role.equals("ROLE_ADMINISTRATOR")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.kreiraj_artikal_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Kreiraj artikal")){
                    Intent intent = new Intent(RestaurantsActivity.this, CreateArticleActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("seller_id",seller_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Prodavnice")){
                    Intent intent = new Intent(RestaurantsActivity.this, RestaurantsActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Recenzije")){
                    Intent intent = new Intent(RestaurantsActivity.this, OrdersActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("customer_id", customer_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Korisnici")){
                    Intent intent = new Intent(RestaurantsActivity.this, UsersActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Odjavi se")){
                    Intent intent = new Intent(RestaurantsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                mDrawerLayout.closeDrawers();
                return true;
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
