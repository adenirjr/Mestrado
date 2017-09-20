package edu.utfpr.webscrapper.application;

import java.util.Map;
import java.util.TreeMap;

import edu.utfpr.webscrapper.adapter.HTTPAdapter;
import edu.utfpr.webscrapper.service.EntityService;

/**
 *
 */
public class EntityScrapper {

    private static final String YEAR = "2017";
    private final EntityService entityService = new EntityService(new HTTPAdapter());

    public static void main(String[] args) {
        EntityScrapper app = new EntityScrapper();
        app.init();
    }

    private void init() {

        final Long startTime = System.currentTimeMillis();
        
        Map<String, Map<String, String>> entitiesData = new TreeMap<String, Map<String, String>>();
        Map<String, String> entities = entityService.findAll();

        int counter = 0;
        for (Map.Entry<String, String> entity : entities.entrySet()) {
            
            if ("0".equals(entity.getValue())) {
                continue; //Skip "Selecione" option
            }
            
            Map<String, String> attributes = entityService.getAttributesByidMunicipioAndYear(entity.getValue(), YEAR);
            entitiesData.put(entity.getKey(), attributes);
            counter++;

            if (counter > 10) {
                break;
            }
        }

        printCSV(entitiesData);
        
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Total Municípios : " + counter + " Tempo: " + estimatedTime/1000 + " Segundos" );
    }

    private void printPretty(Map<String, Map<String, String>> data) {
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> item : data.entrySet()) {
            sb.append(item.getKey()).append("\n");
            for (Map.Entry<String, String> attr : item.getValue().entrySet()) {
                sb.append("    ").append(attr.getKey()).append(" : ").append(attr.getValue()).append("\n");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private void printCSV(Map<String, Map<String, String>> data) {
        final StringBuilder sb = new StringBuilder();

        //Header
        for (Map.Entry<String, Map<String, String>> item : data.entrySet()) {
            sb.append("Município").append(";");
            for (Map.Entry<String, String> attr : item.getValue().entrySet()) {
                sb.append(attr.getKey()).append(";");
            }
            sb.append("\n");
            break;
        }
        
        //Instances
        for (Map.Entry<String, Map<String, String>> item : data.entrySet()) {
            sb.append(item.getKey()).append(";");
            for (Map.Entry<String, String> attr : item.getValue().entrySet()) {
                final String value = format(attr.getValue());
                
                sb.append(value).append(";");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
    
    private String format(final String str) {
        return str.replace(".", "").replace("R$", "").replace("%", "");
    }
}
