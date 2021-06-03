package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateArticleActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    static final String BASE_URL = "http://192.168.0.13:8080/";
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
                Intent intent = new Intent(UpdateArticleActivity.this, ArticleActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", article.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void updateArticle() {
        setArticleData();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<Article> call = articleServiceAPI.update(article, article.getId());
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
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
}
