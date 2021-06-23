package com.ftn.dostavaOSA.dto;

import java.util.ArrayList;
import java.util.List;

import com.ftn.dostavaOSA.model.Stavka;

public class StavkaDTO {
	
	private ArtikalDTO artikal;
	private int kolicina;
	
	public StavkaDTO() {
		
	}
	
	public StavkaDTO(Stavka stavka) {
		super();
		artikal = new ArtikalDTO(stavka.getArtikal());
		kolicina = stavka.getKolicina();
	}
	


	public ArtikalDTO getArtikal() {
		return artikal;
	}

	public void setArtikal(ArtikalDTO artikal) {
		this.artikal = artikal;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	@Override
	public String toString() {
		return "StavkaDTO [artikal=" + artikal + ", kolicina=" + kolicina + "]";
	}
}
