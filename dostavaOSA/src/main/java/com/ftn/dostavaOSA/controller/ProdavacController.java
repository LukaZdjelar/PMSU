package com.ftn.dostavaOSA.controller;

import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.service.ProdavacService;

@Controller
@RequestMapping("/prodavac")
public class ProdavacController {

	@Autowired
	ProdavacService prodavacService;
	
	@GetMapping
	public ResponseEntity<List<Prodavac>> getAll(){
		
		List<Prodavac> prodavci = prodavacService.findAll();
		
		return new ResponseEntity<>(prodavci, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Prodavac> get(Long id){
		
		Prodavac prodavac = prodavacService.findProdavacById(id);
		
		return new ResponseEntity<>(prodavac, HttpStatus.OK);
	}
}
