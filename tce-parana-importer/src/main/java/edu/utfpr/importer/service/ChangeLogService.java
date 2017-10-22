package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import edu.utfpr.importer.model.ChangeLog;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class ChangeLogService {

    private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

    public void save(final ChangeLog changeLog) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(changeLog);
            entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public ChangeLog findByFile(final String file) {

        try {
            TypedQuery<ChangeLog> query = entityManager.createQuery("SELECT c FROM ChangeLog c WHERE c.file = :fileName", ChangeLog.class);

            query.setParameter("fileName", file);

            return (ChangeLog) query.getSingleResult();

        } catch (NoResultException nre) {
            // TODO
        }

        return null;
    }
}
