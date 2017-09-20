package edu.utfpr.importer.application;

import java.io.File;

import edu.utfpr.importer.helper.FileHelper;
import edu.utfpr.importer.service.BiddingParticipantService;
import edu.utfpr.importer.service.BiddingService;

/**
 * Unpack files from FILE_DIRECTORY into UNZIPPED_FILES_DIRECTORY. Parse data from TARGET_UNZIPPED_FILE and save into database.
 */
public class BiddingProcessor {

    private static final String FILE_DIRECTORY = "C:/Users/Adenir/Downloads/licitacoes/2014";
    private static final String UNZIPPED_FILES_DIRECTORY = "/../Temp";
    private static final String BIDDING_UNZIPPED_FILE = "Licitacao.xml";
    private static final String PARTICIPANTS_UNZIPPED_FILE = "LicitacaoParticipante.xml";
    
    private final BiddingService biddingService = new BiddingService();
    private final BiddingParticipantService biddingParticipantService = new BiddingParticipantService();
    

    public static void main(String[] args) {
        BiddingProcessor reader = new BiddingProcessor();
        reader.init();
    }

    public void init() {
        final Long startTime = System.currentTimeMillis();
        File dir = new File(FILE_DIRECTORY);
        
        int counter = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                
                if(file.isDirectory()) {
                    continue;
                }
                
                System.out.print("Processing " + file.getName() + " (" + counter + " of " + dir.listFiles().length + ") ... ");
                
                final String unzippedFilesDir = FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY;
                
                FileHelper.cleanDirectory(unzippedFilesDir);
                
                FileHelper.unzipfile(unzippedFilesDir, file);
                
                processBidding(unzippedFilesDir, BIDDING_UNZIPPED_FILE);
                processParticipants(unzippedFilesDir, PARTICIPANTS_UNZIPPED_FILE);
                
                FileHelper.cleanDirectory(FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY);
                
                System.out.println("OK");
                counter++;
            }
        }
        
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Tempo: " + estimatedTime / 1000 + " Segundos");
    }
    
    private void processBidding(final String path, final String fileName) {
    	System.out.print("Saving Bidding .... ");
    	final String targetXMLFile = FileHelper.getTargetFileName(path, fileName);
        FileHelper.clearEntityReferences(path, targetXMLFile);
        biddingService.saveXMLContent(path, targetXMLFile);
    }
    
    private void processParticipants(final String path, final String fileName) {
    	System.out.print("Saving Participants .... ");
    	final String targetXMLFile = FileHelper.getTargetFileName(path, fileName);
        FileHelper.clearEntityReferences(path, targetXMLFile);
        biddingParticipantService.saveXMLContent(path, targetXMLFile);
    }
}
