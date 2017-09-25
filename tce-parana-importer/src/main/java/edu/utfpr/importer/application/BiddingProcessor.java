package edu.utfpr.importer.application;

import java.io.File;

import edu.utfpr.importer.helper.FileHelper;
import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.model.BiddingParticipant;
import edu.utfpr.importer.model.BiddingWinner;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.service.BiddingParticipantServiceImpl;
import edu.utfpr.importer.service.BiddingService;
import edu.utfpr.importer.service.BiddingServiceImpl;
import edu.utfpr.importer.service.BiddingWinnerServiceImpl;

/**
 * Unpack files from FILE_DIRECTORY into UNZIPPED_FILES_DIRECTORY. Parse data from TARGET_UNZIPPED_FILE and save into database.
 */
public class BiddingProcessor {

    private static final String FILE_DIRECTORY = "C:/Users/Adenir/Downloads/licitacoes/2014";
    private static final String UNZIPPED_FILES_DIRECTORY = "/../Temp";
    private static final String BIDDING_UNZIPPED_FILE = "Licitacao.xml";
    private static final String PARTICIPANTS_UNZIPPED_FILE = "LicitacaoParticipante.xml";
    private static final String WINNER_UNZIPPED_FILE = "LicitacaoVencedor.xml";
    
    private final BiddingService<Bidding> biddingService = new BiddingServiceImpl();
    private final BiddingService<BiddingParticipant> biddingParticipantService = new BiddingParticipantServiceImpl();
    private final BiddingService<BiddingWinner> biddingWinnerService = new BiddingWinnerServiceImpl();

    public static void main(String[] args) {
        BiddingProcessor reader = new BiddingProcessor();
        
        HibernateProvider.getInstance().createEntityManagerFactory();
        
        reader.init();
        
        HibernateProvider.getInstance().close();
    }
    
    @Override
    protected void finalize() throws Throwable {
    	System.out.println("finilized");
    	super.finalize();
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
                
                System.out.print("Processing " + file.getName() + " (" + counter + " of " + dir.listFiles().length + "): ");
                
                final String unzippedFilesDir = FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY;
                
                FileHelper.cleanDirectory(unzippedFilesDir);
                
                FileHelper.unzipfile(unzippedFilesDir, file);
                
                processBidding(unzippedFilesDir, BIDDING_UNZIPPED_FILE, biddingService);
                processBidding(unzippedFilesDir, PARTICIPANTS_UNZIPPED_FILE, biddingParticipantService);
                processBidding(unzippedFilesDir, WINNER_UNZIPPED_FILE, biddingWinnerService);
                
                FileHelper.cleanDirectory(FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY);
                
                System.out.println("OK");
                counter++;
            }
        }
        
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Tempo: " + estimatedTime / 1000 + " Segundos");
    }
    
    /**
     * Parse XML Bidding information and save into database.
     * 
     * @param path
     * @param fileName
     */
    @SuppressWarnings("rawtypes")
	private void processBidding(final String path, final String fileName, final BiddingService service) {
    	System.out.print(" " + service.toString() + " .");
    	final String targetXMLFile = FileHelper.getTargetFileName(path, fileName);
    	
    	System.out.print("..");
        FileHelper.clearEntityReferences(path, targetXMLFile);
        
        System.out.print("...");
        service.saveXMLContent(path, targetXMLFile);
        
        System.out.print("....");
    }
}
