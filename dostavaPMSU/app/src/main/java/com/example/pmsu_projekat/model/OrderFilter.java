package com.example.pmsu_projekat.model;

import java.io.Serializable;

public class OrderFilter implements Serializable {

    private String komentar;
    private Integer ocenaOd;
    private Integer ocenaDo;
    private Integer cenaOd;
    private Integer cenaDo;
    private Long prodavacId;

    public OrderFilter(){

    }

    public OrderFilter(String komentar, Integer ocenaOd, Integer ocenaDo, Integer cenaOd, Integer cenaDo, Long prodavacId) {
        this.komentar = komentar;
        this.ocenaOd = ocenaOd;
        this.ocenaDo = ocenaDo;
        this.cenaOd = cenaOd;
        this.cenaDo = cenaDo;
        this.prodavacId = prodavacId;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getOcenaOd() {
        return ocenaOd;
    }

    public void setOcenaOd(Integer ocenaOd) {
        this.ocenaOd = ocenaOd;
    }

    public Integer getOcenaDo() {
        return ocenaDo;
    }

    public void setOcenaDo(Integer ocenaDo) {
        this.ocenaDo = ocenaDo;
    }

    public Integer getCenaOd() {
        return cenaOd;
    }

    public void setCenaOd(Integer cenaOd) {
        this.cenaOd = cenaOd;
    }

    public Integer getCenaDo() {
        return cenaDo;
    }

    public void setCenaDo(Integer cenaDo) {
        this.cenaDo = cenaDo;
    }

    public Long getProdavacId() {
        return prodavacId;
    }

    public void setProdavacId(Long prodavacId) {
        this.prodavacId = prodavacId;
    }
}
