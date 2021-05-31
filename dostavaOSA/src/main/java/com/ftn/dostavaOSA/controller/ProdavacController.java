package com.ftn.dostavaOSA.controller;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.dto.ProdavacDTO;
import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.service.ProdavacService;

@Controller
@RequestMapping("/prodavac")
public class ProdavacController {

	@Autowired
	ProdavacService prodavacService;
	
	@GetMapping
	public ResponseEntity<List<ProdavacDTO>> getAll(){
		
		List<Prodavac> prodavci = prodavacService.findAll();
		List<ProdavacDTO> dtoList = new ArrayList<ProdavacDTO>();
		
		for (Prodavac prodavac : prodavci) {
			dtoList.add(new ProdavacDTO(prodavac));
		}
		
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}
	
	@GetMapping("/prodavnice")
	public ResponseEntity<ArrayList<String>> getAllProdavnica(){
		
		ArrayList<String> prodavnice = prodavacService.findAllProdavnice();
		return new ResponseEntity<>(prodavnice, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Prodavac> get(Long id){
		
		Prodavac prodavac = prodavacService.findProdavacById(id);
		
		return new ResponseEntity<>(prodavac, HttpStatus.OK);
	}
}
