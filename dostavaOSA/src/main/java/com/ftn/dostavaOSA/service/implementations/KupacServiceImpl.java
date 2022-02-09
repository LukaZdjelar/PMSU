package com.ftn.dostavaOSA.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.repository.KupacRepository;
import com.ftn.dostavaOSA.service.interfaces.KupacService;

@Service
public class KupacServiceImpl implements KupacService{
	
	@Autowired
	KupacRepository kupacRepository;

	@Override
	public Kupac save(Kupac kupac) {
		return kupacRepository.save(kupac);
	}

	@Override
	public Kupac findKupacById(Long id) {
//		System.out.println("Kupac");
//		System.out.println(id);
		return kupacRepository.findKupacById(id);
	}
}
