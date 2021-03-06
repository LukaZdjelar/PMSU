package com.ftn.dostavaOSA.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ftn.dostavaOSA.dto.PorudzbinaKorpaDTO;

@Entity
@Table(name="porudzbina")
public class Porudzbina {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, unique=true)
	private Long id;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Stavka> stavke;
	
	@Column(name="satnica")
	private LocalDate satnica;
	
	@Column(name="dostavljeno")
	private boolean dostavljeno;
	
	@Column(name="ocena")
	private Integer ocena;
	
	@Column(name="komentar")
	private String komentar;
	
	@Column(name="anonimnikomentar")
	private boolean anonimniKomentar;
	
	@Column(name="arhiviranikomentar")
	private boolean arhiviranikomentar;
	
	@ManyToOne
	private Kupac kupac;
	
	@ManyToOne
	private Prodavac prodavac;
	
	public Porudzbina() {
		
	}

	public Porudzbina(Long id, List<Stavka> stavke, LocalDate satnica, boolean dostavljeno, Integer ocena,
			String komentar, boolean anonimniKomentar, boolean arhiviranikomentar, Kupac kupac, Prodavac prodavac) {
		super();
		this.id = id;
		this.stavke = stavke;
		this.satnica = satnica;
		this.dostavljeno = dostavljeno;
		this.ocena = ocena;
		this.komentar = komentar;
		this.anonimniKomentar = anonimniKomentar;
		this.arhiviranikomentar = arhiviranikomentar;
		this.kupac = kupac;
		this.prodavac = prodavac;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Stavka> getStavke() {
		return stavke;
	}

	public void setStavke(List<Stavka> stavke) {
		this.stavke = stavke;
	}

	public LocalDate getSatnica() {
		return satnica;
	}

	public void setSatnica(LocalDate satnica) {
		this.satnica = satnica;
	}

	public boolean isDostavljeno() {
		return dostavljeno;
	}

	public void setDostavljeno(boolean dostavljeno) {
		this.dostavljeno = dostavljeno;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
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

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Prodavac getProdavac() {
		return prodavac;
	}

	public void setProdavac(Prodavac prodavac) {
		this.prodavac = prodavac;
	}

	@Override
	public String toString() {
		return "Porudzbina [id=" + id + ", stavke=" + stavke + ", satnica=" + satnica + ", dostavljeno=" + dostavljeno
				+ ", ocena=" + ocena + ", komentar=" + komentar + ", anonimniKomentar=" + anonimniKomentar
				+ ", arhiviranikomentar=" + arhiviranikomentar + ", kupac=" + kupac + ", prodavac=" + prodavac + "]";
	}
}
