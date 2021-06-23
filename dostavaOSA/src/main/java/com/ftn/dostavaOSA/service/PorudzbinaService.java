package com.ftn.dostavaOSA.service;

import java.util.List;

import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;
import com.ftn.dostavaOSA.dto.StavkaDTO;
import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Stavka;

public interface PorudzbinaService {
	List<Porudzbina> findAll();
	Porudzbina convert(PorudzbinaKorpaDTO pkDTO);
	Porudzbina save(Porudzbina porudzbina);
	Boolean isPending(Long kupacId, Long prodavacId);
	Long findPending(Long kupacId, Long prodavacId);
	Porudzbina findPorudzbinaById(Long id);
	Porudzbina order(Long id);
	List<StavkaDTO> stavkeDTO(List<Stavka> stavke);
	boolean articleInCart(PorudzbinaKorpaDTO pkDTO);

}
