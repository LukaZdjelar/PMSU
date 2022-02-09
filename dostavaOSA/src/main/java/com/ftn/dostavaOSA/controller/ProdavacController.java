package com.ftn.dostavaOSA.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.dto.ProdavacDTO;
import com.ftn.dostavaOSA.model.EUloga;
import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.service.interfaces.PorudzbinaService;
import com.ftn.dostavaOSA.service.interfaces.ProdavacService;

@Controller
@RequestMapping("/prodavac")
public class ProdavacController {

	@Autowired
	ProdavacService prodavacService;
	
	@Autowired
	PorudzbinaService porudzbinaService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	Logger logger = LogManager.getLogger();
	
	@PreAuthorize("hasAnyRole('PRODAVAC', 'ADMINISTRATOR', 'KUPAC')")
	@GetMapping
	public ResponseEntity<List<ProdavacDTO>> getAll(){
		
		List<Prodavac> prodavci = prodavacService.findAll();
		List<ProdavacDTO> dtoList = new ArrayList<ProdavacDTO>();
		
		for (Prodavac prodavac : prodavci) {
			dtoList.add(new ProdavacDTO(prodavac));
		}
		
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC', 'ADMINISTRATOR', 'KUPAC')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdavacDTO> get(@PathVariable("id") Long id){
		
		Prodavac prodavac = prodavacService.findProdavacById(id);
		
		ProdavacDTO prodavacDTO = new ProdavacDTO(prodavac);
		
		return new ResponseEntity<>(prodavacDTO, HttpStatus.OK);
	}
	
	@PostMapping("/registracija")
	public ResponseEntity<Prodavac> register(@RequestBody Prodavac prodavac){
		
		if (prodavac.getIme() =="" || prodavac.getIme() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getPrezime() =="" || prodavac.getPrezime() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getKorisnickoIme() =="" || prodavac.getKorisnickoIme() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getLozinka() =="" || prodavac.getLozinka() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getAdresa() =="" || prodavac.getAdresa() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getEmail() =="" || prodavac.getEmail() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		if (prodavac.getNaziv() =="" || prodavac.getNaziv() == null) {
			return new ResponseEntity<Prodavac>(HttpStatus.BAD_REQUEST);
		}
		
		String sifrovanaLozinka = passwordEncoder.encode(prodavac.getLozinka());
		prodavac.setLozinka(sifrovanaLozinka);
		
		prodavac.setUloga(EUloga.PRODAVAC);
		prodavac.setBanovan(false);
		prodavac.setPoslujeOd(LocalDate.now());
		
		prodavacService.save(prodavac);
		logger.info("Uspesna registracija");
		
		return new ResponseEntity<Prodavac>(prodavac, HttpStatus.OK);
	}
	
	@GetMapping("/prosek/{id}") 
	public ResponseEntity<Double> average(@PathVariable("id") Long id){
		Double prosecnaOcena;
		Integer suma = 0;
		List<Integer> ocene = new ArrayList<>();
		List<Porudzbina> porudzbine = porudzbinaService.findAll();
		for (Porudzbina porudzbina : porudzbine) {
			if (porudzbina.getProdavac().getId() == id && porudzbina.getOcena() != null) {
				ocene.add(porudzbina.getOcena());
			}
		}
		for (Integer ocena : ocene) {
			suma += ocena;
		}
		prosecnaOcena = (suma * 1.0 / ocene.size());
		
		prosecnaOcena = Double.valueOf(df2.format(prosecnaOcena));
		
		return new ResponseEntity<>(prosecnaOcena, HttpStatus.OK);
	}
}
