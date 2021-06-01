package com.ftn.dostavaOSA.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "kupac")
public class Kupac extends Korisnik{

	@Column(name = "adresa", nullable = false)
	private String adresa;
	@Column(name = "banovan", nullable = false)
	private boolean banovan;
	
	public Kupac() {
		
	}

	public Kupac(Long id, String ime, String prezime, String korisnickoIme, String lozinka, EUloga uloga,
			String adresa, boolean banovan) {
		super(id, ime, prezime, korisnickoIme, lozinka, uloga);
		this.adresa = adresa;
		this.banovan = banovan;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public boolean isBanovan() {
		return banovan;
	}

	public void setBanovan(boolean banovan) {
		this.banovan = banovan;
	}

	@Override
	public String toString() {
		return "Kupac [adresa=" + adresa + ", getId()=" + getId() + ", getIme()=" + getIme() + ", getPrezime()="
				+ getPrezime() + ", getKorisnickoIme()=" + getKorisnickoIme() + ", getLozinka()=" + getLozinka()
				+ ", getUloga()=" + getUloga() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
	
}
