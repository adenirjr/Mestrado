package edu.utfpr.importer.xml.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.model.BiddingWinner;
import edu.utfpr.importer.model.BiddingWinnerId;
import edu.utfpr.importer.model.Corporate;
import edu.utfpr.importer.model.Individual;

public class BiddingWinnerSAXHandler extends GenericHandler<BiddingWinner> {

	private BiddingWinner biddingWinner;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("LicitacaoVencedor")) {
			biddingWinner = new BiddingWinner();
			biddingWinner.setPk(new BiddingWinnerId());
			
			final Bidding bidding = new Bidding();
			bidding.setBidId(attributes.getValue("idlicitacao"));
			biddingWinner.getPk().setBidding(bidding);
			
			biddingWinner.getPk().setItemNumber(attributes.getValue("nrItem"));
			biddingWinner.getPk().setLotNumber(attributes.getValue("nrLote"));
			biddingWinner.setAmount(Double.parseDouble(attributes.getValue("nrQuantidadeVencedorLicitacao")));
			biddingWinner.setItemPrice(Double.parseDouble(attributes.getValue("vlLicitacaoVencedorLicitacao")));
			biddingWinner.setDescItem(attributes.getValue("dsItem"));

			String documentNumber = attributes.getValue("nrDocumento");
			
			if(documentNumber != null) {
			    documentNumber = documentNumber.replace(".", "").replace("-", "").replace("/", "");
			}

			if (documentNumber.length() <= 11) {
				final Individual individualEntity = new Individual();
				individualEntity.setName(attributes.getValue("nmPessoa"));
				individualEntity.setDocumentNumber(documentNumber);
				biddingWinner.getPk().setEntity(individualEntity);
			} else {
				final Corporate corporateEntity = new Corporate();
				corporateEntity.setName(attributes.getValue("nmPessoa"));
				corporateEntity.setDocumentNumber(documentNumber);
				biddingWinner.getPk().setEntity(corporateEntity);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("LicitacaoVencedor")) {
			addParsedData(biddingWinner);
		}
	}
}
