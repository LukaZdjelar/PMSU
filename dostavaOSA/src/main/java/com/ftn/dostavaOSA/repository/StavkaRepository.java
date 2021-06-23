package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dostavaOSA.model.Stavka;

public interface StavkaRepository extends JpaRepository<Stavka, Long> {
	Stavka findStavkaById(Long id);
}
