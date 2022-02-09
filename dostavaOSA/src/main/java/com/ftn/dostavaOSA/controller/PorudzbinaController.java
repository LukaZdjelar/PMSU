package com.ftn.dostavaOSA.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.dostavaOSA.dto.ArtikalDTO;
import com.ftn.dostavaOSA.dto.ArtikalFilterDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaFilterDTO;
import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;
import com.ftn.dostavaOSA.dto.StavkaDTO;
import com.ftn.dostavaOSA.model.Kupac;
import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Stavka;
import com.ftn.dostavaOSA.service.interfaces.ArtikalService;
import com.ftn.dostavaOSA.service.interfaces.KupacService;
import com.ftn.dostavaOSA.service.interfaces.PorudzbinaService;
import com.ftn.dostavaOSA.service.interfaces.StavkaService;

@Controller
@RequestMapping("/porudzbina")
public class PorudzbinaController {
	
	@Autowired
	PorudzbinaService porudzbinaService;
	
	@Autowired
	ArtikalService artikalService;
	
	@Autowired
	KupacService kupacService;
	
	@Autowired
	StavkaService stavkaService;

	StavkaDTO stavkaDTO = new StavkaDTO();

	Logger logger = LogManager.getLogger();
	
	@GetMapping
	public ResponseEntity<List<Porudzbina>> getAll(){
		return new ResponseEntity<>(porudzbinaService.findAll(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PorudzbinaDTO> getOne(@PathVariable("id") Long id){
		PorudzbinaDTO porudzbinaDTO = new PorudzbinaDTO(porudzbinaService.findPorudzbinaById(id));
		return new ResponseEntity<>(porudzbinaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "stavke/{id}")
	public ResponseEntity<List<Stavka>> getOneStavke(@PathVariable("id") Long id){
		Porudzbina porudzbina = porudzbinaService.findPorudzbinaById(id);
		return new ResponseEntity<>(porudzbina.getStavke(), HttpStatus.OK);
	}
	
	@PostMapping("/postoji")
	public ResponseEntity<Long> pending(@RequestBody PorudzbinaKorpaDTO pkDTO){
		
		if(porudzbinaService.isPending(pkDTO.getKupacId(), pkDTO.getProdavacId())) {
			Long id = porudzbinaService.findPending(pkDTO.getKupacId(), pkDTO.getProdavacId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(Long.valueOf(0), HttpStatus.OK);
	}
	
	
	@PostMapping("/nova")
	public ResponseEntity<Porudzbina> addNew(@RequestBody PorudzbinaKorpaDTO pkDTO){
		if (pkDTO.getPorudzbinaId() != null) {
			if (porudzbinaService.articleInCart(pkDTO)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}
		Porudzbina porudzbina = porudzbinaService.convert(pkDTO);
		
		if (porudzbinaService.isPending(pkDTO.getKupacId(), pkDTO.getProdavacId())) {
			Porudzbina nadji = porudzbinaService.findPorudzbinaById(pkDTO.getPorudzbinaId());
			
			List<Stavka> stavke = nadji.getStavke();
			stavke.add(new Stavka(pkDTO.getKolicina(), artikalService.findArtikalById(pkDTO.getArtikalId())));
			nadji.setStavke(stavke);
			
			porudzbinaService.save(nadji);
			logger.info("Uspesno dodato u korpu");
		}else {
			porudzbinaService.save(porudzbina);
			logger.info("Uspesno dodato u korpu");
		}
		return new ResponseEntity<Porudzbina>(porudzbina, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@PutMapping("/poruci")
	public ResponseEntity<Porudzbina> order(@RequestBody Long id){
		Porudzbina porudzbina = porudzbinaService.order(id);
		return new ResponseEntity<>(porudzbina, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@GetMapping(value = "/recenzije/{id}")
	public ResponseEntity<List<PorudzbinaDTO>> toBeReviewed(@PathVariable("id") Long id){
		List<PorudzbinaDTO> listDTO = new ArrayList<>();
		List<Porudzbina> porudzbine = porudzbinaService.findAll();
		for (Porudzbina porudzbina : porudzbine) {
			if (porudzbina.getKupac().getId() == id && porudzbina.getOcena() == null && porudzbina.getSatnica() != null) {
				listDTO.add(new PorudzbinaDTO(porudzbina));
			}
		}
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@PutMapping(value = "/oceni/{id}")
	public ResponseEntity<PorudzbinaDTO> review(@PathVariable("id") Long id, @RequestBody PorudzbinaDTO porudzbinaDTO){
		
		Porudzbina porudzbina = porudzbinaService.findPorudzbinaById(id);
		porudzbina.setAnonimniKomentar(porudzbinaDTO.isAnonimniKomentar());
		porudzbina.setOcena(porudzbinaDTO.getOcena());
		porudzbina.setKomentar(porudzbinaDTO.getKomentar());
		porudzbinaService.save(porudzbina);
		porudzbinaDTO = new PorudzbinaDTO(porudzbina);
		porudzbinaService.index(porudzbinaDTO);

		logger.info("Uspesno ocenjivanje");
		return new ResponseEntity<>(porudzbinaDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC', 'ADMINISTRATOR', 'KUPAC')")
	@GetMapping(value = "/komentari/{id}")
	public ResponseEntity<List<PorudzbinaDTO>> comments(@PathVariable("id") Long id){
		List<PorudzbinaDTO> listDTO = new ArrayList<>();
		List<Porudzbina> porudzbine = porudzbinaService.findAll();
		for (Porudzbina porudzbina : porudzbine) {
			if (porudzbina.getProdavac().getId() == id && porudzbina.getOcena() != null && porudzbina.isArhiviranikomentar() == false) {
				listDTO.add(new PorudzbinaDTO(porudzbina));
			}
		}
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@PutMapping(value = "/izbaci/{id}")
	public ResponseEntity<Long> remove(@PathVariable("id") Long stavkaId){
		
		for (Porudzbina porudzbina : porudzbinaService.findAll()) {
			for (Stavka stavka : porudzbina.getStavke()) {
				if (stavka.getId() == stavkaId) {
					porudzbina.getStavke().remove(stavka);
					porudzbinaService.save(porudzbina);
					porudzbinaService.index(new PorudzbinaDTO(porudzbina));
					logger.info("Uspesno izbaceno iz korpe");
					return new ResponseEntity<>(stavkaId, HttpStatus.OK);
				}
			}
		}
		return null;
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@PutMapping(value = "/kolicina/{id}")
	public ResponseEntity<StavkaDTO> ammount(@PathVariable("id") Long stavkaId, @RequestBody Integer ammount){
		
		for (Porudzbina porudzbina : porudzbinaService.findAll()) {
			for (Stavka stavka : porudzbina.getStavke()) {
				if (stavka.getId() == stavkaId) {
					porudzbina.getStavke().remove(stavka);
					stavka.setKolicina(ammount);
					porudzbina.getStavke().add(stavka);
					porudzbinaService.save(porudzbina);
					logger.info("Uspesno promenjena kolicina");
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}
		return null;
	}
	
	@PreAuthorize("hasAnyRole('PRODAVAC')")
	@PutMapping(value = "/arhiviraj/{id}")
	public ResponseEntity<PorudzbinaDTO> archive(@PathVariable("id") Long id){
		Porudzbina porudzbina = porudzbinaService.findPorudzbinaById(id);
		
		porudzbina.setArhiviranikomentar(true);
		porudzbinaService.save(porudzbina);
		
		PorudzbinaDTO porudzbinaDTO = new PorudzbinaDTO(porudzbina);

		logger.info("Uspesno arhiviran komentar");
		return new ResponseEntity<>(porudzbinaDTO, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyRole('KUPAC')")
	@PostMapping("/filter")
	public ResponseEntity<List<PorudzbinaDTO>> filter(@RequestBody PorudzbinaFilterDTO porudzbinaFilterDTO){
		return new ResponseEntity<>(porudzbinaService.filter(porudzbinaFilterDTO), HttpStatus.OK);
	}
	
	@PostMapping("/es/indexsql")
	public ResponseEntity<?> indexsql(){
		for (Porudzbina porudzbina: porudzbinaService.findAll()) {
			if (porudzbina.isDostavljeno()) {
				porudzbinaService.index(new PorudzbinaDTO(porudzbina));
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
