package com.example.pmsu_projekat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.OrderListAdapter;
import com.example.pmsu_projekat.adapters.ReviewArticleListAdapter;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.Order;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputLayout;
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

public class ReviewActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    List<CartItem> cartItems = new ArrayList<>();
    Long order_id;
    private ListView mListView;
    Order order;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle b = getIntent().getExtras();
        if(b != null){
            order_id = b.getLong("order_id");
        }

        findOrder();
        reviewButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartItems();
    }

    private void getCartItems(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);
        Call<List<CartItem>> call = orderServiceAPI.getOneStavke(order_id);

        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                cartItems = response.body();

                mListView = (ListView) findViewById(R.id.listview_cart_items_review);
                mListView.setAdapter(new ReviewArticleListAdapter(getApplicationContext(), R.layout.layout_review_order, cartItems));
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
            }
        });
    }

    private void findOrder(){
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
        Call<Order> call = orderServiceAPI.getOne(order_id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                order = response.body();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    private void orderData(){
        TextInputLayout tiComment = findViewById(R.id.review_comment);
        order.setKomentar(tiComment.getEditText().getText().toString());

        TextInputLayout tiRating = findViewById(R.id.review_rating);
        order.setOcena(Integer.valueOf(tiRating.getEditText().getText().toString()));

        CheckBox chbAnon = findViewById(R.id.checboxAnon);
        order.setAnonimniKomentar(chbAnon.isChecked());
    }

    private void reviewButton(){
        Button reviewButton = findViewById(R.id.buttonReview);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderData();
                if (order.getOcena() == 1 || order.getOcena() == 2 || order.getOcena() == 3 || order.getOcena() == 4 || order.getOcena() == 5){
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
                    Call<Order> call = orderServiceAPI.review(order_id, order);

                    call.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            finish();
                            Toast.makeText(ReviewActivity.this, "Uspesno ocenjeno", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(ReviewActivity.this, "Ocena mora biti od 1 do 5", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }

//    private void listView(){
//        mListView = (ListView) findViewById(R.id.listview_cart_items_review);
//    }
}
