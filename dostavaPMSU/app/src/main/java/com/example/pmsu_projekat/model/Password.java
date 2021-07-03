package com.example.pmsu_projekat.model;

public class Password {

    private String staraLozinka;
    private String novaLozinka;

    public Password() {

    }

    public Password(String staraLozinka, String novaLozinka) {
        super();
        this.staraLozinka = staraLozinka;
        this.novaLozinka = novaLozinka;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }
}
