package edu.utfpr.importer.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.parser.SAXParser;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.xml.handler.BiddingSAXHandler;

public class BiddingServiceImpl implements BiddingService<Bidding>{

	private final CityService cityService = new CityService();
	private final SAXParser<Bidding> xmlParser = new SAXParser<Bidding>();
	
	private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

	public void saveXMLContent(final String path, final String fileName) {

		List<Bidding> biddings = xmlParser.parseFile(path, fileName, BiddingSAXHandler.class);

		if (biddings != null) {
			save(biddings);
		}
	}

	public void save(final List<Bidding> biddings) {
		for (Bidding bidding : biddings) {
			save(bidding);
		}
	}

	public void save(final Bidding bidding) {
		try {
			cityService.save(bidding.getCity());

			entityManager.getTransaction().begin();
			entityManager.persist(bidding);
			entityManager.getTransaction().commit();
			
		} catch (RollbackException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		entityManager.close();
	}
	
	@Override
	public String toString() {
		return "Bidding";
	}
}
