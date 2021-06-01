package com.ftn.dostavaOSA.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.repository.KupacRepository;
import com.ftn.dostavaOSA.service.KupacService;

@Service
public class KupacServiceImpl implements KupacService{
	
	@Autowired
	KupacRepository kupacRepository;

	@Override
	public Kupac save(Kupac kupac) {
		return kupacRepository.save(kupac);
	}
}
