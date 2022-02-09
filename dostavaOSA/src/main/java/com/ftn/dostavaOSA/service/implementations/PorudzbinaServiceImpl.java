package com.ftn.dostavaOSA.service.implementations;

import java.time.LocalDate;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaFilterDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;
import com.ftn.dostavaOSA.dto.SimpleQueryDTO;
import com.ftn.dostavaOSA.dto.StavkaDTO;
import com.ftn.dostavaOSA.lucene.search.SearchQueryGenerator;
import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Stavka;
import com.ftn.dostavaOSA.repository.PorudzbinaRepository;
import com.ftn.dostavaOSA.repositoryES.PorudzbinaRepositoryES;
import com.ftn.dostavaOSA.service.interfaces.ArtikalService;
import com.ftn.dostavaOSA.service.interfaces.KupacService;
import com.ftn.dostavaOSA.service.interfaces.PorudzbinaService;
import com.ftn.dostavaOSA.service.interfaces.ProdavacService;

@Service
public class PorudzbinaServiceImpl implements PorudzbinaService {
	
	@Autowired
	PorudzbinaRepository porudzbinaRepository;
	
	@Autowired
	KupacService kupacService;
	
	@Autowired
	ProdavacService prodavacService;
	
	@Autowired
	ArtikalService artikalService;
	
	@Autowired
	PorudzbinaRepositoryES porudzbinaRepositoryES;
	
	@Autowired
	ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Override
	public List<Porudzbina> findAll() {
		return porudzbinaRepository.findAll();
	}

	@Override
	public Porudzbina convert(PorudzbinaKorpaDTO pkDTO) {
		
		Porudzbina porudzbina = new Porudzbina();
		
		porudzbina.setId(pkDTO.getPorudzbinaId());
		System.out.println(pkDTO.toString());
		porudzbina.setKupac(kupacService.findKupacById(pkDTO.getKupacId()));
		porudzbina.setProdavac(prodavacService.findProdavacById(pkDTO.getProdavacId()));
		
		List<Stavka> stavke = new ArrayList<>();
		stavke.add(new Stavka(pkDTO.getKolicina(), artikalService.findArtikalById(pkDTO.getArtikalId())));
		porudzbina.setStavke(stavke);
		
		return porudzbina;
	}

	@Override
	public Porudzbina save(Porudzbina porudzbina) {
		return porudzbinaRepository.save(porudzbina);
	}

	@Override
	public Boolean isPending(Long kupacId, Long prodavacId) {
		List<Porudzbina> kupacProdavacPorudzbina = new ArrayList<>();
		for (Porudzbina porudzbina : findAll()) {
			if (porudzbina.getKupac().getId() == kupacId && porudzbina.getProdavac().getId() == prodavacId) {
				kupacProdavacPorudzbina.add(porudzbina);
			}
		}
		
		if (kupacProdavacPorudzbina.size()>0) {
			
			for (Porudzbina porudzbina : kupacProdavacPorudzbina) {
				if (porudzbina.isDostavljeno()==false) {
					return true;
				}
			}
			
		}
		else { return false; }
		
		return false;
	}

	@Override
	public Porudzbina findPorudzbinaById(Long id) {
		return porudzbinaRepository.findPorudzbinaById(id);
	}

	@Override
	public Long findPending(Long kupacId, Long prodavacId) {
		
		List<Porudzbina> kupacProdavacPorudzbina = new ArrayList<>();
		for (Porudzbina porudzbina : findAll()) {
			if (porudzbina.getKupac().getId() == kupacId && porudzbina.getProdavac().getId() == prodavacId) {
				kupacProdavacPorudzbina.add(porudzbina);
			}
		}
		
		if (kupacProdavacPorudzbina.size()>0) {
			for (Porudzbina porudzbina : kupacProdavacPorudzbina) {
				if (porudzbina.isDostavljeno()==false) {
					return porudzbina.getId();
				}
			}
		}else {
			return null;
		}
		
		return null;
	}

	@Override
	public Porudzbina order(Long id) {
		Porudzbina porudzbina = porudzbinaRepository.findPorudzbinaById(id);
		porudzbina.setDostavljeno(true);
		porudzbina.setSatnica(LocalDate.now());
		porudzbinaRepository.save(porudzbina);
		return porudzbina;
	}
	
	public List<StavkaDTO> stavkeDTO(List<Stavka> stavke) {
		List<StavkaDTO> list = new ArrayList<>();
		for (Stavka stavka : stavke) {
			list.add(new StavkaDTO(stavka));
		}
		return list;
	}

	@Override
	public boolean articleInCart(PorudzbinaKorpaDTO pkDTO) {
		Porudzbina porudzbina = findPorudzbinaById(pkDTO.getPorudzbinaId());
		for (Stavka stavka : porudzbina.getStavke()) {
			if (stavka.getArtikal().getId() == pkDTO.getArtikalId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void index(PorudzbinaDTO porudzbinaDTO) {
		porudzbinaRepositoryES.save(porudzbinaDTO);
	}

	@Override
	public void deleteIndex(PorudzbinaDTO porudzbinaDTO) {
		porudzbinaRepositoryES.delete(porudzbinaDTO);
	}

	@Override
	public List<PorudzbinaDTO> filter(PorudzbinaFilterDTO filter) {
		QueryBuilder prodavacIdQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryDTO("prodavacId",String.valueOf(filter.getProdavacId())));
		
		BoolQueryBuilder boolQueryFilter = QueryBuilders
                .boolQuery()
                .must(prodavacIdQuery);
		
		if (filter.getKomentar() != null) {
			QueryBuilder komentarQuery = SearchQueryGenerator.createFuzzyQueryBuilder(new SimpleQueryDTO("komentar", filter.getKomentar()));
			boolQueryFilter.must(komentarQuery);
		}
		
		if (filter.getOcenaOd() != null && filter.getOcenaDo() != null) {
			QueryBuilder ocenaQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryDTO("ocena", filter.getOcenaOd().toString() + "-" + filter.getOcenaDo().toString()));
			boolQueryFilter.must(ocenaQuery);
		}
		
		if (filter.getCenaOd() != null && filter.getCenaDo() != null) {
			QueryBuilder ocenaQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryDTO("cena", filter.getCenaOd().toString() + "-" + filter.getCenaDo().toString()));
			boolQueryFilter.must(ocenaQuery);
		}

		List<PorudzbinaDTO> filterList = new ArrayList<>();
		for (SearchHit<PorudzbinaDTO> searchHit : searchByBoolQuery(boolQueryFilter)) {
			filterList.add(searchHit.getContent());
		}
		return filterList;
	}
	
	public SearchHits<PorudzbinaDTO> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, PorudzbinaDTO.class, IndexCoordinates.of("porudzbine"));
	}
}
