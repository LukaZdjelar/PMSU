package com.example.pmsu_projekat.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {
    @SerializedName("id")
    private Integer id;
    @SerializedName("naziv")
    private String naziv;

    public Restaurant(Integer id, String naziv){
        this.id=id;
        this.naziv=naziv;
    }

    public Integer getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }
}
