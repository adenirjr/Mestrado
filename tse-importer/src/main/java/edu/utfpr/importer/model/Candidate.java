package edu.utfpr.importer.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CANDIDATE")
public class Candidate {

	@EmbeddedId
	private CandidateId id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "vicedocumentnumber")
	private Individual vice;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="corporatedocumentnumber")
	private Corporate corporate; // CNPJ do candidato

	@OneToMany(mappedBy = "candidate")
	private Set<Donation> donations;

	private String electionCode; // Código identificador da Eleição
	private String electionDesc; // Descrição da Eleição
	private Date creationDateTime; // Data e hora de geração da informação
	private String candidateSeq; // Sequencial do candidato na base de dados da
								// Justiça Eleitoral
	private String state; // Unidade da Federação do candidato
	private String city; // Município de registro da candidatura
	private String party; // Sigla da legenda partidária do candidato registrado
							// na Justiça Eleitoral
	private String candidateCode; // Número do candidato registrado na Justiça
									// Eleitoral
	private String role; // Candidatura a qual concorre o candidato registrado
							// na Justiça Eleitoral.

	public Individual getVice() {
		return vice;
	}

	public CandidateId getId() {
		return id;
	}

	public void setId(CandidateId id) {
		this.id = id;
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

	public String getElectionCode() {
		return electionCode;
	}

	public void setElectionCode(String electionCode) {
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

	public String getCandidateSeq() {
		return candidateSeq;
	}

	public void setCandidateSeq(String candidateSeq) {
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
