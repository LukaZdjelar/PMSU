package com.ftn.dostavaOSA.dto;

import com.ftn.dostavaOSA.model.Korisnik;

public class KorisnikDTO {

	private String korisnickoIme;
	private String lozinka;
	
	public KorisnikDTO() {
		
	}
	
	public KorisnikDTO(String korisnickoIme, String lozinka) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
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
