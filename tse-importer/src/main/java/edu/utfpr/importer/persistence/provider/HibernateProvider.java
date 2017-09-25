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
		entityManagerFactory = Persistence.createEntityManagerFactory("IMPORTER_PERSISTENCE_UNIT");
	}
	
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	@Override
	protected void finalize() throws Throwable {
		entityManagerFactory.close();
	}
}
