package com.example.pmsu_projekat.model;

import java.time.LocalDate;

public class Seller {

    private Long id;
    private String naziv;
    private String adresa;
    private String email;
    private Boolean banovan;

    public Seller(Long id, String naziv, String adresa, String email, Boolean banovan) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.email = email;
        this.banovan = banovan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getBanovan() {
        return banovan;
    }

    public void setBanovan(Boolean banovan) {
        this.banovan = banovan;
    }
}
