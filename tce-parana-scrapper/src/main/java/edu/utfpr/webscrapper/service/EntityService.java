package edu.utfpr.webscrapper.service;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.utfpr.webscrapper.adapter.HTTPAdapter;

public class EntityService {

    private static final String URL_ENTITY = "https://servicos.tce.pr.gov.br/TCEPR/Tribunal/Relacon/Entidade/EntidadeConsulta/Entidade";
    private static final String URL = "https://servicos.tce.pr.gov.br/TCEPR/Tribunal/Relacon/Entidade";

    private HTTPAdapter httpAdapter;

    public EntityService(HTTPAdapter httpAdapter) {
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
            Element element = response.getElementById("idMunicipio");

            if (element != null) {
                for (Element child : element.children()) {
                    entities.put(child.text().trim(), child.attr("value").trim());
                }
            }
        }

        return Collections.unmodifiableMap(entities);
    }

    /**
     * @param idMunicipio
     * @param nrAno
     * @return Map<Attribute, Value>
     */
    public Map<String, String> getAttributesByidMunicipioAndYear(final String idMunicipio, final String nrAno) {
        final String idPessoa = findIdPessoa(idMunicipio);
        return this.new Attributes().retrieveAttributes(idMunicipio, idPessoa, nrAno);
    }

    /**
     * Get Entity ID from the "Entidade" dropdown menu
     * 
     * Parse:
     * <label class="control-label" for="idPessoa">Entidade</label>
     * <select class="form-control" id="idPessoa" name="idPessoa"><option value="0">Selecione</option>
     * <option value="12176">MUNIC&#205;PIO DE ADRIAN&#211;POLIS</option>    <<<<<<---------
     * <option value="9685">C&#194;MARA MUNICIPAL DE ADRIAN&#211;POLIS</option>
     * <option value="14485">FUNDO MUNICIPAL DE SAUDE DE ADRIANOPOLIS</option>
     * <option value="140981">INSTITUTO DE PREVID&#202;NCIA MUNICIPAL DE ADRIAN&#211;POLIS</option>
     * </select>
     * 
     * @param idMunicipio
     * @return Entity ID
     */
    private String findIdPessoa(final String idMunicipio) {
        final Map<String, String> params = new TreeMap<String, String>();
        params.put("idMunicipio", idMunicipio);

        final Document document = httpAdapter.doPost(URL_ENTITY, params);
        if (document != null) {
            return document.getElementsByTag("option").get(1).attr("value").trim();
        }

        return "";
    }

    private class Attributes {
        private static final String URL_ATTR = "https://servicos.tce.pr.gov.br/TCEPR/Tribunal/Relacon/Entidade/EntidadeConsulta/Consulta";

        public Map<String, String> retrieveAttributes(final String idMunicipio, final String idPessoa, final String nrAno) {
            final Map<String, String> params = new TreeMap<String, String>();
            params.put("idMunicipio", idMunicipio);
            params.put("idPessoa", idPessoa);
            params.put("nrAno", nrAno);

            Document response = findByParam(params);
            return parseResponse(response, nrAno);
        }

        private Document findByParam(final Map<String, String> params) {
            return httpAdapter.doPost(URL_ATTR, params);
        }

        /**
         * Parse: ... <tbody>
         * <tr>
         * <td>População Estimada</td>
         * <td style="text-align: right;">7.823 <i class="fa fa-long-arrow-down red"></i></td>
         * <td style="text-align: right;">9.705</td>
         * </tr>
         * ...
         * 
         * @param response
         * @return Map<attribute, value>
         */
        private Map<String, String> parseResponse(final Document response, final String year) {

            final Map<String, String> attributes = new TreeMap<String, String>();

            if (response != null) {
                attributes.put("Exercício", year);
                attributes.put("População Estimada", extractAttribute("População Estimada", response));
                attributes.put("População Censitária Urbana", extractAttribute("População Censitária Urbana", response));
                attributes.put("População Censitária Rural", extractAttribute("População Censitária Rural", response));
                attributes.put("PIB per capita", extractAttribute("PIB per capita", response));
                attributes.put("PIB a Preços Correntes", extractAttribute("PIB a Preços Correntes", response));
                attributes.put("IDH-M Taxa de Alfabetização", extractAttribute("IDH-M Taxa de Alfabetização", response));
                attributes.put("IDM-M Expectativa de Vida ao Nascer (anos)", extractAttribute("IDM-M Expectativa de Vida ao Nascer (anos)", response));
                attributes.put("Índice de Gini", extractAttribute("Índice de Gini", response));
                attributes.put("Taxa de Pobreza", extractAttribute("Taxa de Pobreza", response));
                attributes.put("Índice de Eficácia da Educação Municipal", extractAttribute("Índice de Eficácia da Educação Municipal", response));
                attributes.put("Índice de Eficiência da Despesa em Educação", extractAttribute("Índice de Eficiência da Despesa em Educação", response));
            }

            return Collections.unmodifiableMap(attributes);
        }

        private String extractAttribute(final String name, final Document response) {
            Elements elements = response.getElementsMatchingText(name);

            if (elements != null && elements.size() > 0) {
                Element element = elements.get(elements.size() - 1);

                return element.siblingElements().get(0).text();
            }

            return "";
        }
    }

}
