package it.bank.FabrickTest.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class BonificoResponse implements Serializable{

	private static final long serialVersionUID = 6996333027481246316L;
	
	private String code;
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
