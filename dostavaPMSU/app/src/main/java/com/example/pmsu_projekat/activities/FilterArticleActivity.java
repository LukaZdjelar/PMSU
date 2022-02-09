package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.ArticleFilter;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputLayout;

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

public class FilterArticleActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    private long seller_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_filter);

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

    private void filterButton() {
        Button button = findViewById(R.id.filterArticlesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleFilter filter = new ArticleFilter(null,null,null,null, null,null,null,null, seller_id);

                TextInputLayout tiName = findViewById(R.id.article_filter_name);
                String naziv = tiName.getEditText().getText().toString();
                if (!naziv.equals("")){
                    filter.setNaziv(naziv);
                }

                TextInputLayout tiDescription = findViewById(R.id.article_filter_description);
                String opis = tiDescription.getEditText().getText().toString();
                if (!opis.equals("")){
                    filter.setOpis(opis);
                }

                TextInputLayout tiPrice1 = findViewById(R.id.article_filter_price1);
                String cenaOd = tiPrice1.getEditText().getText().toString();
                if (!cenaOd.equals("")){
                    filter.setCenaOd(Integer.parseInt(cenaOd));
                }

                TextInputLayout tiPrice2 = findViewById(R.id.article_filter_price2);
                String cenaDo = tiPrice2.getEditText().getText().toString();
                if (!cenaDo.equals("")){
                    filter.setCenaDo(Integer.parseInt(cenaDo));
                }

                TextInputLayout tiRating1 = findViewById(R.id.article_filter_rating1);
                String ocenaOd = tiRating1.getEditText().getText().toString();
                if (!ocenaOd.equals("")){
                    filter.setOcenaOd(Integer.parseInt(ocenaOd));
                }

                TextInputLayout tiRating2 = findViewById(R.id.article_filter_rating2);
                String ocenaDo = tiRating2.getEditText().getText().toString();
                if (!ocenaDo.equals("")){
                    filter.setOcenaDo(Integer.parseInt(ocenaDo));
                }

                TextInputLayout tiNumberOfRating1 = findViewById(R.id.article_filter_number_of_ratings1);
                String brojOcenaOd = tiNumberOfRating1.getEditText().getText().toString();
                if (!brojOcenaOd.equals("")){
                    filter.setBrojOcenaOd(Integer.parseInt(brojOcenaOd));
                }

                TextInputLayout tiNumberOfRating2 = findViewById(R.id.article_filter_number_of_ratings2);
                String brojOcenaDo = tiNumberOfRating2.getEditText().getText().toString();
                if (!brojOcenaDo.equals("")){
                    filter.setBrojOcenaDo(Integer.parseInt(brojOcenaDo));
                }

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

                Intent intent = new Intent(FilterArticleActivity.this, ArticlesActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id", seller_id);
                intent.putExtra("filter", filter);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}