package com.example.pmsu_projekat.model;

import java.io.Serializable;

public class Article implements Serializable {
    private Long id;
    private String naziv;
    private Double cena;
    private String opis;
    private Long prodavacId;

    public Article(Long id, String naziv, Double cena, String opis, Long prodavacId) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
        this.prodavacId = prodavacId;
    }

    public Article(String naziv, Double cena, String opis, Long prodavacId) {
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
        this.prodavacId = prodavacId;
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

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getProdavacId() {
        return prodavacId;
    }

    public void setProdavacId(Long prodavacId) {
        this.prodavacId = prodavacId;
    }
}
