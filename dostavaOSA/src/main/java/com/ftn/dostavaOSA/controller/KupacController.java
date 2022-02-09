package com.ftn.dostavaOSA.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.model.EUloga;
import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.service.interfaces.KupacService;

@Controller
@RequestMapping("/kupac")
public class KupacController {

	@Autowired
	KupacService kupacService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	Logger logger = LogManager.getLogger();
	
	@PostMapping("/registracija")
	public ResponseEntity<Kupac> register(@RequestBody Kupac kupac){
		System.out.println("usao");
		System.out.println(kupac.toString());
		
		
		if (kupac.getIme() =="" || kupac.getIme() == null) {
			return new ResponseEntity<Kupac>(HttpStatus.BAD_REQUEST);
		}
		
		if (kupac.getPrezime() =="" || kupac.getPrezime() == null) {
			return new ResponseEntity<Kupac>(HttpStatus.BAD_REQUEST);
		}
		
		if (kupac.getKorisnickoIme() =="" || kupac.getKorisnickoIme() == null) {
			return new ResponseEntity<Kupac>(HttpStatus.BAD_REQUEST);
		}
		
		if (kupac.getLozinka() =="" || kupac.getLozinka() == null) {
			return new ResponseEntity<Kupac>(HttpStatus.BAD_REQUEST);
		}
		
		if (kupac.getAdresa() =="" || kupac.getAdresa() == null) {
			return new ResponseEntity<Kupac>(HttpStatus.BAD_REQUEST);
		}
		
		String sifrovanaLozinka = passwordEncoder.encode(kupac.getLozinka());
		kupac.setLozinka(sifrovanaLozinka);
		
		kupac.setUloga(EUloga.KUPAC);
		kupac.setBanovan(false);
		
		kupacService.save(kupac);
		logger.info("Uspesna registracija");
		
		return new ResponseEntity<Kupac>(kupac, HttpStatus.OK);
	}
}
