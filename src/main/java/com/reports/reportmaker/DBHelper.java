package com.reports.reportmaker;

import java.sql.*;
import java.text.DecimalFormat;

class DBHelper {

    private static Connection conn = null;
    private static Statement stmt = null;

    /**
     * CREATE DB
     */

    /*
     * OPEN DATA-BASE CONNECTION
     * */
    static void openDBConnection() {

        try {
            // Register JDBC driver
            Class.forName(DataClass.JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DataClass.DB_URL, DataClass.USER, DataClass.PASS);
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
                    " clientId VARCHAR(6), " +
                    " requestId LONG, " +
                    " name VARCHAR(255), " +
                    " quantity INTEGER, " +
                    " price DOUBLE, " +
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
    private static void insertData(String clientId, long requestId, String name, int quantity, double price) {
        // Insert data query
        String sqlInsert = "INSERT INTO RAPPORTS " + "VALUES (default, '" +
                clientId + "', " +
                requestId + ", '" +
                name + "', " +
                quantity + ", " +
                price + ")";
        try {
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            // Handle errors for executeUpdate
            e.printStackTrace();
        }
    }

    /*
     * Checks whether the data is correct and saves it using the function @insertData
     * */
    static void checkCorrectDataAndSafe(DataObject object) {

        try {
            //parse strings to correct formats and delete white-spaces from clientID
            String clientID = object.getClientID().replaceAll("\\s", "");
            long requestID = Long.parseLong(object.getRequestID());
            int quantity = Integer.parseInt(object.getQuantity());

            //makes sure that the entry price has 2 decimal places - I use ROUND_HALF_UP!!!
            double price = Double.parseDouble(object.getPrice());
            price = Math.round(price * 100) / 100D;


            //checking if these values are within the varchar range
            if (clientID.length() > 6) {
                throw new Exception("too long clientID");
            }
            if (object.getName().length() > 255) {
                throw new Exception("too long name of product");
            }

            // save data in database with insertData function
            insertData(clientID, requestID, object.getName(), quantity, price);

        } catch (Exception e) {
            //throw parse exception for wrong format data, and too long value in varChar
            System.out.println(object.getRequestID() + " probably wrong data format in: " + object.getErrorIdentyficator());
            e.printStackTrace();
        }
    }

    /*
     *CLOSING DATABASE CONNECTION AND CLEAN UP ENVIRONMENT
     * */
    static void closeDB() {
        try {
            stmt.close();
            conn.close();
            System.out.println("Goodbye!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * GENERATE REPORTS
     */

    //LISTS
    static ResultSet showWholeData(String query) {

        ResultSet rs = null;
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
    static double calculateData(String query, String columnLabel) {

        ResultSet rs;
        double result = 0;
        try {
            rs = stmt.executeQuery(query);
            rs.next();
            result = rs.getDouble(columnLabel);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
