package edu.utfpr.importer.persistence.provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateProvider {

	private static final HibernateProvider hibernateProvider = new HibernateProvider();
	private EntityManagerFactory entityManagerFactory;

	public static HibernateProvider getInstance() {
		return hibernateProvider;
	}

	private HibernateProvider() {
		createEntityManagerFactory();
	}

	public void createEntityManagerFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("IMPORTER_PERSISTENCE_UNIT");
	}

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public void close() {
		entityManagerFactory.close();
	}
}
