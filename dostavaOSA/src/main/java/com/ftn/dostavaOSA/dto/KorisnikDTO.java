package com.ftn.dostavaOSA.dto;

import com.ftn.dostavaOSA.model.Korisnik;

public class KorisnikDTO {

	private Long id;
	private String ime;
	private String prezime;
	private String uloga;
	private String korisnickoIme;
	private String lozinka;
	
	public KorisnikDTO() {
		
	}
	
	public KorisnikDTO(String korisnickoIme, String lozinka) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}
	
	public KorisnikDTO(Korisnik korisnik) {
		ime = korisnik.getIme();
		prezime = korisnik.getPrezime();
		uloga = korisnik.getUloga().toString();
		korisnickoIme = korisnik.getKorisnickoIme();
		lozinka = korisnik.getLozinka();
		id = korisnik.getId();
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

	@Override
	public String toString() {
		return "KorisnikDTO [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", uloga=" + uloga
				+ ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + "]";
	}
	
	
}
