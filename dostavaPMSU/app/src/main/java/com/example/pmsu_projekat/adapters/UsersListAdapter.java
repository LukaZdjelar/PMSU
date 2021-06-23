package com.example.pmsu_projekat.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.activities.LoginActivity;
import com.example.pmsu_projekat.model.User;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.service.UserServiceAPI;
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

public class UsersListAdapter extends ArrayAdapter<User> {

    Retrofit retrofit;
    private Context mContext;
    int mResource;

    public UsersListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String firstname = getItem(position).getIme();
        String lastname = getItem(position).getPrezime();
        String username = getItem(position).getKorisnickoIme();
        String role = getItem(position).getUloga();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvFirstname = convertView.findViewById(R.id.user_firstname);
        TextView tvLastname = convertView.findViewById(R.id.user_lastname);
        TextView tvUsername = convertView.findViewById(R.id.user_username);
        TextView tvRole = convertView.findViewById(R.id.user_role);

        tvFirstname.setText(firstname);
        tvLastname.setText(lastname);
        tvUsername.setText(username);
        tvRole.setText(role);

        Button blockButton = convertView.findViewById(R.id.button_block);
        blockButton.setOnClickListener(new View.OnClickListener() {
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

                UserServiceAPI userServiceAPI = retrofit.create(UserServiceAPI.class);
                Call<User> call = userServiceAPI.block(getItem(position).getId());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(mContext, "Korisnik blokiran", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;
    }
}
