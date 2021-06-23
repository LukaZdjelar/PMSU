package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.CartItem;
import com.example.pmsu_projekat.model.CartOrder;
import com.example.pmsu_projekat.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderServiceAPI {

    @GET("dostava/porudzbina/{id}")
    Call<Order> getOne(@Path("id") Long id);

    @POST("dostava/porudzbina/nova")
    Call<CartOrder> addNew(@Body CartOrder cartOrder);

    @POST("dostava/porudzbina/postoji")
    Call<Long> cartExists(@Body CartOrder cartOrder);

    @GET("dostava/porudzbina/stavke/{id}")
    Call<List<CartItem>> getOneStavke(@Path("id") Long id);

    @PUT("dostava/porudzbina/poruci")
    Call<Order> order(@Body Long id);

    @GET("dostava/porudzbina/recenzije/{id}")
    Call<List<Order>> toBeReviewed(@Path("id") Long id);

    @PUT("dostava/porudzbina/oceni/{id}")
    Call<Order> review(@Path("id") Long id, @Body Order order);

    @GET("dostava/porudzbina/komentari/{id}")
    Call<List<Order>> comments(@Path("id") Long id);

    @PUT("dostava/porudzbina/izbaci/{id}")
    Call<Long> remove(@Path("id") Long stavkaId);

    @PUT("dostava/porudzbina/kolicina/{id}")
    Call<CartItem> ammount(@Path("id") Long stavkaId, @Body Integer ammount);

    @PUT("dostava/porudzbina/arhiviraj/{id}")
    Call<Order> archive(@Path("id") Long id);
}
