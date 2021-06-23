package com.example.pmsu_projekat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.activities.CartActivity;
import com.example.pmsu_projekat.activities.LoginActivity;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.Order;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;

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

public class CartItemListAdapter extends ArrayAdapter<CartItem> {
    private Context mContext;
    int mResource;
    Retrofit retrofit;
    Integer ammount;
    Double price;
    Double totalPrice;
    ListView mListview;
    ArrayAdapter<CartItem> adapter;


    public CartItemListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CartItem> objects) {
        super(context, resource, objects);
        adapter = this;
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        mListview = convertView.findViewById(R.id.listview_cart_items);

        String name = getItem(position).getArtikal().getNaziv();
        price = getItem(position).getArtikal().getCena();
        ammount = getItem(position).getKolicina();
        totalPrice = price * ammount;

        TextView tvName = (TextView) convertView.findViewById(R.id.cart_item_name);
        TextView tvAmmount = (TextView) convertView.findViewById(R.id.cart_item_ammount);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.cart_item_price);

        tvName.setText(name);
        tvPrice.setText(totalPrice.toString());
        tvAmmount.setText(ammount.toString());

        Button plusButton = convertView.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ammount = getItem(position).getKolicina() + 1;
                totalPrice = totalPrice + price;
                tvAmmount.setText(ammount.toString());
                tvPrice.setText(totalPrice.toString());
                getItem(position).setKolicina(ammount);

                SharedPreferences preferences = mContext.getSharedPreferences(LoginActivity.sharedPrefernces, Context.MODE_PRIVATE);
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
                Call<CartItem> call = orderServiceAPI.ammount(getItem(position).getId(), ammount);

                call.enqueue(new Callback<CartItem>() {
                    @Override
                    public void onResponse(Call<CartItem> call, Response<CartItem> response) {

                    }

                    @Override
                    public void onFailure(Call<CartItem> call, Throwable t) {

                    }
                });
            }
        });

        Button minusButton = convertView.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getKolicina() == 1){
                    Toast t = Toast.makeText(mContext, "Imate samo jedan artikal", Toast.LENGTH_SHORT);
                    t.show();
                }else{
                    ammount = getItem(position).getKolicina() - 1;
                    totalPrice = totalPrice - price;
                    tvAmmount.setText(ammount.toString());
                    tvPrice.setText(totalPrice.toString());
                    getItem(position).setKolicina(ammount);

                    SharedPreferences preferences = mContext.getSharedPreferences(LoginActivity.sharedPrefernces, Context.MODE_PRIVATE);
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
                    Call<CartItem> call = orderServiceAPI.ammount(getItem(position).getId(), ammount);

                    call.enqueue(new Callback<CartItem>() {
                        @Override
                        public void onResponse(Call<CartItem> call, Response<CartItem> response) {

                        }

                        @Override
                        public void onFailure(Call<CartItem> call, Throwable t) {

                        }
                    });
                }
            }
        });

        Button removeButton = convertView.findViewById(R.id.remove_item_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = mContext.getSharedPreferences(LoginActivity.sharedPrefernces, Context.MODE_PRIVATE);
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
                Call<Long> call = orderServiceAPI.remove(getItem(position).getId());

                call.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        adapter.remove(getItem(position));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(mContext, "Izbaceno iz korpe", Toast.LENGTH_SHORT).show();
                        if (adapter.getCount() == 0){
                            ((Activity) mContext).finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e("Izbaci onfail", t.toString());
                    }
                });
            }
        });

        return convertView;
    }
}
