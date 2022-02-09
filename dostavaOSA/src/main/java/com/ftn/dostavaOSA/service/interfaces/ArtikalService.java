package com.ftn.dostavaOSA.service.interfaces;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.dto.ArtikalFilterDTO;
import com.ftn.dostavaOSA.model.Artikal;

public interface ArtikalService {

	Artikal findArtikalById(Long id);
	List<Artikal> findAll();
	Artikal save(Artikal artikal);
	void delete(Artikal artikal);
	
	//ES
	List<ArtikalDTO> findAllES();
	void index(ArtikalDTO artikalDTO);
	List<ArtikalDTO> findAllByNaziv(String naziv, Long prodavacId);
	void deleteIndex(ArtikalDTO artikalDTO);
	List<ArtikalDTO> filter(ArtikalFilterDTO filter);
}
