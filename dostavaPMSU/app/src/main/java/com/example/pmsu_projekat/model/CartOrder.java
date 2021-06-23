package com.example.pmsu_projekat.model;

import java.io.Serializable;

public class CartOrder implements Serializable {
    private Long porudzbinaId;
    private Long kupacId;
    private Long prodavacId;
    private Long artikalId;
    private int kolicina;

    public CartOrder(Long porudzbinaId, Long kupacId, Long prodavacId, Long artikalId, int kolicina) {
        this.porudzbinaId = porudzbinaId;
        this.kupacId = kupacId;
        this.prodavacId = prodavacId;
        this.artikalId = artikalId;
        this.kolicina = kolicina;
    }

    public CartOrder() {

    }

    public Long getPorudzbinaId() {
        return porudzbinaId;
    }

    public void setPorudzbinaId(Long porudzbinaId) {
        this.porudzbinaId = porudzbinaId;
    }

    public Long getKupacId() {
        return kupacId;
    }

    public void setKupacId(Long kupacId) {
        this.kupacId = kupacId;
    }

    public Long getProdavacId() {
        return prodavacId;
    }

    public void setProdavacId(Long prodavacId) {
        this.prodavacId = prodavacId;
    }

    public Long getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Long artikalId) {
        this.artikalId = artikalId;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
