package com.example.pmsu_projekat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.adapters.ArticleListAdapter;
import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.ArticleFilter;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.CartOrder;
import com.example.pmsu_projekat.service.ArticleServiceAPI;
import com.example.pmsu_projekat.service.OrderServiceAPI;
import com.example.pmsu_projekat.tools.LocalHost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesActivity extends AppCompatActivity {
    static final String TAG = ArticlesActivity.class.getSimpleName();
    List<CartItem> cartItems;
    DrawerLayout mDrawerLayout;
    static Retrofit retrofit = null;
    List<Article> articles = new ArrayList<>();
    private ListView mListView;
    private long seller_id;
    Long customer_id;
    CartOrder cartOrder = new CartOrder();
    Long order_id;
    String role;
    Long claim_id;
    Long user_id;
    ArticleFilter filter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Bundle b = getIntent().getExtras();
        if (b != null)
            seller_id = b.getLong("seller_id");

        filter = (ArticleFilter)getIntent().getSerializableExtra("filter");

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_button_cart);

        SharedPreferences sp = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);

        JWT jwt = new JWT(sp.getString(LoginActivity.token, ""));
        Claim claimRole = jwt.getClaim("role");
        role = claimRole.asString();
        Claim claimId = jwt.getClaim("id");
        claim_id = claimId.asLong();
        if (!role.equals("ROLE_KUPAC")) {
            floatingActionButton.setVisibility(View.GONE);
        }
        if (role.equals("ROLE_KUPAC")) {
            customer_id = claim_id;
        }
        if (role.equals("ROLE_PRODAVAC")) {
            seller_id = claim_id;
        }
        toolbarAndDrawer();
        listView();
    }

    @Override
    public void onResume() {
        super.onResume();
        pendingCart();
        getArticles();
        fab();
        fab2();
    }

    private void pendingCart() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LocalHost.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        cartOrder.setKupacId(customer_id);
        cartOrder.setProdavacId(seller_id);

        OrderServiceAPI orderServiceAPI = retrofit.create(OrderServiceAPI.class);
        Call<Long> call = orderServiceAPI.cartExists(cartOrder);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                order_id = response.body();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("Order id fail", t.toString());
            }
        });
    }

    private void getArticles() {
        SharedPreferences preferences = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);
        String token = preferences.getString(LoginActivity.token, "");

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
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

        if (filter == null){
            Call<List<Article>> call = articleServiceAPI.getAll(seller_id);

            call.enqueue(new Callback<List<Article>>() {
                @Override
                public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                    articles = response.body();
                    mListView.setAdapter(new ArticleListAdapter(getApplicationContext(), R.layout.layout_article, (ArrayList<Article>) articles));
                }

                @Override
                public void onFailure(Call<List<Article>> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }else{
            Call<List<Article>> call = articleServiceAPI.filter(filter);
            call.enqueue(new Callback<List<Article>>() {
                @Override
                public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                    articles = response.body();
                    mListView.setAdapter(new ArticleListAdapter(getApplicationContext(), R.layout.layout_article, (ArrayList<Article>) articles));
                }

                @Override
                public void onFailure(Call<List<Article>> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        }
    }

    private void listView() {
        mListView = (ListView) findViewById(R.id.listview_articles);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArticlesActivity.this, ArticleActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", articles.get(position).getId());
                b.putLong("seller_id", seller_id);
                if (role.equals("ROLE_KUPAC")) {
                    b.putLong("customer_id", customer_id);
                }
                if (order_id > 0) {
                    b.putLong("order_id", order_id);
                }
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void toolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_articles);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_articles);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_articles);
        Menu menu = navigationView.getMenu();
        if (role.equals("ROLE_PRODAVAC")) {
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.prodavnice_menu);
            menu.removeItem(R.id.korisnici_menu);
            user_id = seller_id;
            Log.d("user_id articles", user_id.toString());
        }
        if (role.equals("ROLE_KUPAC")) {
            menu.removeItem(R.id.kreiraj_artikal_menu);
            menu.removeItem(R.id.korisnici_menu);
            user_id = customer_id;
        }
        if (role.equals("ROLE_ADMINISTRATOR")) {
            menu.removeItem(R.id.recenzije_menu);
            menu.removeItem(R.id.kreiraj_artikal_menu);
            menu.removeItem(R.id.loznika_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Kreiraj artikal")) {
                    Intent intent = new Intent(ArticlesActivity.this, CreateArticleActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("seller_id", seller_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Prodavnice")) {
                    Intent intent = new Intent(ArticlesActivity.this, RestaurantsActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Recenzije")) {
                    Intent intent = new Intent(ArticlesActivity.this, OrdersActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("customer_id", customer_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Korisnici")) {
                    Intent intent = new Intent(ArticlesActivity.this, UsersActivity.class);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Promeni lozinku")) {
                    Intent intent = new Intent(ArticlesActivity.this, PasswordActivity.class);
                    Bundle b = new Bundle();
                    b.putLong("user_id", user_id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                if (menuItem.getTitle().equals("Odjavi se")) {
                    SharedPreferences sp = getSharedPreferences(LoginActivity.sharedPrefernces, MODE_PRIVATE);
                    sp.edit().remove("token").apply();
                    Intent intent = new Intent(ArticlesActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void fab() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_button_cart);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticlesActivity.this, CartActivity.class);
                if (order_id > 0) {
                    Bundle b = new Bundle();
                    b.putLong("order_id", order_id);
                    intent.putExtras(b);
                }
                startActivity(intent);
            }
        });


    }

    private void fab2() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_button_article_filter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticlesActivity.this, FilterArticleActivity.class);
                Bundle b = new Bundle();
                b.putLong("seller_id", seller_id);
                intent.putExtras(b);
                startActivity(intent);
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
