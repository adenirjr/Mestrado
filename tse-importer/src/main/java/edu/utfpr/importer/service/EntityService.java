package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.Entity;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class EntityService {

    private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

    public void save(final Entity entity) {
        try {

            if (entity == null) {
                return;
            }

            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
        }
    }
}
