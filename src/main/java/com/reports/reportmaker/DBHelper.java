package com.reports.reportmaker;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * functions manipulating the database
 */
class DBHelper {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;


    /**
     * OPEN DATABASE CONNECTION
     */
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

    /**
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

    /**
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

    /**
     * CLOSE DATABASE
     */
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
     * Shows a list of data - it gets many records
     * @param query SQL query to the database
     * @return ResultSet element containing many rows from the database
     */
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

    /**
     * Shows the calculated element from the selected column from the database
     *
     * @param query       SQL query to the database
     * @param columnLabel selected column on which the calculations will be made
     * @return single calculated value in String to avoid rounding errors
     */
    static String showCalculateData(String query, String columnLabel) {

        //ResultSet rs;
        String result = null;
        try {
            rs = stmt.executeQuery(query);
            rs.next();


            // Set decimal format with dot like a separator
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
            otherSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#####.##", otherSymbols);

            result = decimalFormat.format(rs.getDouble(columnLabel));
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
