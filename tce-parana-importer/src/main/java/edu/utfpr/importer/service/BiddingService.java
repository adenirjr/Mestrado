package edu.utfpr.importer.service;

import java.util.List;

public interface BiddingService<T> {

    public List<T> parseXML(final String path, final String fileName) throws Exception;

    public void save(final List<T> biddings);

    public void save(final T bidding);
}
