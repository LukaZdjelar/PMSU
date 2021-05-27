package com.ftn.dostavaOSA.service;

import java.util.List;

import com.ftn.dostavaOSA.model.Artikal;

public interface ArtikalService {

	Artikal findArtikalById(Long id);
	List<Artikal> findAll();
	Artikal save(Artikal artikal);
	void delete(Artikal artikal);
}
