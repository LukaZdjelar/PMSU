package com.ftn.dostavaOSA.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.repository.ProdavacRepository;
import com.ftn.dostavaOSA.service.interfaces.ProdavacService;

@Service
public class ProdavacServiceImpl implements ProdavacService{

	@Autowired
	ProdavacRepository prodavacRepository;
	
	@Override
	public Prodavac findProdavacById(Long id) {
		Prodavac prodavac = prodavacRepository.findProdavacById(id);
		return prodavac;
	}

	@Override
	public List<Prodavac> findAll() {
		return prodavacRepository.findAll();
	}

	@Override
	public Prodavac save(Prodavac prodavac) {
		return prodavacRepository.save(prodavac);
	}

	@Override
	public void delete(Prodavac prodavac) {
		prodavacRepository.delete(prodavac);
		
	}

	@Override
	public ArrayList<String> findAllProdavnice() {
		ArrayList<String> prodavnice = new ArrayList<>();
		for (Prodavac prodavac : findAll()) {
			prodavnice.add(prodavac.getNaziv());
		}
		return prodavnice;
	}

}
