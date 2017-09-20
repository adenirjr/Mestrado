package edu.utfpr.webscrapper.service;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.utfpr.webscrapper.adapter.HTTPAdapter;

public class BiddingService {

    private static final String URL = "http://servicos.tce.pr.gov.br/TCEPR/Tribunal/Relacon/Dados/DadosConsulta/Consulta";
    private static final String FILE_URL = "http://servicos.tce.pr.gov.br/TCEPR/Tribunal/Relacon/Arquivos";

    private HTTPAdapter httpAdapter;

    public BiddingService(HTTPAdapter httpAdapter) {
        super();
        this.httpAdapter = httpAdapter;
    }

    /**
     * Find all IDs from the "Município" dropdown menu
     * 
     * @return List of IDs
     */
    public Map<String, String> findAll() {
        Document response = httpAdapter.doGet(URL);
        return parseResponse(response);
    }

    /**
     * Parse:
     * ...
     * <select id="idMunicipio" name="idMunicipio">
     *  <option value="0">Selecione</option>
     *  <option value="35">ABATIÁ                                  </option>
     *  <option value="77">ADRIANÓPOLIS                            </option>
     *  ...
     */
    private  Map<String, String> parseResponse(final Document response) {

        Map<String, String> entities = new TreeMap<String, String>();

        if (response != null) {
            Element element = response.getElementById("cdMunicipio");

            if (element != null) {
                for (Element child : element.children()) {
                    entities.put(child.text().trim(), child.attr("value").trim());
                }
            }
        }

        return Collections.unmodifiableMap(entities);
    }
    
    public byte[] getFileByNameAndYear(final String fileName, final String year) {
        Response response = httpAdapter.execute(FILE_URL + "/" + year + "/" + fileName);
        
        if(response == null) {
        	return new byte[0];
        }
        
        return response.bodyAsBytes();
    }

}
