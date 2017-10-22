package edu.utfpr.importer.application;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.utfpr.importer.helper.FileHelper;
import edu.utfpr.importer.model.Bidding;
import edu.utfpr.importer.model.BiddingParticipant;
import edu.utfpr.importer.model.BiddingWinner;
import edu.utfpr.importer.model.ChangeLog;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.service.BiddingParticipantServiceImpl;
import edu.utfpr.importer.service.BiddingService;
import edu.utfpr.importer.service.BiddingServiceImpl;
import edu.utfpr.importer.service.BiddingWinnerServiceImpl;
import edu.utfpr.importer.service.ChangeLogService;

/**
 * Unpack files from FILE_DIRECTORY into UNZIPPED_FILES_DIRECTORY. Parse data from TARGET_UNZIPPED_FILE and save into database.
 */
public class BiddingProcessor {

    private static final String FILE_DIRECTORY = "C:/Users/a026710/Downloads/licitacoes/2017";
    private static final String UNZIPPED_FILES_DIRECTORY = "/../Temp";
    private static final String BIDDING_UNZIPPED_FILE = "Licitacao.xml";
    private static final String PARTICIPANTS_UNZIPPED_FILE = "LicitacaoParticipante.xml";
    private static final String WINNER_UNZIPPED_FILE = "LicitacaoVencedor.xml";

    private final BiddingService<Bidding> biddingService = new BiddingServiceImpl();
    private final BiddingService<BiddingParticipant> biddingParticipantService = new BiddingParticipantServiceImpl();
    private final BiddingService<BiddingWinner> biddingWinnerService = new BiddingWinnerServiceImpl();
    private final ChangeLogService changeLogService = new ChangeLogService();

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

                if (file.isDirectory()) {
                    continue;
                }

                System.out.print("Processing " + file.getName() + " (" + counter + " of " + dir.listFiles().length + "): ");

                final String unzippedFilesDir = FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY;

                FileHelper.cleanDirectory(unzippedFilesDir);

                FileHelper.unzipfile(unzippedFilesDir, file);

                processBidding(unzippedFilesDir, BIDDING_UNZIPPED_FILE, biddingService, 1);
                processBidding(unzippedFilesDir, PARTICIPANTS_UNZIPPED_FILE, biddingParticipantService, 1);
                processBidding(unzippedFilesDir, WINNER_UNZIPPED_FILE, biddingWinnerService, 8);

                FileHelper.cleanDirectory(FILE_DIRECTORY + UNZIPPED_FILES_DIRECTORY);

                System.out.println("OK");
                counter++;
            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Tempo: " + estimatedTime / 1000 + " Segundos");
    }

    private void registerChangeLog(final String fileName) {
        final ChangeLog changeLog = new ChangeLog();

        changeLog.setFile(fileName);
        changeLog.setProcessedDate(new Date());

        changeLogService.save(changeLog);
    }

    private boolean skipFile(final String fileName) {
        if (changeLogService.findByFile(fileName) != null) {
            return true;
        }

        return false;
    }

    /**
     * Parse XML Bidding information and save into database.
     * 
     * @param path
     * @param fileName
     */
    @SuppressWarnings("rawtypes")
    private void processBidding(final String path, final String fileName, final BiddingService service, final Integer numberThreads) {
        try {
            final String targetXMLFile = FileHelper.getTargetFileName(path, fileName);

            if (skipFile(targetXMLFile)) {
                System.out.print(" " + service.toString() + " - SKIP ...");
                return;
            }

            System.out.println(" " + service.toString() + " ...");

            FileHelper.clearEntityReferences(path, targetXMLFile);

            saveInParallel(service.parseXML(path, targetXMLFile), service.getClass(), numberThreads);

            registerChangeLog(targetXMLFile);

            System.out.print(".");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveInParallel(final List data, final Class service, final Integer numThreads) {
        
        List<Thread> threads = new ArrayList<Thread>();
        int subListSize = data.size() / numThreads;
        System.out.println("Total Size: " + data.size());
        
        for (int i = 0; i < numThreads; i++) {
            int start = i * subListSize;
            int end = start + subListSize;
            
            if(i == numThreads - 1) {
                end = data.size();
            }
            
            System.out.println("Starting Thread " + i + " Data Index from: " + start + " to " + end);
            Thread t = new Thread(new SaveBiddingThread(service, data.subList(start, end)), "Thread " + i);
            t.start();
            threads.add(t);
        }

        try {
            // Wait for the previous threads to finish
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class SaveBiddingThread implements Runnable {

        private Class serviceClazz;
        private List data;

        public SaveBiddingThread(final Class serviceClazz, final List data) {
            this.serviceClazz = serviceClazz;
            this.data = data;
        }

        @Override
        public void run() {
            try {

                final BiddingService service = (BiddingService) serviceClazz.newInstance();
                service.save(data);
                System.out.println(Thread.currentThread().getName() + " Finished");

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
