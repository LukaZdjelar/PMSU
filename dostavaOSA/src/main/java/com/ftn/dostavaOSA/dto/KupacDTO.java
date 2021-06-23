package com.ftn.dostavaOSA.dto;

import javax.persistence.Column;

import com.ftn.dostavaOSA.model.Kupac;

public class KupacDTO {

	private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
	private String adresa;
	private Boolean banovan;
	
	public KupacDTO() {
		
	}
	
	public KupacDTO(Kupac kupac) {
		id = kupac.getId();
		ime = kupac.getIme();
		prezime = kupac.getPrezime();
		korisnickoIme = kupac.getKorisnickoIme();
		lozinka = kupac.getLozinka();
		adresa = kupac.getAdresa();
		banovan = kupac.isBanovan();
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

	@Override
	public String toString() {
		return "KupacDTO [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", korisnickoIme=" + korisnickoIme
				+ ", lozinka=" + lozinka + ", adresa=" + adresa + ", banovan=" + banovan + "]";
	}
}
