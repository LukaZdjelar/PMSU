package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Article;
import com.example.pmsu_projekat.model.ArticleFilter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @PUT("dostava/artikal/{id}")
    Call<Article> update(@Body Article article, @Path("id") Long id);

    @DELETE("dostava/artikal/{id}")
    Call<Article> delete(@Path("id") Long id);

    @POST("dostava/artikal/filter")
    Call<List<Article>> filter(@Body ArticleFilter articleFilter);
}
