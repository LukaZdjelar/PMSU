package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dostavaOSA.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
	Korisnik findKorisnikByKorisnickoIme(String korisnickoIme);
}
