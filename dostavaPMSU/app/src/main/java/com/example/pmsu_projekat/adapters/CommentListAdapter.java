package com.example.pmsu_projekat.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.activities.LoginActivity;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.Order;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;

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

public class CommentListAdapter extends ArrayAdapter<Order> {

    Context mContext;
    int mResource;
    Retrofit retrofit;
    ArrayAdapter<Order> adapter;

    public CommentListAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        adapter = this;
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getKupac().getKorisnickoIme();
        String comment = getItem(position).getKomentar();
        Integer rating = getItem(position).getOcena();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvUsername = (TextView) convertView.findViewById(R.id.comment_layout_username);
        TextView tvComment = (TextView) convertView.findViewById(R.id.comment_layout_comment);
        TextView tvRating = (TextView) convertView.findViewById(R.id.comment_layout_rating);

        Button archiveButton = convertView.findViewById(R.id.button_archive_comment);

        SharedPreferences preferences = mContext.getSharedPreferences(LoginActivity.sharedPrefernces, Context.MODE_PRIVATE);
        JWT jwt = new JWT(preferences.getString(LoginActivity.token, ""));

        Claim claim = jwt.getClaim("role");
        String role = claim.asString();
        if (!role.equals("ROLE_PRODAVAC")){
            archiveButton.setVisibility(View.GONE);
        }

        if(getItem(position).isAnonimniKomentar()){
            tvUsername.setText("Anonimno");
        }else{
            tvUsername.setText(username);
        }
        tvComment.setText(comment);
        tvRating.setText(Integer.toString(rating));


        archiveButton.setOnClickListener(new View.OnClickListener() {
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
                Call<Order> call = orderServiceAPI.archive(getItem(position).getPorudzbinaId());

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        adapter.remove(getItem(position));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(mContext, "Komentar arhiviran", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;
    }
}
