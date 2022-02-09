package com.ftn.dostavaOSA.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.ftn.dostavaOSA.model.Prodavac;

public interface ProdavacService {

	Prodavac findProdavacById(Long id);
	List<Prodavac> findAll();
	Prodavac save(Prodavac prodavac);
	void delete(Prodavac prodavac);
	ArrayList<String> findAllProdavnice();
}
