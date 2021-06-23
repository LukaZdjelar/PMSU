package com.ftn.dostavaOSA.service;

import com.ftn.dostavaOSA.model.Kupac;

public interface KupacService {

	Kupac save(Kupac kupac);
	Kupac findKupacById(Long id);
}
