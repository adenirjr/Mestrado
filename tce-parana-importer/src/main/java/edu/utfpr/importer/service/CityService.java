package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.City;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class CityService {

	private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

	public void save(final City city) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(city);
			entityManager.getTransaction().commit();
		} catch (RollbackException e) {
			entityManager.getTransaction().rollback();
		}
	}
}
