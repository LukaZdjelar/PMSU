package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.MainActivity;
import com.example.pmsu_projekat.R;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinnerRole;

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
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(RegisterActivity.this, RegisterSellerActivity.class);
                    startActivity(intent);
                }
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
