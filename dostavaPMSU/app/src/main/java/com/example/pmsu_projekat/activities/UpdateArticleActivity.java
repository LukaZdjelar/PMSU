package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
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

public class UpdateArticleActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    Article article;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);

        article = (Article) getIntent().getSerializableExtra("article");

        setValues();

        Button confirmButton = findViewById(R.id.article_info_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArticle();
            }
        });
    }

    private void updateArticle() {
        setArticleData();

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

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<Article> call = articleServiceAPI.update(article, article.getId());
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                startActivity();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

                Log.e("Error", t.toString());
            }
        });
    }

    private void setValues(){
        TextInputLayout tiName = findViewById(R.id.article_info_name);
        TextInputLayout tiPrice = findViewById(R.id.article_info_price);
        TextInputLayout tiDescription = findViewById(R.id.article_info_description);

        tiName.getEditText().setText(article.getNaziv());
        tiPrice.getEditText().setText(article.getCena().toString());
        tiDescription.getEditText().setText(article.getOpis());
    }

    private void setArticleData(){
        TextInputLayout tiName = findViewById(R.id.article_info_name);
        TextInputLayout tiPrice = findViewById(R.id.article_info_price);
        TextInputLayout tiDescription = findViewById(R.id.article_info_description);

        article.setNaziv(tiName.getEditText().getText().toString());
        article.setCena(Double.parseDouble(tiPrice.getEditText().getText().toString()));
        article.setOpis(tiDescription.getEditText().getText().toString());
    }

    private void startActivity(){
        Intent intent = new Intent(UpdateArticleActivity.this, ArticlesActivity.class);
        Bundle b = new Bundle();
        b.putLong("id", article.getProdavacId());
        intent.putExtras(b);
        startActivity(intent);
    }
}
