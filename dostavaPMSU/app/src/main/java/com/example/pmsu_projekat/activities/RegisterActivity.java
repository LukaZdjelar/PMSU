package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.CustomerRegister;
import com.example.pmsu_projekat.model.SellerRegister;
import com.example.pmsu_projekat.service.CustomerServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinnerRole;
    SellerRegister seller;
    CustomerRegister customer;
    static Retrofit retrofit = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        spinner();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerRole.getSelectedItemPosition() == 0){
                    registerBuyer();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(RegisterActivity.this, RegisterSellerActivity.class);
                    sellerData();
                    intent.putExtra("seller", seller);
                    startActivity(intent);
                }
            }
        });
    }

    private void sellerData(){
        TextInputLayout tiName = findViewById(R.id.firstname_register);
        TextInputLayout tiLastName = findViewById(R.id.lastname_register);
        TextInputLayout tiUsername = findViewById(R.id.username_register);
        TextInputLayout tiPassword = findViewById(R.id.password_register);
        TextInputLayout tiAddress = findViewById(R.id.adress_register);

        seller = new SellerRegister(tiName.getEditText().getText().toString(),
                tiLastName.getEditText().getText().toString(), tiUsername.getEditText().getText().toString(),
                tiPassword.getEditText().getText().toString(), tiAddress.getEditText().getText().toString());
    }

    private void registerBuyer(){
        TextInputLayout tiName = findViewById(R.id.firstname_register);
        TextInputLayout tiLastName = findViewById(R.id.lastname_register);
        TextInputLayout tiUsername = findViewById(R.id.username_register);
        TextInputLayout tiPassword = findViewById(R.id.password_register);
        TextInputLayout tiAddress = findViewById(R.id.adress_register);

        customer = new CustomerRegister(tiName.getEditText().getText().toString(),
                tiLastName.getEditText().getText().toString(), tiUsername.getEditText().getText().toString(),
                tiPassword.getEditText().getText().toString(), tiAddress.getEditText().getText().toString());

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        CustomerServiceAPI customerServiceAPI = retrofit.create(CustomerServiceAPI.class);
        Call<CustomerRegister> call = customerServiceAPI.register(customer);

        call.enqueue(new Callback<CustomerRegister>() {
            @Override
            public void onResponse(Call<CustomerRegister> call, Response<CustomerRegister> response) {
                Toast t = Toast.makeText(getApplicationContext(), "Uspesna registracija", Toast.LENGTH_SHORT);
                t.show();
            }

            @Override
            public void onFailure(Call<CustomerRegister> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    private void spinner(){
        spinnerRole = (Spinner) findViewById(R.id.spinnerRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerRoles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }
}
