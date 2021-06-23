package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

public class CreateArticleActivity extends AppCompatActivity {

    static Retrofit retrofit = null;
    Article article;
    Long seller_id;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);

        Bundle b = getIntent().getExtras();
        if(b != null)
            seller_id = b.getLong("seller_id");

        Button confirmButton = findViewById(R.id.article_info_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createArticle();
            }
        });
    }

    private void createArticle(){
        TextInputLayout tiName = findViewById(R.id.article_info_name);
        TextInputLayout tiPrice = findViewById(R.id.article_info_price);
        TextInputLayout tiDescription = findViewById(R.id.article_info_description);

        article = new Article(tiName.getEditText().getText().toString(), Double.parseDouble(tiPrice.getEditText().getText().toString()),
                            tiDescription.getEditText().getText().toString(), seller_id);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<Article> call = articleServiceAPI.create(article);

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

    private void startActivity(){
        Intent intent = new Intent(CreateArticleActivity.this, ArticlesActivity.class);
        Bundle b =new Bundle();
        b.putLong("id", seller_id);
        intent.putExtras(b);
        startActivity(intent);
    }
}
