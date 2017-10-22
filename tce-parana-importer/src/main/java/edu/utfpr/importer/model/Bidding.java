package edu.utfpr.importer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BIDDING")
public class Bidding {

	@Id
	private String bidId;

	@Column(length = 2000)
	private String description; // dsObjeto

	private String codeIBGE;
	private String entityName;
	private String year;
	private String descBiddingModel;
	private Date start; // dsNaturezaLicitacao
	private String evaluationCriteria; // dsAvaliacaoLicitacao
	private String classification; // dsClassificacaoObjetoLicitacao
	private String descExecution; // dsRegimeExecucaoLicitacao
	private Double total; // vlLicitacao

	@ManyToOne(optional = false)
	@JoinColumns({@JoinColumn(name="city"), @JoinColumn(name="state")})
	private City city;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.bidding")
	private Set<BiddingParticipant> participants = new HashSet<BiddingParticipant>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.bidding")
	private Set<BiddingWinner> winners = new HashSet<BiddingWinner>();

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public String getCodeIBGE() {
		return codeIBGE;
	}

	public void setCodeIBGE(String codeIBGE) {
		this.codeIBGE = codeIBGE;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescBiddingModel() {
		return descBiddingModel;
	}

	public void setDescBiddingModel(String descBiddingModel) {
		this.descBiddingModel = descBiddingModel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getEvaluationCriteria() {
		return evaluationCriteria;
	}

	public void setEvaluationCriteria(String evaluationCriteria) {
		this.evaluationCriteria = evaluationCriteria;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<BiddingParticipant> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<BiddingParticipant> participants) {
		this.participants = participants;
	}
	
	public Set<BiddingWinner> getWinners() {
		return winners;
	}

	public void setWinners(Set<BiddingWinner> winners) {
		this.winners = winners;
	}

    public String getDescExecution() {
        return descExecution;
    }

    public void setDescExecution(String descExecution) {
        this.descExecution = descExecution;
    }

    public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidId == null) ? 0 : bidId.hashCode());
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
		Bidding other = (Bidding) obj;
		if (bidId == null) {
			if (other.bidId != null)
				return false;
		} else if (!bidId.equals(other.bidId))
			return false;
		return true;
	}
}
