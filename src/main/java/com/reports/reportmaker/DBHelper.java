package com.reports.reportmaker;

import java.sql.*;
import java.util.ArrayList;

class DBHelper {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;


    /**
     * CREATE DB
     */

    /*
     * OPEN DATA-BASE CONNECTION
     * */
    static void openDBConnection() {

        try {
            // Register JDBC driver
            Class.forName(ConstDataClass.JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(ConstDataClass.DB_URL, ConstDataClass.USER, ConstDataClass.PASS);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    /*
     * CREATE A TABLE WITH A MANDATORY FIELDS
     * */
    static void createTable() {
        try {

            // Create table with the necessary columns
            String sqlCreate = "CREATE TABLE   RAPPORTS " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " clientId VARCHAR(6) not NULL, " +
                    " requestId LONG not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " quantity INTEGER not NULL, " +
                    " price DECIMAL(7 , 2 ) not NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sqlCreate);

            System.out.println("Created table in given database...");
        } catch (Exception e) {
            // Handle errors for executeUpdate
            e.printStackTrace();
        }
    }

    /*
     * FUNCTION RESPONSIBLE FOR INSErt DATA TO DATABASE
     * */
    static void insertData(ArrayList<DataModel> dataModels) {
        // Insert data query

        if (dataModels.size() > 0) {
            for (DataModel datamodel : dataModels) {

                String sqlInsert = "INSERT INTO RAPPORTS " + "VALUES (default, '" +
                        datamodel.getClientID() + "', " +
                        datamodel.getRequestID() + ", '" +
                        datamodel.getName() + "', " +
                        datamodel.getQuantity() + ", " +
                        datamodel.getPrice() + ")";
                try {
                    stmt.executeUpdate(sqlInsert);
                } catch (SQLException e) {
                    // Handle errors for executeUpdate
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     *CLOSING DATABASE CONNECTION AND CLEAN UP ENVIRONMENT
     * */
    static void closeDB() {
        try {
            stmt.close();
            conn.close();
            if (rs != null) {
                rs.close();
            }
            System.out.println("Goodbye!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SHOW DATA
     */

    //LISTS
    static ResultSet showWholeData(String query) {

        try {
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Retrieve by column name
                int id = rs.getInt("id");
                String clientId = rs.getString("clientId");
                long requestId = rs.getLong("requestId");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                // Display values
                System.out.print("ID: " + id + "\t");
                System.out.print(", clientId: " + clientId + "\t");
                System.out.print(", requestId: " + requestId + "\t");
                System.out.print(", name: " + name + "\t");
                System.out.print(", quantity: " + quantity + "\t");
                System.out.print(", price: " + String.format("%.2f", price) + "\n");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //CALCULATIONS
    static double showCalculateData(String query, String columnLabel) {

        //ResultSet rs;
        double result = 0;
        try {
            rs = stmt.executeQuery(query);
            rs.next();
            result = rs.getDouble(columnLabel);

            // Display value
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
