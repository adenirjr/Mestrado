package edu.utfpr.importer.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CANDIDATE")
public class Candidate {

	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cpf")
	private Individual individual;

	private Individual vice;

	private Corporate corporate; // CNPJ do candidato

	@OneToMany(mappedBy = "candidate")
	private Set<Donation> donations;

	private Long electionCode; // Código identificador da Eleição
	private String electionDesc; // Descrição da Eleição
	private Date creationDateTime; // Data e hora de geração da informação
	private Long candidateSeq; // Sequencial do candidato na base de dados da
								// Justiça Eleitoral
	private String state; // Unidade da Federação do candidato
	private String city; // Município de registro da candidatura
	private String party; // Sigla da legenda partidária do candidato registrado
							// na Justiça Eleitoral
	private String candidateCode; // Número do candidato registrado na Justiça
									// Eleitoral
	private String role; // Candidatura a qual concorre o candidato registrado
							// na Justiça Eleitoral.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public Individual getVice() {
		return vice;
	}

	public void setVice(Individual vice) {
		this.vice = vice;
	}

	public Corporate getCorporate() {
		return corporate;
	}

	public void setCorporate(Corporate corporate) {
		this.corporate = corporate;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	public Long getElectionCode() {
		return electionCode;
	}

	public void setElectionCode(Long electionCode) {
		this.electionCode = electionCode;
	}

	public String getElectionDesc() {
		return electionDesc;
	}

	public void setElectionDesc(String electionDesc) {
		this.electionDesc = electionDesc;
	}

	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public Long getCandidateSeq() {
		return candidateSeq;
	}

	public void setCandidateSeq(Long candidateSeq) {
		this.candidateSeq = candidateSeq;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getCandidateCode() {
		return candidateCode;
	}

	public void setCandidateCode(String candidateCode) {
		this.candidateCode = candidateCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
