package com.example;

import java.sql.*;

public class MySql {

    private Connection connection;

    /*
            CREATE TABLE `url` (
          `k` varchar(200) DEFAULT NULL,
          `v` varchar(200) DEFAULT NULL
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
     */

    public static void main(String[] argv) {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/url", "root", "");

            new MySql().add("1", "ad");
            System.out.println(new MySql().get(1 + ""));

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

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
