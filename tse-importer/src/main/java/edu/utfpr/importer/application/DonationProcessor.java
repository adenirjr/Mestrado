package edu.utfpr.importer.application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utfpr.importer.builder.DonationBuilder;
import edu.utfpr.importer.helper.FileHelper;
import edu.utfpr.importer.persistence.provider.HibernateProvider;
import edu.utfpr.importer.service.DonationService;

public class DonationProcessor {

	private static final Long YEAR = 2012l;
	private static final String TEMP_DIR = "/temp";
	private static final String PATH = "C:/Users/Adenir/Downloads/prestacao_contas";
	private static final String CSV_FILE_NAME = "receitas_candidatos_2012_PR.txt";
	private static final String SEPARATOR = "\";\"";
	private static final Integer NUMBER_OF_THREADS = 4;

	public static void main(String args[]) {
		final long start = System.currentTimeMillis();

		final DonationProcessor processor = new DonationProcessor();
		processor.init();

		final long end = System.currentTimeMillis();
		System.out.println("Tempo: " + (end - start) / 1000 + " Segundos");
	}

	private void init() {
		HibernateProvider.getInstance().createEntityManagerFactory();

		processFile(PATH, CSV_FILE_NAME);

		HibernateProvider.getInstance().close();
	}

	private void processFile(final String path, final String originalFileName) {
		final String[] metadata = getCSVHeader(path, originalFileName);
		final List<Thread> threads = new ArrayList<Thread>();

		FileHelper.cleanDirectory(PATH + TEMP_DIR);
		FileHelper.splitFile(PATH, TEMP_DIR, originalFileName, 5000000);

		for (String fileName : FileHelper.getFilesFromDirectory(PATH + TEMP_DIR)) {
			Thread thread = new Thread(new SavingDonationThread(PATH + TEMP_DIR, fileName, metadata));
			threads.add(thread);

			System.out.println("Starting Thread " + threads.size() + " File: " + fileName);
			thread.start();

			if (threads.size() == NUMBER_OF_THREADS) {
				wait(threads);
				threads.clear();
			}
		}
		
		wait(threads);
	}

	private void wait(final List<Thread> threads) {
		try {
			for (Thread t : threads) {
				t.join();
			}
		} catch (InterruptedException e) {
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

	private class SavingDonationThread implements Runnable {

		private final DonationService donationService = new DonationService();
		private String filePath;
		private String fileName;
		private String[] metadata;

		public SavingDonationThread(final String filePath, final String fileName, final String[] metadata) {
			this.filePath = filePath;
			this.fileName = fileName;
			this.metadata = metadata;
		}

		@Override
		public void run() {
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
					//System.out.println("Thread " +  Thread.currentThread().getName() + " Data: " + m.toString());
					donationService.save(new DonationBuilder().setAttributes(m).setYear(YEAR).build());
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
