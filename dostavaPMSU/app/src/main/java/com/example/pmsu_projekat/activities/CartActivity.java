package com.example.pmsu_projekat.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.CartItemListAdapter;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.Order;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
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

public class CartActivity extends AppCompatActivity {

    ListView mListView;
    List<CartItem> cartItems = new ArrayList<>();
    Retrofit retrofit = null;
    Long order_id = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            order_id = b.getLong("order_id");
        }

        displayMetrics();
        finishOrderButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartItems();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        order_id = 0L;
    }

    public void displayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height * 0.6));
        getWindow().setGravity(Gravity.BOTTOM);
    }

    private void finishOrderButton() {
        Button orderButton = findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);
                String token = preferences.getString(LoginActivity.token, "");

                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
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

                if (mListView == null || mListView.getCount() == 0) {
                    order_id = 0L;
                }
                if (order_id > 0) {
                    OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);
                    Call<Order> call = orderServiceAPI.order(order_id);

                    call.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Toast t = Toast.makeText(getApplicationContext(), "Uspesno poruceno", Toast.LENGTH_SHORT);
                            t.show();
                            order_id = 0L;
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            Log.e("poruci fail", t.toString());
                        }
                    });
                } else {
                    Toast t = Toast.makeText(getApplicationContext(), "Korpa je prazna", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    private void cartItems() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (order_id > 0) {
            OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);
            Call<List<CartItem>> call = orderServiceAPI.getOneStavke(order_id);

            call.enqueue(new Callback<List<CartItem>>() {
                @Override
                public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                    cartItems = response.body();
                    listView();
                }

                @Override
                public void onFailure(Call<List<CartItem>> call, Throwable t) {

                }
            });
        }
    }

    public void listView() {
        mListView = (ListView) findViewById(R.id.listview_cart_items);
        mListView.setAdapter(new CartItemListAdapter(this, R.layout.layout_cart_item, (ArrayList<CartItem>) cartItems));
    }
}
