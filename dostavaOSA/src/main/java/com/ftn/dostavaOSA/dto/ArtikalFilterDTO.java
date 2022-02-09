package com.ftn.dostavaOSA.dto;

public class ArtikalFilterDTO {

	private String naziv;
	private String opis;
	private Integer cenaOd;
	private Integer cenaDo;
	private Integer ocenaOd;
	private Integer ocenaDo;
	private Integer brojOcenaOd;
	private Integer brojOcenaDo;
	private long prodavacId;

	public ArtikalFilterDTO() {

	}

	public ArtikalFilterDTO(String naziv, String opis, Integer cenaOd, Integer cenaDo, Integer ocenaOd, Integer ocenaDo,
			Integer brojOcenaOd, Integer brojOcenaDo, long prodavacId) {
		super();
		this.naziv = naziv;
		this.opis = opis;
		this.cenaOd = cenaOd;
		this.cenaDo = cenaDo;
		this.ocenaOd = ocenaOd;
		this.ocenaDo = ocenaDo;
		this.brojOcenaOd = brojOcenaOd;
		this.brojOcenaDo = brojOcenaDo;
		this.prodavacId = prodavacId;
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

	public Integer getCenaOd() {
		return cenaOd;
	}

	public void setCenaOd(Integer cenaOd) {
		this.cenaOd = cenaOd;
	}

	public Integer getCenaDo() {
		return cenaDo;
	}

	public void setCenaDo(Integer cenaDo) {
		this.cenaDo = cenaDo;
	}

	public Integer getOcenaOd() {
		return ocenaOd;
	}

	public void setOcenaOd(Integer ocenaOd) {
		this.ocenaOd = ocenaOd;
	}

	public Integer getOcenaDo() {
		return ocenaDo;
	}

	public void setOcenaDo(Integer ocenaDo) {
		this.ocenaDo = ocenaDo;
	}

	public Integer getBrojOcenaOd() {
		return brojOcenaOd;
	}

	public void setBrojOcenaOd(Integer brojOcenaOd) {
		this.brojOcenaOd = brojOcenaOd;
	}

	public Integer getBrojOcenaDo() {
		return brojOcenaDo;
	}

	public void setBrojOcenaDo(Integer brojOcenaDo) {
		this.brojOcenaDo = brojOcenaDo;
	}

	public long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(long prodavacId) {
		this.prodavacId = prodavacId;
	}

	@Override
	public String toString() {
		return "ArtikalFilterDTO [naziv=" + naziv + ", opis=" + opis + ", cenaOd=" + cenaOd + ", cenaDo=" + cenaDo
				+ ", ocenaOd=" + ocenaOd + ", ocenaDo=" + ocenaDo + ", brojOcenaOd=" + brojOcenaOd + ", brojOcenaDo="
				+ brojOcenaDo + ", prodavacId=" + prodavacId + "]";
	}
}
