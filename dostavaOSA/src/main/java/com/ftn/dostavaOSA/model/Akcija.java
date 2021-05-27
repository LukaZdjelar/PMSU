package com.ftn.dostavaOSA.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "akcija")
public class Akcija {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "procenat", nullable = false)
	private Integer procenat;
	
	@Column(name = "odkad", nullable = false)
	private LocalDate odKad;
	
	@Column(name = "dokad", nullable = false)
	private LocalDate doKad;
	
	@Column(name = "tekst", nullable = false)
	private String tekst;
	
	public Akcija() {
		
	}

	public Akcija(Long id, Integer procenat, LocalDate odKad, LocalDate doKad, String tekst) {
		super();
		this.id = id;
		this.procenat = procenat;
		this.odKad = odKad;
		this.doKad = doKad;
		this.tekst = tekst;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProcenat() {
		return procenat;
	}

	public void setProcenat(Integer procenat) {
		this.procenat = procenat;
	}

	public LocalDate getOdKad() {
		return odKad;
	}

	public void setOdKad(LocalDate odKad) {
		this.odKad = odKad;
	}

	public LocalDate getDoKad() {
		return doKad;
	}

	public void setDoKad(LocalDate doKad) {
		this.doKad = doKad;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	@Override
	public String toString() {
		return "Akcija [id=" + id + ", procenat=" + procenat + ", odKad=" + odKad + ", doKad=" + doKad + ", tekst="
				+ tekst + "]";
	}
}
