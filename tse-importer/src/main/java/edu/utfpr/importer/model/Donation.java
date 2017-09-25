package edu.utfpr.importer.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "DONATION")
public class Donation {

	//TODO MAPEAR DOA�AO ENTRE CANDIDATOS	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "documentnumber")
	private Entity donator;

	private String transactionNumber; // N�mero do documento referente �
										// transa��o banc�ria ou comprovante da
										// doa��o estim�vel
	private String state; // Sigla da unidade da federa��o do doador, se
							// candidato ou dire��o partid�ria
	private String party; // N�mero do partido do doador, se candidato ou
							// dire��o partid�ria
	private String CNAE; // C�digo CNAE do doador, se pessoa jur�dica
	private Date donationDate; // Data da doa��o declarada � Justi�a Eleitoral
	private String incomeValue;
	private String incomeType; // Tipo da doa��o, podendo ser �Recursos
								// Pr�prios�, �Recursos de Pessoas F�sicas�,
								// �Doa��es pela internet�, �Recursos de Outros
								// Candidatos�, �Recursos de Partidos
								// Pol�ticos�, �Recursos de Origem N�o
								// Identificada�, �Comercializa��o de bens ou
								// realiza��o de eventos�, �Rendimentos de
								// aplica��es financeiras�
	private String incomeSource; // Fonte do recurso da doa��o, podendo ser
									// �Fundo Partid�rio� ou �Outros Recursos�
	private String incomeFormat; // sp�cie do recurso da doa��o
	private String incomeDesc; // Descri��o da doa��o quando esta for estim�vel
								// em dinheiro

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
