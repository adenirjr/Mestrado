package edu.utfpr.importer.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BIDDING_WINNER")
@AssociationOverrides({ @AssociationOverride(name = "pk.bidding", joinColumns = @JoinColumn(name = "bidid")),
		@AssociationOverride(name = "pk.entity", joinColumns = @JoinColumn(name = "documentnumber")) })
public class BiddingWinner {

	@EmbeddedId
	private BiddingWinnerId pk = new BiddingWinnerId();

	private String descItem; //dsItem
	private String amount; // nrQuantidadeVencedorLicitacao
	private String itemPrice; // vlLicitacaoVencedorLicitacao

	public BiddingWinnerId getPk() {
		return pk;
	}

	public void setPk(BiddingWinnerId pk) {
		this.pk = pk;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public String getDescItem() {
        return descItem;
    }

    public void setDescItem(String descItem) {
        this.descItem = descItem;
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
		BiddingWinner other = (BiddingWinner) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}
