package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dostavaOSA.model.Prodavac;

public interface ProdavacRepository extends JpaRepository<Prodavac, Long>{

	Prodavac findProdavacById(Long id);
}
