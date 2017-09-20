package edu.utfpr.importer.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BIDDING_PARTICIPANT")
@AssociationOverrides({ @AssociationOverride(name = "pk.bidding", joinColumns = @JoinColumn(name = "bidid")),
		@AssociationOverride(name = "pk.entity", joinColumns = @JoinColumn(name = "documentnumber")) })
public class BiddingParticipant {

	@EmbeddedId
	private BiddingParticipantId pk = new BiddingParticipantId();

	public BiddingParticipantId getPk() {
		return pk;
	}

	public void setPk(BiddingParticipantId pk) {
		this.pk = pk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		BiddingParticipant other = (BiddingParticipant) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

	// ADD MORE ATTRIBUTES IF NEEDED
}
