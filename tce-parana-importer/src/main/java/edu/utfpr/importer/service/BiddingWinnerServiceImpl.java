package edu.utfpr.importer.service;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utfpr.importer.model.BiddingWinner;
import edu.utfpr.importer.parser.SAXParser;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.xml.handler.BiddingWinnerSAXHandler;

public class BiddingWinnerServiceImpl implements BiddingService<BiddingWinner> {

	private final SAXParser<BiddingWinner> xmlParser = new SAXParser<BiddingWinner>();
	private final EntityManager entityManager = HibernateProvider.getInstance().getEntityManager();

	private final EntityService entityService = new EntityService();

	public void saveXMLContent(final String path, final String fileName) {
		List<BiddingWinner> biddings = xmlParser.parseFile(path, fileName, BiddingWinnerSAXHandler.class);

		if (biddings != null) {
			save(biddings);
		}
	}

	public void save(final List<BiddingWinner> winners) {
		for (BiddingWinner winner : winners) {
			save(winner);
		}
	}

	public void save(final BiddingWinner winner) {
		try {
			entityService.save(winner.getPk().getEntity());

			entityManager.getTransaction().begin();
			entityManager.persist(winner);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Bidding: " + winner.getPk().getBidding().getBidId() + " docNumber: "
					+ winner.getPk().getEntity().getDocumentNumber() + " nrItem : " + winner.getPk().getItemNumber());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		entityManager.close();
	}
	
	@Override
	public String toString() {
		return "Winners";
	}
}
