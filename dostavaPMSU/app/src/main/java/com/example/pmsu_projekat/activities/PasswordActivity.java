package com.example.pmsu_projekat.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Password;
import com.example.pmsu_projekat.model.User;
import com.example.pmsu_projekat.service.UserServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    Long user_id;
    Password password = new Password();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Bundle b = getIntent().getExtras();
        user_id = b.getLong("user_id");
        Log.d("user_id", user_id.toString());

        Button button = findViewById(R.id.change_password_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword(){
        SharedPreferences preferences = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE  );
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

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        passwordData();
        UserServiceAPI userServiceAPI = retrofit.create(UserServiceAPI.class);
        Call<User> call = userServiceAPI.changePassword(user_id, password);
        Log.d("DataCheck",new Gson().toJson(password));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                PasswordActivity.this.finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                PasswordActivity.this.finish();
            }
        });
    }

    private void passwordData(){
        TextInputLayout tiOldPassword  = findViewById(R.id.password_old);
        TextInputLayout tiNewPassword  = findViewById(R.id.password_new);

        password.setStaraLozinka(tiOldPassword.getEditText().getText().toString());
        password.setNovaLozinka(tiNewPassword.getEditText().getText().toString());
    }
}
