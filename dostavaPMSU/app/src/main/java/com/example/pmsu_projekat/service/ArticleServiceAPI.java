package com.example.pmsu_projekat.service;

import com.example.pmsu_projekat.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleServiceAPI {

    @GET("dostava/artikal")
    Call<List<Article>> getAll(@Query("id") Long id);

}
