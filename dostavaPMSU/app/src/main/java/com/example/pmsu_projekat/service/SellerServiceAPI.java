package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Seller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SellerServiceAPI {

    @GET("dostava/prodavac")
    Call<List<Seller>> getAll();

    @GET("dostava/prodavac/prodavnice")
    Call<List<String>> getAllRestaurant();
}
