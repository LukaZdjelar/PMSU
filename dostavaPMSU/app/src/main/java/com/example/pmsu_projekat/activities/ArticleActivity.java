package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    static final String BASE_URL = "http://192.168.0.13:8080/";
    private long article_id;
    private Article article = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Bundle b = getIntent().getExtras();
        if(b != null)
            article_id = b.getLong("id");

        Button articleEditButton = findViewById(R.id.articleEditButton);
        articleEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, UpdateArticleActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", article.getId());
                b.putString("name", article.getNaziv());
                b.putString("description", article.getOpis());
                b.putDouble("price", article.getCena());
                b.putLong("seller_id", article.getProdavacId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Button articleDeleteButton = findViewById(R.id.articleDeleteButton);
        articleDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        toolbarAndDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getArticle();
    }

    private void getArticle(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<Article> call = articleServiceAPI.getOne(article_id);

        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                article = response.body();
                articleData(article);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    private void articleData(Article article){
        TextView tvNaziv = findViewById(R.id.article_activity_name);
        TextView tvOpis = findViewById(R.id.article_activity_description);
        TextView tvCena = findViewById(R.id.article_activity_price);

        tvNaziv.setText(article.getNaziv());
        tvOpis.setText(article.getOpis());
        tvCena.setText(article.getCena().toString());
    }

    private void delete(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<Article> call = articleServiceAPI.delete(article_id);

        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Intent intent = new Intent(ArticleActivity.this, ArticlesActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", article.getProdavacId());
                intent.putExtras(b);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    private void toolbarAndDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_article);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_article);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                Toast.makeText(ArticleActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
