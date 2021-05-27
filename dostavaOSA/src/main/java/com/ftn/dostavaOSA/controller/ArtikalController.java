package com.ftn.dostavaOSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.service.ArtikalService;

@Controller
@RequestMapping("/artikal")
public class ArtikalController {

	@Autowired
	ArtikalService artikalService;
	
	@GetMapping
	public ResponseEntity<List<Artikal>> getAll(){
		
		List<Artikal> artikli = artikalService.findAll();
		
		return new ResponseEntity<>(artikli, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Artikal> get(@PathVariable("id") Long id){
		
		Artikal artikal = artikalService.findArtikalById(id);
		
		return new ResponseEntity<>(artikal, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Artikal> create(Artikal artikal){
		
		artikalService.save(artikal);
		
		return new ResponseEntity<Artikal>(artikal, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Artikal> update(Artikal novi, @PathVariable("id") Long id){
		
		Artikal artikal = artikalService.findArtikalById(id);
		artikal = novi;
		
		artikalService.save(artikal);
		
		return new ResponseEntity<Artikal>(artikal, HttpStatus.OK);
	}
	
	@DeleteMapping()
	public ResponseEntity<Artikal> delete(Long id){
		
		System.out.println(id);
		
		Artikal artikal = artikalService.findArtikalById(id);
		
		System.out.println(artikal);
		
		artikalService.delete(artikal);
		
		return new ResponseEntity<Artikal>(HttpStatus.NO_CONTENT);
	}
}
