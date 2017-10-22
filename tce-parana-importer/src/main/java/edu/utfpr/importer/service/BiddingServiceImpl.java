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

	public List<Bidding> parseXML(final String path, final String fileName) throws Exception {
		return xmlParser.parseFile(path, fileName, BiddingSAXHandler.class);
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
			entityManager.merge(bidding);
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
