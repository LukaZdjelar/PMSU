package com.ftn.dostavaOSA.dto;

import com.ftn.dostavaOSA.model.Porudzbina;

public class PorudzbinaKorpaDTO {
	
	private Long porudzbinaId;
	private Long kupacId;
	private Long prodavacId;
	private Long artikalId;
	private int kolicina;
	
	public PorudzbinaKorpaDTO(Long porudzbinaId, Long kupacId, Long prodavacId, Long artikalId, int kolicina) {
		super();
		this.porudzbinaId = porudzbinaId;
		this.kupacId = kupacId;
		this.prodavacId = prodavacId;
		this.artikalId = artikalId;
		this.kolicina = kolicina;
	}

	public Long getPorudzbinaId() {
		return porudzbinaId;
	}

	public void setPorudzbinaId(Long porudzbinaId) {
		this.porudzbinaId = porudzbinaId;
	}

	public Long getKupacId() {
		return kupacId;
	}

	public void setKupacId(Long kupacId) {
		this.kupacId = kupacId;
	}

	public Long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(Long prodavacId) {
		this.prodavacId = prodavacId;
	}

	public Long getArtikalId() {
		return artikalId;
	}

	public void setArtikalId(Long artikalId) {
		this.artikalId = artikalId;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	@Override
	public String toString() {
		return "PorudzbinaKorpaDTO [porudzbinaId=" + porudzbinaId + ", kupacId=" + kupacId + ", prodavacId="
				+ prodavacId + ", artikalId=" + artikalId + ", kolicina=" + kolicina + "]";
	}
}
