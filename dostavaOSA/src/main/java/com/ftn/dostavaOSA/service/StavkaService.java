package com.ftn.dostavaOSA.service;

import com.ftn.dostavaOSA.model.Stavka;

public interface StavkaService {

	Stavka findStavkaById(Long id);
	void delete(Stavka stavka);
}
