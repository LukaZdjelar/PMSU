package com.example.pmsu_projekat.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.ArticleListAdapter;
import com.example.pmsu_projekat.adapters.RestaurantListAdapter;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesActivity extends AppCompatActivity {
    static final String TAG = ArticlesActivity.class.getSimpleName();
    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    static final String BASE_URL = "http://192.168.0.13:8080/";
    List<Article> articles = new ArrayList<>();
    List<Article> articlesTest = new ArrayList<>();
    private ListView mListView;
    private long seller_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Bundle b = getIntent().getExtras();
        if(b != null)
            seller_id = b.getLong("id");

        mListView = (ListView) findViewById(R.id.listview_articles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_articles);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_articles);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_articles);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(ArticlesActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getArticles();
    }

    private void getArticles(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ArticleServiceAPI articleServiceAPI = retrofit.create(ArticleServiceAPI.class);
        Call<List<Article>> call = articleServiceAPI.getAll(seller_id);

        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {

                //Article a1 = new Article(1L,"Dupla punjena", 350.0, "Opis 1",1L);
                //Article a2 = new Article(2L,"Index", 250.0, "Opis 2",1L);
                //Article a3 = new Article(3L,"Krilca", 320.0, "Opis 3", 2L);

                //articlesTest.add(a1);
                //articlesTest.add(a2);
                //articlesTest.add(a3);

                Log.d("DataCheck",new Gson().toJson(response.body()));

                articles = response.body();

                mListView.setAdapter(new ArticleListAdapter(getApplicationContext(), R.layout.layout_article, (ArrayList<Article>) articles));
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e(TAG, t.toString());
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
