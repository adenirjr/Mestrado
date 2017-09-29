package edu.utfpr.importer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "INDIVIDUAL")
public class Individual extends edu.utfpr.importer.model.Entity {

	private String gender;
	
	public Individual() {
		// TODO Auto-generated constructor stub
	}
	
	public Individual(final String docNumber, final String name) {
		this.setDocumentNumber(docNumber);
		this.setName(name);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
