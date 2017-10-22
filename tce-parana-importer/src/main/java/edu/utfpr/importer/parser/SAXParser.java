package edu.utfpr.importer.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import edu.utfpr.importer.xml.handler.GenericHandler;

public class SAXParser<T> {

    public List<T> parseFile(final String path, final String fileName, final Class<? extends GenericHandler<T>> handlerClazz) throws Exception {
        File initialFile = new File(path + "/" + fileName);
        InputStream is = new FileInputStream(initialFile);
        
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            final javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            final GenericHandler<T> handler = handlerClazz.newInstance();

            saxParser.parse(is, handler);

            return handler.getAllParsedData();
        } catch (Exception e) {
            throw e;
        } finally {
            is.close();
        }
    }
}