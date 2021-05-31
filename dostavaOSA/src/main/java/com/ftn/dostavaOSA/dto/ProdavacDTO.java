package com.ftn.dostavaOSA.dto;

import java.time.LocalDate;

import com.ftn.dostavaOSA.model.Prodavac;

public class ProdavacDTO {
	
	private Long id;
    private String naziv;
    private String adresa;
    private String email;
    private LocalDate poslujeOd;
    private Boolean banovan;
    
    public ProdavacDTO() {
    	
    }

	public ProdavacDTO(Prodavac prodavac) {
		super();
		id = prodavac.getId();
		naziv = prodavac.getNaziv();
		adresa = prodavac.getAdresa();
		email = prodavac.getEmail();
		banovan = prodavac.isBanovan();
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getBanovan() {
		return banovan;
	}

	public void setBanovan(Boolean banovan) {
		this.banovan = banovan;
	}
    
    

}
