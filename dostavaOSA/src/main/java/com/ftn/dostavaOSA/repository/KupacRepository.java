package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dostavaOSA.model.Kupac;

public interface KupacRepository extends JpaRepository<Kupac, Long> {
	
	Kupac findKupacById(Long id);
}
