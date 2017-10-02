package edu.utfpr.importer.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "DONATION")
public class Donation {

	//TODO MAPEAR DOACAO ENTRE CANDIDATOS	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumns({@JoinColumn(name="candidatedocumentnumber"), @JoinColumn(name="year")})
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "donatordocumentnumber")
	private Entity donator;

	private String transactionNumber; 
										
	private String state; 
	private String party; 
	private String CNAE; 
	private Date donationDate; 
	private String incomeValue;
	private String incomeType; 
	private String incomeSource;
								
	private String incomeFormat; 
	private String incomeDesc; 
								

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Entity getDonator() {
		return donator;
	}

	public void setDonator(Entity donator) {
		this.donator = donator;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getCNAE() {
		return CNAE;
	}

	public void setCNAE(String cNAE) {
		CNAE = cNAE;
	}

	public Date getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

	public String getIncomeValue() {
		return incomeValue;
	}

	public void setIncomeValue(String incomeValue) {
		this.incomeValue = incomeValue;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public String getIncomeFormat() {
		return incomeFormat;
	}

	public void setIncomeFormat(String incomeFormat) {
		this.incomeFormat = incomeFormat;
	}

	public String getIncomeDesc() {
		return incomeDesc;
	}

	public void setIncomeDesc(String incomeDesc) {
		this.incomeDesc = incomeDesc;
	}
}
