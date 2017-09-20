package edu.utfpr.importer.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHelper {

	public static void clearEntityReferences(final String absPath, final String fileName) {

		Path path = Paths.get(absPath + "/" + fileName);

		try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_16LE)) {
			List<String> replaced = lines.map(line -> line.replaceAll("&#x00;", "É")).collect(Collectors.toList());
			Files.write(path, replaced);
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

	public static String getTargetFileName(final String path, final String target) {
		final File dir = new File(path);

		if (dir.isDirectory()) {
			for (String fileName : dir.list()) {
				if (fileName.endsWith(target)) {
					return fileName;
				}
			}
		}

		return "";
	}

	public static void unzipfile(final String unzipHere, final File compressedFile) {

		try {
			final byte[] buffer = new byte[1024];
			final ZipInputStream zis = new ZipInputStream(new FileInputStream(compressedFile));

			ZipEntry zipEntry = zis.getNextEntry();

			while (zipEntry != null) {
				final String fileName = zipEntry.getName();
				final File newFile = new File(unzipHere + "/" + fileName);
				final FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				zipEntry = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
