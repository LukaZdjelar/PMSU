package com.example.pmsu_projekat.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Order implements Serializable {
    private Long porudzbinaId;
    private Integer ocena;
    private List<CartItem> stavke;
    private String komentar;
    private boolean anonimniKomentar;
    private boolean arhiviranikomentar;
    private Double cena;
    private String datum;

    private Customer kupac;
    private Seller prodavac;

    public Order(Long porudzbinaId, Integer ocena, List<CartItem> stavke, String komentar, boolean anonimniKomentar, boolean arhiviranikomentar, Customer kupac, Seller prodavac, Double cena, String datum) {
        this.porudzbinaId = porudzbinaId;
        this.ocena = ocena;
        this.stavke = stavke;
        this.komentar = komentar;
        this.anonimniKomentar = anonimniKomentar;
        this.arhiviranikomentar = arhiviranikomentar;
        this.kupac = kupac;
        this.prodavac = prodavac;
        this.cena = cena;
        this.datum = datum;

    }

    public Long getPorudzbinaId() {
        return porudzbinaId;
    }

    public void setPorudzbinaId(Long porudzbinaId) {
        this.porudzbinaId = porudzbinaId;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public List<CartItem> getStavke() {
        return stavke;
    }

    public void setStavke(List<CartItem> stavke) {
        this.stavke = stavke;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public boolean isAnonimniKomentar() {
        return anonimniKomentar;
    }

    public void setAnonimniKomentar(boolean anonimniKomentar) {
        this.anonimniKomentar = anonimniKomentar;
    }

    public boolean isArhiviranikomentar() {
        return arhiviranikomentar;
    }

    public void setArhiviranikomentar(boolean arhiviranikomentar) {
        this.arhiviranikomentar = arhiviranikomentar;
    }

    public Customer getKupac() {
        return kupac;
    }

    public void setKupac(Customer kupac) {
        this.kupac = kupac;
    }

    public Seller getProdavac() {
        return prodavac;
    }

    public void setProdavac(Seller prodavac) {
        this.prodavac = prodavac;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
