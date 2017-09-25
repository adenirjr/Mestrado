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

	//TODO MAPEAR DOAÇAO ENTRE CANDIDATOS	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Candidate candidate;

	@OneToOne
	@JoinColumn(name = "documentnumber")
	private Entity donator;

	private String transactionNumber; // Número do documento referente à
										// transação bancária ou comprovante da
										// doação estimável
	private String state; // Sigla da unidade da federação do doador, se
							// candidato ou direção partidária
	private String party; // Número do partido do doador, se candidato ou
							// direção partidária
	private String CNAE; // Código CNAE do doador, se pessoa jurídica
	private Date donationDate; // Data da doação declarada à Justiça Eleitoral
	private String incomeValue;
	private String incomeType; // Tipo da doação, podendo ser “Recursos
								// Próprios”, “Recursos de Pessoas Físicas”,
								// “Doações pela internet”, “Recursos de Outros
								// Candidatos”, “Recursos de Partidos
								// Políticos”, “Recursos de Origem Não
								// Identificada”, “Comercialização de bens ou
								// realização de eventos”, “Rendimentos de
								// aplicações financeiras”
	private String incomeSource; // Fonte do recurso da doação, podendo ser
									// “Fundo Partidário” ou “Outros Recursos”
	private String incomeFormat; // spécie do recurso da doação
	private String incomeDesc; // Descrição da doação quando esta for estimável
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
