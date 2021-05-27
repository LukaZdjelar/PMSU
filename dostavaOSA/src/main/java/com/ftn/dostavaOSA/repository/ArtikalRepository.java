package com.ftn.dostavaOSA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dostavaOSA.model.Artikal;

public interface ArtikalRepository extends JpaRepository<Artikal, Long>{

	Artikal findArtikalById(Long id);
}
