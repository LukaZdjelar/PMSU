package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
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

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.CartOrder;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.navigation.NavigationView;
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

public class ArticleActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    private long article_id;
    private Article article = null;
    CartOrder cartOrder = new CartOrder();
    Long seller_id;
    Long customer_id;
    Long order_id;
    String role;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Bundle b = getIntent().getExtras();
        if(b != null){
            article_id = b.getLong("id");
            seller_id = b.getLong("seller_id");
            customer_id = b.getLong("customer_id");
            order_id = b.getLong("order_id");
        }

        Button articleEditButton = findViewById(R.id.articleEditButton);
        articleEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, UpdateArticleActivity.class);
                intent.putExtra("article", article);
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

        Button addToCartButton = findViewById((R.id.buttonAddToCart));
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartOrder.setArtikalId(article.getId());

                TextInputLayout tiAmmount = findViewById(R.id.order_cart_ammount);
                int ammount = Integer.parseInt(tiAmmount.getEditText().getText().toString());
                cartOrder.setKolicina(ammount);

                cartOrder.setProdavacId(seller_id);

                cartOrder.setKupacId(customer_id);

                if (order_id > 0){
                    cartOrder.setPorudzbinaId(order_id);
                }
                addNew();
            }
        });

        SharedPreferences sp = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);

        JWT jwt = new JWT(sp.getString(LoginActivity.token, ""));
        Claim claimRole = jwt.getClaim("role");
        role = claimRole.asString();
        Claim claimId = jwt.getClaim("id");
        Long id = claimId.asLong();
        if (!role.equals("ROLE_PRODAVAC")){
            articleEditButton.setVisibility(View.GONE);
            articleDeleteButton.setVisibility(View.GONE);
        }
        if (!role.equals("ROLE_KUPAC")){
            addToCartButton.setVisibility(View.GONE);
            TextInputLayout tiAmmount = findViewById(R.id.order_cart_ammount);
            tiAmmount.setVisibility(View.GONE);
        }

        toolbarAndDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getArticle();
    }

    private void getArticle(){

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

        retrofit = new Retrofit.Builder()
                .baseUrl(LocalHost.BASE_URL)
                .client(client)
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
        Menu menu =navigationView.getMenu();
        if (role.equals("ROLE_PRODAVAC")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.prodavnice_menu);
            menu.removeItem(R.id.korisnici_menu);
        }
        if (role.equals("ROLE_KUPAC")){
            menu.removeItem(R.id.kreiraj_artikal_menu);
            menu.removeItem(R.id.korisnici_menu);
        }
        if (role.equals("ROLE_ADMINISTRATOR")){
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.kreiraj_artikal_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Kreiraj artikal")){
                    Intent intent = new Intent(ArticleActivity.this, CreateArticleActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("seller_id",seller_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Prodavnice")){
                    Intent intent = new Intent(ArticleActivity.this, RestaurantsActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Recenzije")){
                    Intent intent = new Intent(ArticleActivity.this, OrdersActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("customer_id", customer_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Korisnici")){
                    Intent intent = new Intent(ArticleActivity.this, UsersActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Odjavi se")){
                    Intent intent = new Intent(ArticleActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void addNew(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);
        Call<CartOrder> call = orderServiceAPI.addNew(cartOrder);
        call.enqueue(new Callback<CartOrder>() {
            @Override
            public void onResponse(Call<CartOrder> call, Response<CartOrder> response) {
                if (response.code() == 204){
                    Toast.makeText(ArticleActivity.this, "Vec imate ovaj artikal u korpi", Toast.LENGTH_SHORT).show();
                }else{
                    Toast t = Toast.makeText(getApplicationContext(), "Uspesno dodato u korpu", Toast.LENGTH_SHORT);
                    t.show();
                }
            }

            @Override
            public void onFailure(Call<CartOrder> call, Throwable t) {

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
