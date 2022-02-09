package com.ftn.dostavaOSA.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.dto.ArtikalFilterDTO;
import com.ftn.dostavaOSA.dto.SimpleQueryDTO;
import com.ftn.dostavaOSA.lucene.search.SearchQueryGenerator;
import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.repository.ArtikalRepository;
import com.ftn.dostavaOSA.repositoryES.ArtikalRepositoryES;
import com.ftn.dostavaOSA.service.interfaces.ArtikalService;

import aj.org.objectweb.asm.Type;

@Service
public class ArtikalServiceImpl implements ArtikalService{
	
	@Autowired
	ArtikalRepository artikalRepository;
	
	@Autowired
	ArtikalRepositoryES artikalRepositoryES;
	
	@Autowired
	ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Override
	public Artikal findArtikalById(Long id) {
		Artikal artikal = artikalRepository.findArtikalById(id);
		return artikal;
	}

	@Override
	public List<Artikal> findAll() {
		return artikalRepository.findAll();
	}

	@Override
	public Artikal save(Artikal artikal) {
		return artikalRepository.save(artikal);
	}

	@Override
	public void delete(Artikal artikal) {
		artikalRepository.delete(artikal);
	}

	@Override
	public List<ArtikalDTO> findAllES() {
		return artikalRepositoryES.findAll();
	}

	@Override
	public void index(ArtikalDTO artikalDTO) {
		artikalRepositoryES.save(artikalDTO);
	}

	@Override
	public List<ArtikalDTO> findAllByNaziv(String naziv, Long prodavacId) {
		return artikalRepositoryES.findByNazivLikeAndProdavacId(naziv, prodavacId);
	}

	@Override
	public void deleteIndex(ArtikalDTO artikalDTO) {
		artikalRepositoryES.delete(artikalDTO);
	}

	@Override
	public List<ArtikalDTO> filter(ArtikalFilterDTO filter) {
		if (filter.getCenaOd() == null) {
			filter.setCenaOd(0);
		}
		if(filter.getCenaDo() == null) {
			filter.setCenaDo(Integer.MAX_VALUE);
		}
		
		QueryBuilder prodavacIdQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryDTO("prodavacId",String.valueOf(filter.getProdavacId())));
		
		BoolQueryBuilder boolQueryFilter = QueryBuilders
                .boolQuery()
                .must(prodavacIdQuery);
		
		if (filter.getNaziv() != null) {
			QueryBuilder nazivQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryDTO("naziv", filter.getNaziv()));
			boolQueryFilter.must(nazivQuery);
		}
		
		if (filter.getOpis() != null) {
			QueryBuilder opisQuery = SearchQueryGenerator.createFuzzyQueryBuilder(new SimpleQueryDTO("opis", filter.getOpis()));
			boolQueryFilter.must(opisQuery);
		}
		
		if (filter.getCenaOd() != null && filter.getCenaDo() != null) {
			QueryBuilder cenaQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryDTO("cena", filter.getCenaOd().toString() + "-" + filter.getCenaDo().toString()));
			boolQueryFilter.must(cenaQuery);
		}
		
		List<ArtikalDTO> filterList = new ArrayList<>();
		System.out.println(searchByBoolQuery(boolQueryFilter));
		for (SearchHit<ArtikalDTO> searchHit : searchByBoolQuery(boolQueryFilter)) {
			filterList.add(searchHit.getContent());
		}
		return filterList;
	}

	public SearchHits<ArtikalDTO> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, ArtikalDTO.class, IndexCoordinates.of("artikli"));
	}
}
