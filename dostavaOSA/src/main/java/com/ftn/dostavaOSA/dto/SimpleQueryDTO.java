package com.ftn.dostavaOSA.dto;

public class SimpleQueryDTO {

	private String field;
	private String valueString;
	private Long valueLong;
	
	public SimpleQueryDTO() {
		
	}

	public SimpleQueryDTO(String field, String valueString) {
		super();
		this.field = field;
		this.valueString = valueString;
	}

	public SimpleQueryDTO(String field, Long valueLong) {
		super();
		this.field = field;
		this.valueLong = valueLong;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public Long getValueLong() {
		return valueLong;
	}

	public void setValueLong(Long valueLong) {
		this.valueLong = valueLong;
	}

	@Override
	public String toString() {
		return "SimpleQueryDTO [field=" + field + ", valueString=" + valueString + ", valueLong=" + valueLong + "]";
	}
	
}
