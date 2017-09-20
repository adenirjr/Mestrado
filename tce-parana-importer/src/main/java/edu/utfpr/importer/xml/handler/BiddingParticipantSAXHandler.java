package edu.utfpr.importer.xml.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.model.BiddingParticipant;
import edu.utfpr.importer.model.BiddingParticipantId;
import edu.utfpr.importer.model.Corporate;
import edu.utfpr.importer.model.Individual;

public class BiddingParticipantSAXHandler extends GenericHandler<BiddingParticipant> {

	private BiddingParticipant biddingParticipant;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("LicitacaoParticipante")) {
			biddingParticipant = new BiddingParticipant();
			biddingParticipant.setPk(new BiddingParticipantId());
			
			final Bidding bidding = new Bidding();
			bidding.setBidId(attributes.getValue("idLicitacao"));
			biddingParticipant.getPk().setBidding(bidding);

			final String documentType = attributes.getValue("sgDocParticipanteLicitacao");
			final String documentNumber = attributes.getValue("nrDocParticipanteLicitacao");

			if ("CPF".equals(documentType)) {
				final Individual individualEntity = new Individual();
				individualEntity.setName(attributes.getValue("nmParticipanteLicitacao"));
				individualEntity.setDocumentNumber(documentNumber.replace(".", "").replace("-", ""));
				biddingParticipant.getPk().setEntity(individualEntity);
			} else {
				final Corporate corporateEntity = new Corporate();
				corporateEntity.setName(attributes.getValue("nmParticipanteLicitacao"));
				corporateEntity.setDocumentNumber(documentNumber.replace(".", "").replace("-", "").replace("/", ""));
				biddingParticipant.getPk().setEntity(corporateEntity);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("LicitacaoParticipante")) {
			addParsedData(biddingParticipant);
		}
	}
}
