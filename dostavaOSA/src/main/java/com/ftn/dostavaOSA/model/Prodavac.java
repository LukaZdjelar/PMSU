package com.ftn.dostavaOSA.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prodavac")
public class Prodavac extends Korisnik{

	@Column(name = "poslujeod", nullable = false)
	private LocalDate poslujeOd;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "banovan", nullable = false)
	private boolean banovan;
	
	public Prodavac() {
		
	}

	public Prodavac(Long id, String ime, String prezime, String korisnickoIme, String lozinka, EUloga uloga,
			LocalDate poslujeOd, String email, String adresa, String naziv, boolean banovan) {
		super(id, ime, prezime, korisnickoIme, lozinka, uloga);
		this.poslujeOd = poslujeOd;
		this.email = email;
		this.adresa = adresa;
		this.naziv = naziv;
		this.banovan = banovan;
	}

	@Override
	public String toString() {
		return "Prodavac [poslujeOd=" + poslujeOd + ", email=" + email + ", adresa=" + adresa + ", naziv=" + naziv
				+ ", banovan=" + banovan + "]";
	}

	
}
