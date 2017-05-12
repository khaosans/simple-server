package com.example;

import java.sql.*;

public class MySql {

    private Connection connection;

    public void add(String id, String url) throws SQLException {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/url", "root", "");

            Statement st = connection.createStatement();

            //insert into url (k,v) values (112,123);
            String add = String.format("insert into url (k,v) values (\"%s\",\"%s\");", id, url);

            System.out.println(add);
            st.executeUpdate(add);
        } finally {
            connection.close();
        }


    }

    public String get(String uuid) throws SQLException {
        try {

            String url = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/url", "root", "");

            Statement st = connection.createStatement();
            String query = String.format("select * from url where k = \"%s\" limit 1;", uuid);
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
