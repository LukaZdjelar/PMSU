package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.CommentListAdapter;
import com.example.pmsu_projekat.model.ArticleFilter;
import com.example.pmsu_projekat.model.Order;
import com.example.pmsu_projekat.model.OrderFilter;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.service.SellerServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    Seller seller;
    ListView mListView;
    Double averageRating;
    String role;
    Long claim_id;
    Long customer_id;
    Long seller_id;
    Long user_id;
    OrderFilter filter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Bundle b = getIntent().getExtras();
        if(b != null){
            seller_id = b.getLong("seller_id");
        }
        filter = (OrderFilter)getIntent().getSerializableExtra("filter");

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

        getSeller();
        getComments();
        articlesButton();
        filterButton();
        toolbarAndDrawer();
    }

    private void getSeller(){
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
        Call<Seller> call = sellerServiceAPI.get(seller_id);

        call.enqueue(new Callback<Seller>() {
            @Override
            public void onResponse(Call<Seller> call, Response<Seller> response) {
                seller = response.body();
                averageRating();
                sellerData();
            }

            @Override
            public void onFailure(Call<Seller> call, Throwable t) {
                Log.d("getSeller fail",t.toString());
            }
        });
    }

    private void getComments(){
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

        OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);

        if(filter == null){
            Call<List<Order>> call = orderServiceAPI.comments(seller_id);

            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    mListView = findViewById(R.id.listview_restaurant_comments);
                    mListView.setAdapter(new CommentListAdapter(getApplicationContext(), R.layout.layout_comment, response.body()));
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Log.d("getComments fail",t.toString());
                }
            });
        }else{
            Call<List<Order>> call = orderServiceAPI.filter(filter);

            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    Log.d("Comments", response.body().toString());
                    mListView = findViewById(R.id.listview_restaurant_comments);
                    mListView.setAdapter(new CommentListAdapter(getApplicationContext(), R.layout.layout_comment, response.body()));
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Log.d("getComments fail",t.toString());
                }
            });
        }
    }

    private void averageRating(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        SellerServiceAPI sellerServiceAPI = retrofit.create(SellerServiceAPI.class);
        Call<Double> call = sellerServiceAPI.average(seller_id);

        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                averageRating = response.body();
                TextView tvRating = findViewById(R.id.restaurant_activity_rating);
                tvRating.setText(averageRating.toString());
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    private void sellerData(){

        TextView tvName = findViewById(R.id.restaurant_activity_name);
        TextView tvAddress = findViewById(R.id.restaurant_activity_adress);
        TextView tvDate = findViewById(R.id.restaurant_activity_date);

        tvName.setText(seller.getNaziv());
        tvAddress.setText(seller.getAdresa());
        tvDate.setText(seller.getPoslujeOd());
    }

    private void articlesButton(){
        Button articlesButton = findViewById(R.id.button_articles);
        articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, ArticlesActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id" , seller_id);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void filterButton(){
        Button filterButton = findViewById(R.id.button_filter_comments);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantActivity.this, FilterCommentsActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id", seller_id);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void toolbarAndDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_restaurant);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_restaurant);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_restaurant);
        Menu menu =navigationView.getMenu();
        if (role.equals("ROLE_PRODAVAC")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.prodavnice_menu);
            menu.removeItem(R.id.korisnici_menu);
            user_id = seller_id;
        }
        if (role.equals("ROLE_KUPAC")){
            menu.removeItem(R.id.kreiraj_artikal_menu);
            menu.removeItem(R.id.korisnici_menu);
            user_id = customer_id;
        }
        if (role.equals("ROLE_ADMINISTRATOR")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.kreiraj_artikal_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Kreiraj artikal")){
                    Intent intent = new Intent(RestaurantActivity.this, CreateArticleActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("seller_id",seller_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Prodavnice")){
                    Intent intent = new Intent(RestaurantActivity.this, RestaurantsActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Recenzije")){
                    Intent intent = new Intent(RestaurantActivity.this, OrdersActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("customer_id", customer_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Korisnici")){
                    Intent intent = new Intent(RestaurantActivity.this, UsersActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Promeni lozinku")){
                    Intent intent = new Intent(RestaurantActivity.this, PasswordActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("user_id" , user_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Odjavi se")){
                    SharedPreferences sp = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);
                    sp.edit().remove("token").apply();
                    Intent intent = new Intent(RestaurantActivity.this, LoginActivity.class);
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
