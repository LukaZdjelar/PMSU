package com.ftn.dostavaOSA.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Stavka;
import com.ftn.dostavaOSA.repository.StavkaRepository;
import com.ftn.dostavaOSA.service.interfaces.StavkaService;

@Service
public class StavkaServiceImpl implements StavkaService {

	@Autowired
	StavkaRepository stavkaRepository;
	
	@Override
	public Stavka findStavkaById(Long id) {
		return stavkaRepository.findStavkaById(id);
	}

	@Override
	public void delete(Stavka stavka) {
		stavkaRepository.delete(stavka);
	}
}
