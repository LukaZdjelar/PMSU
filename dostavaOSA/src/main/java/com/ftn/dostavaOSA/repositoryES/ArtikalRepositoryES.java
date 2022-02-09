package com.ftn.dostavaOSA.repositoryES;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.model.Artikal;

@Repository
public interface ArtikalRepositoryES extends ElasticsearchRepository<ArtikalDTO, Long>{
	
	List<ArtikalDTO> findAll();

	List<ArtikalDTO> findAllByNaziv(String naziv);
	
	List<ArtikalDTO> findByNazivContaining(String naziv);
	
	List<ArtikalDTO> findByNazivLikeAndProdavacId(String naziv, Long prodavacId);
}
