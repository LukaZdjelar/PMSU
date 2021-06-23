package com.ftn.dostavaOSA.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.dto.KorisnikDTO;
import com.ftn.dostavaOSA.model.EUloga;
import com.ftn.dostavaOSA.model.Korisnik;
import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.security.TokenUtils;
import com.ftn.dostavaOSA.service.KorisnikService;
import com.ftn.dostavaOSA.service.KupacService;
import com.ftn.dostavaOSA.service.ProdavacService;

@Controller
@RequestMapping("/korisnik")
@CrossOrigin
public class KorisnikController {

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    KorisnikService korisnikService;
    
    @Autowired
    KupacService kupacService;
    
    @Autowired
    ProdavacService prodavacService;
	
	@PostMapping
    public ResponseEntity<String> login(@RequestBody KorisnikDTO korisnikDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(korisnikDTO.getKorisnickoIme(), korisnikDTO.getLozinka());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(korisnikDTO.getKorisnickoIme());
            System.out.println(tokenUtils.generateToken(userDetails));
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
	@GetMapping
	public ResponseEntity<List<KorisnikDTO>> getAll(){
		List<Korisnik> korisnici = korisnikService.findAll();
		List<KorisnikDTO> listDTO = new ArrayList<>();
		
		for (Korisnik korisnik : korisnici) {
			listDTO.add(new KorisnikDTO(korisnik));
		}
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
	@PutMapping(value = "/blok/{id}")
	public ResponseEntity<KorisnikDTO> block(@PathVariable("id") Long id){
		
		Korisnik korisnik = korisnikService.findKorisnikById(id);
		KorisnikDTO korisnikDTO = new KorisnikDTO(korisnik);
		Boolean status = true;
		
		if (korisnik.getUloga() == EUloga.KUPAC) {
			Kupac kupac = kupacService.findKupacById(id);
			if (kupac.isBanovan()) {
				status = false;
			}
			kupac.setBanovan(status);
			kupacService.save(kupac);
		}else if(korisnik.getUloga() == EUloga.PRODAVAC) {
			Prodavac prodavac = prodavacService.findProdavacById(id);
			if (prodavac.isBanovan()) {
				status = false;
			}
			prodavac.setBanovan(status);
			prodavacService.save(prodavac);
		}
		
		return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
	}
}
