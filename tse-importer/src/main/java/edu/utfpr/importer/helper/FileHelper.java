package edu.utfpr.importer.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

	/**
	 * Chunk Size bytes
	 *
	 * @param absPath
	 * @param fileName
	 * @param chunkSize
	 */
	public static List<String> splitFile(final String absPath, final String chunkDir, final String fileName,
			final int chunkSize) {
		final File file = new File(absPath + "/" + fileName);
		final List<String> chunkFiles = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1))) {

			String line = "";
			int fileNumber = 1;
			int lineNumber = 0;
			final StringBuilder content = new StringBuilder();
			while ((line = br.readLine()) != null) {
				lineNumber++;

				if (lineNumber == 1) {
					continue;
				}

				content.append(line).append("\n");

				if (content.length() > chunkSize) {
					final String name = fileNumber + "-" + fileName;

					createFile(absPath + chunkDir, name, content.toString());
					chunkFiles.add(name);

					fileNumber++;
					content.delete(0, content.length());
				}
			}

			createFile(absPath + chunkDir, fileNumber + "-" + fileName, content.toString());

			System.out.println(lineNumber + " Rows migrated to the chunk files.");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return chunkFiles;
	}

	public static void createFile(final String absPath, final String fileName, final String content) {
		System.out.println("Creating file: " + fileName);
		File chunk = new File(absPath, fileName);
		try {
			if (chunk.createNewFile()) {
				FileWriter writer = new FileWriter(chunk);
				writer.write(content);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cleanDirectory(final String path) {
		final File dir = new File(path);

		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}
	}

	public static String[] getFilesFromDirectory(final String directory) {
		final File dir = new File(directory);
		return dir.list();
	}
}
