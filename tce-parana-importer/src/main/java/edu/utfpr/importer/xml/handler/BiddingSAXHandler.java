package edu.utfpr.importer.xml.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.model.City;
import edu.utfpr.importer.model.CityId;

public class BiddingSAXHandler extends GenericHandler<Bidding> {

	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    private Bidding bid;
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Licitacao")) {
            bid = new Bidding();
            
            final City city = new City();
            city.setId(new CityId());
            
            city.getId().setName(attributes.getValue("nmMunicipio") != null ? attributes.getValue("nmMunicipio").trim() : null);
            city.getId().setState("PR"); //TODO FIX
            
            bid.setCity(city);
            bid.setBidId(attributes.getValue("idLicitacao"));
            bid.setCodeIBGE(attributes.getValue("cdIBGE"));
            bid.setEntityName(attributes.getValue("nmEntidade"));
            bid.setYear(attributes.getValue("nrAnoLicitacao"));
            bid.setDescBiddingModel(attributes.getValue("dsModalidadeLicitacao"));
            bid.setDescription(attributes.getValue("dsObjeto"));
            bid.setEvaluationCriteria(attributes.getValue("dsAvaliacaoLicitacao"));
            bid.setClassification(attributes.getValue("dsClassificacaoObjetoLicitacao"));
            bid.setDescExecution(attributes.getValue("dsRegimeExecucaoLicitacao"));
            bid.setTotal(Double.parseDouble(attributes.getValue("vlLicitacao")));
            
            try {
				bid.setStart(FORMAT_DATE.parse(attributes.getValue("dtAbertura")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Licitacao")) {
        	addParsedData(bid);
		}
	}
}
