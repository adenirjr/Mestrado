package edu.utfpr.webscrapper.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import edu.utfpr.webscrapper.adapter.HTTPAdapter;
import edu.utfpr.webscrapper.service.BiddingService;

/**
 *
 */
public class BiddingScrapper {

    private static final String YEAR = "2013";
    private static final String SAVE_TO = "C:/Users/Adenir/Downloads/licitacoes/";

    private final BiddingService openDataService = new BiddingService(new HTTPAdapter());

    public static void main(String[] args) {
        BiddingScrapper app = new BiddingScrapper();
        app.init();
    }

    private void init() {

        final Long startTime = System.currentTimeMillis();

        final Map<String, String> entities = openDataService.findAll();

        int counter = 0;
        for (Map.Entry<String, String> entity : entities.entrySet()) {

            if ("0".equals(entity.getValue())) {
                continue; // Skip "Selecione" option
            }

            counter++;

            /*if (counter > 10) {
                break;
            }*/
            
            final String fileName = getFileName(entity.getValue());
            final String absolutePath = SAVE_TO + fileName;
            
            if(isExistingFile(absolutePath)) {
            	 System.out.println("File already downloaded [" + fileName + "]. Skipping. ");
            	continue;
            }
            
            System.out.print("Saving [" + fileName + "] .... ");
            
            saveFile(openDataService.getFileByNameAndYear(fileName, YEAR), absolutePath);
            
            System.out.println("OK");
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Total arquivos : " + counter + " Tempo: " + estimatedTime / 1000 + " Segundos");
    }
    
    private Boolean isExistingFile(final String path) {
    	File file = new File(path);
    	return file.exists();
    }

    private String getFileName(final String cdMunicipio) {
        final StringBuilder fileName = new StringBuilder(); // File Name format "<year>_41<cdmunicipio>_Licitacao.zip"

        fileName.append(YEAR).append("_41").append(cdMunicipio.substring(0, 4)).append("_Licitacao.zip");

        return fileName.toString();
    }

    private void saveFile(final byte[] downloadedFile, final String filePath) {
        try {
            FileOutputStream out = (new FileOutputStream(new File(filePath)));
            out.write(downloadedFile);
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
