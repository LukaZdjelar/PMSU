package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.OrderFilter;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class FilterCommentsActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    private long seller_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_filter);

        Bundle b = getIntent().getExtras();
        if (b != null)
            seller_id = b.getLong("seller_id");

        displayMetrics();
        filterButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void displayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height * 0.55));
        getWindow().setGravity(Gravity.BOTTOM);
    }

    private void filterButton(){
        Button button = findViewById(R.id.filterCommentsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderFilter filter = new OrderFilter(null, null, null,null,null, seller_id);

                TextInputLayout tiText = findViewById(R.id.comment_filter_text);
                String komentar = tiText.getEditText().getText().toString();
                if (!komentar.equals("")){
                    filter.setKomentar(komentar);
                }

                TextInputLayout tiRating1 = findViewById(R.id.comment_filter_rating1);
                String ocenaOd = tiRating1.getEditText().getText().toString();
                if (!ocenaOd.equals("")){
                    filter.setOcenaOd(Integer.parseInt(ocenaOd));
                }

                TextInputLayout tiRating2 = findViewById(R.id.comment_filter_rating2);
                String ocenaDo = tiRating2.getEditText().getText().toString();
                if (!ocenaDo.equals("")){
                    filter.setOcenaDo(Integer.parseInt(ocenaDo));
                }

                TextInputLayout tiPrice1 = findViewById(R.id.comment_filter_price1);
                String cenaOd = tiPrice1.getEditText().getText().toString();
                if (!cenaOd.equals("")){
                    filter.setCenaOd(Integer.parseInt(cenaOd));
                }

                TextInputLayout tiPrice2 = findViewById(R.id.comment_filter_price2);
                String cenaDo = tiPrice2.getEditText().getText().toString();
                if (!cenaDo.equals("")){
                    filter.setCenaDo(Integer.parseInt(cenaDo));
                }

                Intent intent = new Intent(FilterCommentsActivity.this, RestaurantActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id", seller_id);
                intent.putExtra("filter", filter);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
