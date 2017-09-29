package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.Donation;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class DonationService {

	private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

	public void save(final Donation donation) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(donation);
			entityManager.getTransaction().commit();
		} catch (RollbackException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
