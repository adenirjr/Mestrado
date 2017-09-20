package edu.utfpr.importer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class BiddingWinnerId implements Serializable {

	private static final long serialVersionUID = 250198607687427501L;

	@ManyToOne
	private Bidding bidding;

	@ManyToOne
	private Entity entity;

	public Bidding getBidding() {
		return bidding;
	}

	public void setBidding(Bidding bidding) {
		this.bidding = bidding;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidding == null) ? 0 : bidding.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
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
		BiddingWinnerId other = (BiddingWinnerId) obj;
		if (bidding == null) {
			if (other.bidding != null)
				return false;
		} else if (!bidding.equals(other.bidding))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		return true;
	}
}
