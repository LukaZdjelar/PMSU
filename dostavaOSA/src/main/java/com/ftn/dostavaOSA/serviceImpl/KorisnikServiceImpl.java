package com.ftn.dostavaOSA.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Korisnik;
import com.ftn.dostavaOSA.repository.KorisnikRepository;
import com.ftn.dostavaOSA.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService{

	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Override
	public Korisnik findKorisnikByKorisnickoIme(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findKorisnikByKorisnickoIme(korisnickoIme);
		System.out.println(korisnik.toString());
        if (korisnik != null) {
            return korisnik;
        }
        return null;
	}

}
