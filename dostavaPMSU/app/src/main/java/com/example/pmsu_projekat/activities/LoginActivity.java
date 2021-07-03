package com.example.pmsu_projekat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.MainActivity;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.User;
import com.example.pmsu_projekat.service.UserServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String sharedPrefernces = "sp";
    public static final String token = "token";
    Retrofit retrofit;
    User user = new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout tiUsername = findViewById(R.id.username_login);
                user.setKorisnickoIme(tiUsername.getEditText().getText().toString());

                TextInputLayout tiPassword = findViewById(R.id.password_login);
                user.setLozinka(tiPassword.getEditText().getText().toString());

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(LocalHost.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }

                UserServiceAPI userServiceAPI = retrofit.create(UserServiceAPI.class);
                Call<String> call = userServiceAPI.login(user);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        SharedPreferences sp = getSharedPreferences(sharedPrefernces, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        if (response.code() == 200){
                            editor.putString(token, response.body());
                            editor.apply();

                            JWT jwt = new JWT(sp.getString(token, ""));

                            Claim claimRole = jwt.getClaim("role");
                            String role = claimRole.asString();
                            Claim claimId = jwt.getClaim("id");
                            Long id = claimId.asLong();
                            if (role.equals("ROLE_PRODAVAC")){
                                Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
                                Bundle b = new Bundle();
                                b.putLong("seller_id", id);
                                intent.putExtras(b);
                                startActivity(intent);
                            }else if(role.equals("ROLE_KUPAC") || role.equals("ROLE_ADMINISTRATOR")){
                                Intent intent = new Intent(LoginActivity.this, RestaurantsActivity.class);
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Pogresni podaci", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("Login onFail", t.toString());
                    }
                });


            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
