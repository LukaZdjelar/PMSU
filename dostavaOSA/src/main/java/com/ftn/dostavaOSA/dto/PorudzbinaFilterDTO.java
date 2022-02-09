package com.ftn.dostavaOSA.dto;

public class PorudzbinaFilterDTO {

	private Integer ocenaOd;
	private Integer ocenaDo;
	private Integer cenaOd;
	private Integer cenaDo;
	private String komentar;
	private Long prodavacId;
	
	public PorudzbinaFilterDTO() {
		
	}

	public PorudzbinaFilterDTO(Integer ocenaOd, Integer ocenaDo, Integer cenaOd, Integer cenaDo, String komentar,
			Long prodavacId) {
		super();
		this.ocenaOd = ocenaOd;
		this.ocenaDo = ocenaDo;
		this.cenaOd = cenaOd;
		this.cenaDo = cenaDo;
		this.komentar = komentar;
		this.prodavacId = prodavacId;
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

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public Long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(Long prodavacId) {
		this.prodavacId = prodavacId;
	}

	@Override
	public String toString() {
		return "PorudzbinaFilterDTO [ocenaOd=" + ocenaOd + ", ocenaDo=" + ocenaDo + ", cenaOd=" + cenaOd + ", cenaDo="
				+ cenaDo + ", komentar=" + komentar + ", prodavacId=" + prodavacId + "]";
	}
}
