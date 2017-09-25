package edu.utfpr.importer.service;

import java.util.List;

public interface BiddingService <T> {

	public void saveXMLContent(final String path, final String fileName);
	
	public void save(final List<T> biddings);
	
	public void save(final T bidding);
}
