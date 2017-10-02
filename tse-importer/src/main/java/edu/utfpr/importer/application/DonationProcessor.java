package edu.utfpr.importer.application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import edu.utfpr.importer.builder.DonationBuilder;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.service.DonationService;

public class DonationProcessor {

    private static final Long YEAR = 2014l;
    private static final String PATH = "C:/Users/a026710/Downloads/prestacao_contas";
    private static final String CSV_FILE_NAME = "receitas_candidatos_2014_brasil.txt";
    private static final String SEPARATOR = "\";\"";

    private final DonationService donationService = new DonationService();

    public static void main(String args[]) {
        final long start = System.currentTimeMillis();

        final DonationProcessor processor = new DonationProcessor();
        processor.init();

        final long end = System.currentTimeMillis();
        System.out.println("Tempo: " + (end - start) / 1000 + " Segundos");
    }

    private void init() {
        final String[] metadata = getCSVHeader(PATH, CSV_FILE_NAME);

        HibernateProvider.getInstance().createEntityManagerFactory();

        saveLineByLine(PATH, CSV_FILE_NAME, metadata);

        HibernateProvider.getInstance().close();
    }

    /**
     * @param filePath
     * @param fileName
     * @param metadata
     */
    private void saveLineByLine(final String filePath, final String fileName, final String[] metadata) {
        try {
            final Path path = Paths.get(filePath, fileName);
            Files.lines(path, StandardCharsets.ISO_8859_1).skip(1).map(line -> {
                final Map<String, String> keyValue = new HashMap<String, String>();
                final String[] values = line.split(SEPARATOR);

                int index = 0;
                for (String value : values) {
                    keyValue.put(metadata[index], value.replaceAll("\"", ""));
                    index++;
                }
                return keyValue;
            }).forEach(m -> {
                donationService.save(new DonationBuilder().setAttributes(m).setYear(YEAR).build());
            });

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * The file's first line MUST be the attribute names.
     * 
     * @return
     */
    private String[] getCSVHeader(final String filePath, final String fileName) {
        try {
            final Path path = Paths.get(filePath, fileName);
            String firstLine = Files.lines(path, StandardCharsets.ISO_8859_1).findFirst().get();

            return firstLine.replaceAll("\"", "").split(";");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
