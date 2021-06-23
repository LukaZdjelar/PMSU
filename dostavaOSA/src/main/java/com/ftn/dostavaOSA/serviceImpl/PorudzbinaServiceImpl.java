package com.ftn.dostavaOSA.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;
import com.ftn.dostavaOSA.dto.StavkaDTO;
import com.ftn.dostavaOSA.model.Artikal;
import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Stavka;
import com.ftn.dostavaOSA.repository.PorudzbinaRepository;
import com.ftn.dostavaOSA.service.ArtikalService;
import com.ftn.dostavaOSA.service.KupacService;
import com.ftn.dostavaOSA.service.PorudzbinaService;
import com.ftn.dostavaOSA.service.ProdavacService;

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
}
