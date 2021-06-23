package com.ftn.dostavaOSA.service;

import java.util.List;

import com.ftn.dostavaOSA.model.Korisnik;

public interface KorisnikService {

	Korisnik findKorisnikByKorisnickoIme(String korisnickoIme);
	Korisnik findKorisnikById(Long id);
	List<Korisnik> findAll();
}
