package edu.utfpr.importer.parser;

import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import edu.utfpr.importer.xml.handler.GenericHandler;

public class SAXParser<T> {

	public List<T> parseFile(final String path, final String fileName, final Class<? extends GenericHandler<T>> handlerClazz) {

		try {

			final SAXParserFactory factory = SAXParserFactory.newInstance();
			final javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
			final GenericHandler<T> handler = handlerClazz.newInstance();

			saxParser.parse(path + "/" + fileName, handler);

			return handler.getAllParsedData();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}