package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.model.SellerRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SellerServiceAPI {

    @GET("dostava/prodavac")
    Call<List<Seller>> getAll();

    @GET("dostava/prodavac/{id}")
    Call<Seller> get(@Path("id") Long id);

    @POST("dostava/prodavac/registracija")
    Call<SellerRegister> register(@Body SellerRegister seller);

    @GET("dostava/prodavac/prosek/{id}")
    Call<Double> average(@Path("id") Long id);
}
