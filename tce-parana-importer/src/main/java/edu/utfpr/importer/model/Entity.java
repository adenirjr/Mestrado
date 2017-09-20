package edu.utfpr.importer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@javax.persistence.Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Entity {

	@Id
	private String documentNumber;
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.entity")
	private Set<BiddingParticipant> biddingParticipations = new HashSet<BiddingParticipant>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.entity")
	private Set<BiddingWinner> biddingWins = new HashSet<BiddingWinner>();

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BiddingParticipant> getBiddingParticipations() {
		return biddingParticipations;
	}

	public void setBiddingParticipations(Set<BiddingParticipant> biddingParticipations) {
		this.biddingParticipations = biddingParticipations;
	}

	public Set<BiddingWinner> getBiddingWins() {
		return biddingWins;
	}

	public void setBiddingWins(Set<BiddingWinner> biddingWins) {
		this.biddingWins = biddingWins;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
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
		Entity other = (Entity) obj;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		return true;
	}

}
