package edu.utfpr.webscrapper.adapter;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTTPAdapter {

    public Document doPost(final String url, final Map<String, String> params) {
        try {
            Connection connection = Jsoup.connect(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                connection.data(entry.getKey(), entry.getValue());
            }

            return connection.post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Document doGet(final String url) {
        try {

            Connection connection = Jsoup.connect(url);
            return connection.get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Response execute(final String url) {
        try {

            Connection connection = Jsoup.connect(url);
            return connection.ignoreContentType(true).maxBodySize(0).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
