package com.ftn.dostavaOSA.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
public class Administrator extends Korisnik {

	public Administrator() {
		
	}

	public Administrator(Long id, String ime, String prezime, String korisnickoIme, String lozinka, EUloga uloga) {
		super(id, ime, prezime, korisnickoIme, lozinka, uloga);
	}

	@Override
	public String toString() {
		return "Administrator [getId()=" + getId() + ", getIme()=" + getIme() + ", getPrezime()=" + getPrezime()
				+ ", getKorisnickoIme()=" + getKorisnickoIme() + ", getLozinka()=" + getLozinka() + ", getUloga()="
				+ getUloga() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
}
