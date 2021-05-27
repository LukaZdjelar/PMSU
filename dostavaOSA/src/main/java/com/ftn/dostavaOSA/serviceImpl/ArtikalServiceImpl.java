package com.ftn.dostavaOSA.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.repository.ArtikalRepository;
import com.ftn.dostavaOSA.service.ArtikalService;

@Service
public class ArtikalServiceImpl implements ArtikalService{
	
	@Autowired
	ArtikalRepository artikalRepository;

	@Override
	public Artikal findArtikalById(Long id) {
		Artikal artikal = artikalRepository.findArtikalById(id);
		return artikal;
	}

	@Override
	public List<Artikal> findAll() {
		return artikalRepository.findAll();
	}

	@Override
	public Artikal save(Artikal artikal) {
		return artikalRepository.save(artikal);
	}

	@Override
	public void delete(Artikal artikal) {
		artikalRepository.delete(artikal);
		
	}

}
