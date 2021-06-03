package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.model.SellerRegister;
import com.example.pmsu_projekat.service.SellerServiceAPI;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterSellerActivity extends AppCompatActivity {

    SellerRegister seller;
    static Retrofit retrofit = null;
    static final String BASE_URL = "http://192.168.0.13:8080/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_seller);

        seller = (SellerRegister) getIntent().getSerializableExtra("seller");

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerData();
                register();
                Log.d("DataCheck",new Gson().toJson(seller));
                Intent intent = new Intent(RegisterSellerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sellerData(){
        TextInputLayout tiEmail = findViewById(R.id.email_register);
        TextInputLayout tiName = findViewById(R.id.restaurant_name_register);

        seller.setEmail(tiEmail.getEditText().getText().toString());
        seller.setNaziv(tiName.getEditText().getText().toString());
    }

    private void register(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SellerServiceAPI sellerServiceAPI = retrofit.create(SellerServiceAPI.class);
        Call<SellerRegister> call = sellerServiceAPI.register(seller);

        call.enqueue(new Callback<SellerRegister>() {
            @Override
            public void onResponse(Call<SellerRegister> call, Response<SellerRegister> response) {

            }

            @Override
            public void onFailure(Call<SellerRegister> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }
}
