package edu.utfpr.importer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "INDIVIDUAL")
public class Individual extends edu.utfpr.importer.model.Entity {

	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
