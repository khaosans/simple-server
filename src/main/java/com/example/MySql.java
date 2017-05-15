package com.example;

import java.sql.*;


//TODO
public class MySql {

    public static final String DATABASE_PATH = "jdbc:mysql://localhost:3306/url";
    private Connection connection;

    public void add(String hash, String url) throws SQLException {

        try {
            connection = DriverManager.getConnection(DATABASE_PATH, "root", "");

            Statement st = connection.createStatement();

            //insert into url (k,v) values (112,123);
            String add = String.format("insert into url (k,v) values (\"%s\",\"%s\");", hash, url);

            System.out.println(add);
            st.executeUpdate(add);
        } finally {
            connection.close();
        }


    }

    public String get(String hash) throws SQLException {
        try {

            String url = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/url", "root", "");

            Statement st = connection.createStatement();
            String query = String.format("select * from url where k = \"%s\" limit 1;", hash);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                url = rs.getString("v");
            }
            return url;
        } finally {
            connection.close();
        }
    }
}
