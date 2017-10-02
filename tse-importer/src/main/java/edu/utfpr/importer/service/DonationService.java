package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.Donation;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class DonationService {

	private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();
	
	private final EntityService entityService = new EntityService();
	private final CandidateService candidateService = new CandidateService();

	public void save(final Donation donation) {
		try {
		    entityService.save(donation.getCandidate().getId().getIndividual());
		    entityService.save(donation.getDonator());
		    entityService.save(donation.getCandidate().getVice());
		    entityService.save(donation.getCandidate().getCorporate());
		    
		    candidateService.save(donation.getCandidate());
		    
			entityManager.getTransaction().begin();
			entityManager.persist(donation);
			entityManager.getTransaction().commit();
			
		} catch (RollbackException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
