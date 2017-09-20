package edu.utfpr.importer.persistence.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCProvider {

    public static Connection getConnection() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:eleicoes2014.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}