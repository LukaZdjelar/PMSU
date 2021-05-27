package com.ftn.dostavaOSA.dto;

public class ArtikalDTO {

	private Long id;
	private String naziv;
	private String opis;
	private Double cena;
	private Long prodavacId;
	
	public ArtikalDTO() {
		
	}

	public ArtikalDTO(Long id, String naziv, String opis, Double cena, Long prodavacId) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.cena = cena;
		this.prodavacId = prodavacId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(Long prodavacId) {
		this.prodavacId = prodavacId;
	}
	
	
}
