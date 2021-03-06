package com.ftn.dostavaOSA.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.ftn.dostavaOSA.model.Porudzbina;
import com.ftn.dostavaOSA.model.Prodavac;
import com.ftn.dostavaOSA.model.Stavka;
import com.ftn.dostavaOSA.service.interfaces.PorudzbinaService;

@Document(indexName = "porudzbine")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class PorudzbinaDTO {
	
	@Id
	private Long porudzbinaId;
	@Field(type = FieldType.Integer)
	private Integer ocena;
	private List<StavkaDTO> stavke = new ArrayList<StavkaDTO>();
	@Field(type = FieldType.Text)
	private String komentar;
	private boolean anonimniKomentar;
	private boolean arhiviranikomentar;
	@Field(type = FieldType.Integer)
	private Double cena = 0.0;
	private String datum;
	
	private KupacDTO kupac;
	private ProdavacDTO prodavac;
	@Field(type = FieldType.Long)
	private Long prodavacId;
	
	public PorudzbinaDTO() {
		
	}
	
	public PorudzbinaDTO(Porudzbina porudzbina) {
		super();
		porudzbinaId = porudzbina.getId();
		ocena = porudzbina.getOcena();
		
		List<StavkaDTO> listDTO = new ArrayList<>();
		for (Stavka stavka : porudzbina.getStavke()) {
			listDTO.add(new StavkaDTO(stavka));
			cena = cena + stavka.getArtikal().getCena() * stavka.getKolicina();
		}
		stavke = listDTO;
		
		komentar = porudzbina.getKomentar();
		anonimniKomentar = porudzbina.isAnonimniKomentar();
		arhiviranikomentar = porudzbina.isArhiviranikomentar();
		datum = porudzbina.getSatnica().toString();
		kupac = new KupacDTO(porudzbina.getKupac());
		prodavac = new ProdavacDTO(porudzbina.getProdavac());
		prodavacId = prodavac.getId();
	}

	public Long getPorudzbinaId() {
		return porudzbinaId;
	}

	public void setPorudzbinaId(Long porudzbinaId) {
		this.porudzbinaId = porudzbinaId;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}

	public List<StavkaDTO> getStavke() {
		return stavke;
	}

	public void setStavke(List<StavkaDTO> stavke) {
		this.stavke = stavke;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public boolean isAnonimniKomentar() {
		return anonimniKomentar;
	}

	public void setAnonimniKomentar(boolean anonimniKomentar) {
		this.anonimniKomentar = anonimniKomentar;
	}

	public boolean isArhiviranikomentar() {
		return arhiviranikomentar;
	}

	public void setArhiviranikomentar(boolean arhiviranikomentar) {
		this.arhiviranikomentar = arhiviranikomentar;
	}

	public KupacDTO getKupac() {
		return kupac;
	}

	public void setKupac(KupacDTO kupac) {
		this.kupac = kupac;
	}

	public ProdavacDTO getProdavac() {
		return prodavac;
	}

	public void setProdavac(ProdavacDTO prodavac) {
		this.prodavac = prodavac;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public Long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(Long prodavacId) {
		this.prodavacId = prodavacId;
	}

	@Override
	public String toString() {
		return "PorudzbinaDTO [porudzbinaId=" + porudzbinaId + ", ocena=" + ocena + ", stavke=" + stavke + ", komentar="
				+ komentar + ", anonimniKomentar=" + anonimniKomentar + ", arhiviranikomentar=" + arhiviranikomentar
				+ ", cena=" + cena + ", datum=" + datum + ", kupac=" + kupac + ", prodavac=" + prodavac
				+ ", prodavacId=" + prodavacId + "]";
	}
}
