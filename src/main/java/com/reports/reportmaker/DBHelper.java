package com.reports.reportmaker;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.reports.reportmaker.ConstDataClass.CLIENT_ID;
import static com.reports.reportmaker.ConstDataClass.REQUEST_ID;
import static com.reports.reportmaker.ConstDataClass.NAME;
import static com.reports.reportmaker.ConstDataClass.QUANTITY;
import static com.reports.reportmaker.ConstDataClass.PRICE;
import static com.reports.reportmaker.ConstDataClass.TABLE_NAME;
import static com.reports.reportmaker.ConstDataClass.ID;


/**
 * functions for manipulating the database.
 */
final class DBHelper {

    /**
     * {@link Connection}.
     */
    private static Connection conn = null;
    /**
     * {@link Statement}.
     */
    private static Statement stmt = null;
    /**
     * {@link ResultSet}.
     */
    private static ResultSet rs = null;

    /**
     * Private constructor.
     */
    private DBHelper() {
        // do nothing
        // This prevents the default parameter-less
        // constructor from being used elsewhere in your code.
    }

    /**
     * OPEN DATABASE CONNECTION.
     */
    static void openDBConnection() {

        try {
            // Register JDBC driver
            Class.forName(ConstDataClass.JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(ConstDataClass.DB_URL,
                    ConstDataClass.USER, ConstDataClass.PASS);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    /**
     * CREATE A TABLE WITH A MANDATORY FIELDS.
     */
    static void createTable() {
        try {

            // Create table with the necessary columns
            String sqlCreate = "CREATE TABLE " + TABLE_NAME
                    + "( " + ID + " INTEGER not NULL AUTO_INCREMENT, "
                    + CLIENT_ID + " VARCHAR(6) not NULL, "
                    + REQUEST_ID + " LONG not NULL, "
                    + NAME + " VARCHAR(255) not NULL, "
                    + QUANTITY + "  INTEGER not NULL, "
                    + PRICE + " DECIMAL(7 , 2 ) not NULL, "
                    + " PRIMARY KEY ( " + ID + " ))";
            stmt.executeUpdate(sqlCreate);

            System.out.println("Created table in given database...");
        } catch (Exception e) {
            // Handle errors for executeUpdate
            e.printStackTrace();
        }
    }

    /**
     * FUNCTION RESPONSIBLE FOR INSErt DATA TO DATABASE.
     *
     * @param dataModels ArrayList of {@link DataModel}
     */
    static void insertData(final ArrayList<DataModel> dataModels) {
        // Insert data query

        if (dataModels.size() > 0) {
            for (DataModel datamodel : dataModels) {

                String sqlInsert = "INSERT INTO RAPPORTS "
                        + "VALUES (default, '"
                        + datamodel.getmClientID() + "', "
                        + datamodel.getmRequestID() + ", '"
                        + datamodel.getmName() + "', "
                        + datamodel.getmQuantity() + ", "
                        + datamodel.getmPrice() + ")";
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
     * Get data from database by query.
     *
     * @param query query to the database
     * @return ResultSet object with data
     */
    static ResultSet getData(final String query) {
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * CLOSE DATABASE.
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
}
