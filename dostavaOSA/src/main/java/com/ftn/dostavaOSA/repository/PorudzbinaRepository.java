package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dostavaOSA.model.Porudzbina;

public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Long> {

	Porudzbina findPorudzbinaById(Long id);
}
