package com.ftn.dostavaOSA.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.ftn.dostavaOSA.model.Artikal;

@Document(indexName = "artikli")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class ArtikalDTO {

	@Id
	private Long id;
	@Field(type = FieldType.Text)
	private String naziv;
	@Field(type = FieldType.Text)
	private String opis;
	@Field(type = FieldType.Double)
	private Double cena;
	@Field(type = FieldType.Long)
	private Long prodavacId;
	
	public ArtikalDTO() {
		
	}

	public ArtikalDTO(Artikal artikal) {
		super();
		id = artikal.getId();
		naziv = artikal.getNaziv();
		opis = artikal.getOpis();
		cena = artikal.getCena();
		prodavacId = artikal.getProdavac().getId();
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

	@Override
	public String toString() {
		return "ArtikalDTO [id=" + id + ", naziv=" + naziv + ", opis=" + opis + ", cena=" + cena + ", prodavacId="
				+ prodavacId + "]";
	}
	
	
}
