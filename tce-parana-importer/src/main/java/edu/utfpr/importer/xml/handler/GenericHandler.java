package edu.utfpr.importer.xml.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

public class GenericHandler<T> extends DefaultHandler {

	private final List<T> parsedData = new ArrayList<T>();

	public List<T> getAllParsedData() {
		return parsedData;
	}

	public void addParsedData(T t) {
		parsedData.add(t);
	}
}
