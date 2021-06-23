package com.example.pmsu_projekat.model;

public class User {

    private Long id;
    private String ime;
    private String prezime;
    private String uloga;
    private String korisnickoIme;
    private String lozinka;

    public User(Long id, String ime, String prezime, String uloga, String korisnickoIme, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.uloga = uloga;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public User() {

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

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
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
}
