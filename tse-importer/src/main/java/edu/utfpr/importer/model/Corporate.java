package edu.utfpr.importer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CORPORATE")
public class Corporate extends edu.utfpr.importer.model.Entity {

	private String title;
	private String status;
	private Date openingDate;
	
	public Corporate() {
		// TODO Auto-generated constructor stub
	}
	
	public Corporate(final String docNumber, final String name) {
		this.setDocumentNumber(docNumber);
		this.setName(name);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDocumentNumber() == null) ? 0 : getDocumentNumber().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corporate other = (Corporate) obj;
		if (getDocumentNumber() == null) {
			if (other.getDocumentNumber() != null)
				return false;
		} else if (!getDocumentNumber().equals(other.getDocumentNumber()))
			return false;
		return true;
	}

}
