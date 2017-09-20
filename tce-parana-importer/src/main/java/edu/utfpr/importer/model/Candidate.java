package edu.utfpr.importer.model;

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
	@JoinColumn(name="cpf")
	private Individual individual;
	
	@OneToMany(mappedBy="candidate")
	private Set<Donation> donations;
	
	private String goods;
	private String job;
	private String party;
	private Boolean elected;
	private String scholaship;
	private String situation;
	private String state;
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public Boolean getElected() {
		return elected;
	}

	public void setElected(Boolean elected) {
		this.elected = elected;
	}

	public String getScholaship() {
		return scholaship;
	}

	public void setScholaship(String scholaship) {
		this.scholaship = scholaship;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}
}
