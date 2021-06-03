package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.CustomerRegister;
import com.example.pmsu_projekat.model.Seller;
import com.example.pmsu_projekat.model.SellerRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerServiceAPI {

    @POST("dostava/kupac/registracija")
    Call<CustomerRegister> register(@Body CustomerRegister customer);
}
