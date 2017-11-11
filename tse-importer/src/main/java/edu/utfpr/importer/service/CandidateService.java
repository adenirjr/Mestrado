package edu.utfpr.importer.service;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.Candidate;
import edu.utfpr.importer.persistence.provider.HibernateProvider;

public class CandidateService {

    private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

    public void save(final Candidate candidate) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(candidate);
            entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
