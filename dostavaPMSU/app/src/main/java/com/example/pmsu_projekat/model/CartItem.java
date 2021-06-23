package com.example.pmsu_projekat.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Long id;
    private Article artikal;
//    private Long artikalId;
    //private Long porudzbinaId;
    private int kolicina;

    public CartItem(Long id, Article artikal,  int kolicina) {
        this.id =id;
        this.artikal = artikal;
        this.kolicina = kolicina;
    }

//    public CartItem(Article artikal, Long porudzbinaId, int kolicina) {
//        this.artikal = artikal;
//        this.porudzbinaId = porudzbinaId;
//        this.kolicina = kolicina;
//    }

//    public CartItem(Long artikalId, Long porudzbinaId, int kolicina) {
//        this.artikalId = artikalId;
//        this.porudzbinaId = porudzbinaId;
//        this.kolicina = kolicina;
//    }

    public Article getArtikal() {
        return artikal;
    }

    public void setArtikal(Article artikal) {
        this.artikal = artikal;
    }

//    public Long getArtikalId() {
//        return artikalId;
//    }
//
//    public void setArtikalId(Long artikalId) {
//        this.artikalId = artikalId;
//    }

//    public Long getPorudzbinaId() {
//        return porudzbinaId;
//    }
//
//    public void setPorudzbinaId(Long porudzbinaId) {
//        this.porudzbinaId = porudzbinaId;
//    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
