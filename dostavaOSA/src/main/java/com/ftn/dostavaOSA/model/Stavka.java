package com.ftn.dostavaOSA.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stavka")
public class Stavka {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "kolicina", nullable = false)
	private Integer kolicina;
	
	@OneToOne
	private Artikal artikal;
	
	public Stavka() {
		
	}

	public Stavka(Long id, Integer kolicina, Artikal artikal) {
		super();
		this.id = id;
		this.kolicina = kolicina;
		this.artikal = artikal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public Artikal getArtikal() {
		return artikal;
	}

	public void setArtikal(Artikal artikal) {
		this.artikal = artikal;
	}

	@Override
	public String toString() {
		return "Stavka [id=" + id + ", kolicina=" + kolicina + ", artikal=" + artikal + "]";
	}
	
	
}
