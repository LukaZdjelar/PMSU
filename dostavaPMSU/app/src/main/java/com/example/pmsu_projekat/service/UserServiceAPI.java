package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserServiceAPI {

    @POST("dostava/korisnik")
    Call<String> login(@Body User user);

    @GET("dostava/korisnik")
    Call<List<User>> getAll();

    @PUT("dostava/korisnik/blok/{id}")
    Call<User> block(@Path("id") Long id);
}
