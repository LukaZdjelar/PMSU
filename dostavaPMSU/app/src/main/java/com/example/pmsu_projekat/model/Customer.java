package com.example.pmsu_projekat.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String adresa;
    private Boolean banovan;

    public Customer(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String adresa, Boolean banovan) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.adresa = adresa;
        this.banovan = banovan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Boolean getBanovan() {
        return banovan;
    }

    public void setBanovan(Boolean banovan) {
        this.banovan = banovan;
    }
}
