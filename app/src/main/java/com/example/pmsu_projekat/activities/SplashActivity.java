package com.example.pmsu_projekat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.pmsu_projekat.MainActivity;
import com.example.pmsu_projekat.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
