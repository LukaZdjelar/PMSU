package com.example.pmsu_projekat.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
                Log.d("DataCheck",new Gson().toJson(response.body()));
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
        Log.d("ArticleData", article.getNaziv());
        Log.d("ArticleData", article.getOpis());
        Log.d("ArticleData", article.getCena().toString());

        TextView tvNaziv = findViewById(R.id.article_activity_name);
        TextView tvOpis = findViewById(R.id.article_activity_description);
        TextView tvCena = findViewById(R.id.article_activity_price);

        tvNaziv.setText(article.getNaziv());
        tvOpis.setText(article.getOpis());
        tvCena.setText(article.getCena().toString());
    }
}
