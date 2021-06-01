package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleServiceAPI {

    @GET("dostava/artikal")
    Call<List<Article>> getAll(@Query("prodavacId") Long id);

    @GET("dostava/artikal/{id}")
    Call<Article> getOne(@Path("id") Long id);

    @POST("dostava/artikal")
    Call<Article> create(@Body Article article);
}
