package com.ftn.dostavaOSA.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.service.ArtikalService;
import com.ftn.dostavaOSA.service.ProdavacService;

@Controller
@RequestMapping("/artikal")
public class ArtikalController {

	@Autowired
	ArtikalService artikalService;
	
	@Autowired
	ProdavacService prodavacService;
	
//	@GetMapping
//	public ResponseEntity<List<ArtikalDTO>> getAll(){
//		List<ArtikalDTO> dtoList = new ArrayList<>();
//		
//		for (Artikal artikal : artikalService.findAll()) {
//			dtoList.add(new ArtikalDTO(artikal));
//		}
//		
//		return new ResponseEntity<>(dtoList, HttpStatus.OK);
//	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC', 'ADMINISTRATOR', 'KUPAC')")
	@GetMapping
	public ResponseEntity<List<ArtikalDTO>> getAll(Long prodavacId){
		List<ArtikalDTO> dtoList = new ArrayList<>();
		
		for (Artikal artikal : artikalService.findAll()) {
			if(artikal.getProdavac().getId() == prodavacId) {
				dtoList.add(new ArtikalDTO(artikal));
			}
		}
		
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC', 'ADMINISTRATOR', 'KUPAC')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ArtikalDTO> get(@PathVariable("id") Long id){
		
		Artikal artikal = artikalService.findArtikalById(id);
		ArtikalDTO artikalDTO = new ArtikalDTO(artikal);
		
		return new ResponseEntity<>(artikalDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC')")
	@PostMapping
	public ResponseEntity<Artikal> create(@RequestBody ArtikalDTO artikalDTO){
		
		Artikal artikal = new Artikal(null, artikalDTO.getNaziv(), artikalDTO.getOpis(), artikalDTO.getCena(), "abc", 
									prodavacService.findProdavacById(artikalDTO.getProdavacId()));
		
		artikalService.save(artikal);
		
		return new ResponseEntity<Artikal>(artikal, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Artikal> update(@RequestBody ArtikalDTO novi, @PathVariable("id") Long id){
		
		Artikal artikal = artikalService.findArtikalById(id);
		artikal = new Artikal(id, novi.getNaziv(), novi.getOpis(), novi.getCena(), artikal.getPutanjaSlike(), 
				artikal.getProdavac());
		
		artikalService.save(artikal);
		
		return new ResponseEntity<Artikal>(artikal, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Artikal> delete(@PathVariable("id") Long id){
		
		Artikal artikal = artikalService.findArtikalById(id);
		
		artikalService.delete(artikal);
		
		return new ResponseEntity<Artikal>(HttpStatus.NO_CONTENT);
	}
}
