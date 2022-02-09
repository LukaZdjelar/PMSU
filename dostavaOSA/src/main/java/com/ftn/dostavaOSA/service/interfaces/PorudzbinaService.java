package com.ftn.dostavaOSA.service.interfaces;

import java.util.List;

import com.ftn.dostavaOSA.dto.PorudzbinaDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaFilterDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;
import com.ftn.dostavaOSA.dto.StavkaDTO;
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

	//ES
//	List<PorudzbinaDTO> findAllES();
	void index(PorudzbinaDTO porudzbinaDTO);
	void deleteIndex(PorudzbinaDTO porudzbinaDTO);
	List<PorudzbinaDTO> filter(PorudzbinaFilterDTO filter);
}
