package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.reports.reportmaker.UserMenu.getCustomerIdentifier;

/**
 * Generates a report to display or save to a file
 */
class GenerateReportsOptions {

    static void generateReport(ResultSet rs, Boolean saveFile, Boolean showData) {
        if (saveFile && !showData)
            saveFile(rs);

        if (showData && !saveFile)
            showData(rs);

        if (showData || saveFile)
            saveFileAndSHowData(rs);
    }

    /*
     * QUERY OPTIONS
     */

    /**
     * Generate query with function
     *
     * @param customerIdentifierFilter if need custom ID filter
     * @param typeOfCount              type of function (SUM, COUNT, AVG)
     * @param columnId                 column on which operations will be performed
     * @return query as a String
     */
    static String generateQuery(Boolean customerIdentifierFilter, String typeOfCount, String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";

        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    /**
     * Generate query for whole columns
     *
     * @param customerIdentifierFilter if need custom ID filter
     * @return query as a String
     */
    static String generateQuery(Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    /*
     * REPORT OPTIONS
     */

    /**
     * Writes the data on the screen from ResultSet
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void showData(ResultSet rs) {
        try {

            //reset cursor for any case
            rs.beforeFirst();

            //get number of columns from metadata
            ResultSetMetaData metaDataRs = rs.getMetaData();
            int numberOfColumns = metaDataRs.getColumnCount();

            //show headers
            for (int i = 0; i < numberOfColumns; ++i) {

                System.out.print(metaDataRs.getColumnName(i + 1) + ", ");
            }
            System.out.println();

            //show data
            while (rs.next()) {

                for (int i = 0; i < numberOfColumns; ++i) {

                    System.out.print(rs.getString(i + 1) + ", ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a csv file with data from ResultSet with columns headers
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void saveFile(ResultSet rs) {

        try {
            //cursor reset
            rs.beforeFirst();

            //create file
            FileWriter out = new FileWriter("report_generated.csv");
            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);

            try {
                //get number of columns from metadata
                ResultSetMetaData metaDataRs = rs.getMetaData();
                int numberOfColumns = metaDataRs.getColumnCount();

                for (int i = 0; i < numberOfColumns; ++i) {

                    csvPrinter.print(metaDataRs.getColumnName(i + 1));
                }
                csvPrinter.println();

                while (rs.next()) {

                    for (int i = 0; i < numberOfColumns; ++i) {

                        csvPrinter.print(rs.getString(i + 1));
                    }
                    csvPrinter.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //clean up environment
                out.flush();
                out.close();
                csvPrinter.close();
                System.out.println("Your report has been generated.");
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a csv file and show whole data from ResultSet with columns headers
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void saveFileAndSHowData(ResultSet rs) {

        try {
            //cursor reset
            rs.beforeFirst();

            //create file
            FileWriter out = new FileWriter("report_generated.csv");
            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);

            try {
                //get number of columns from metadata
                ResultSetMetaData metaDataRs = rs.getMetaData();
                int numberOfColumns = metaDataRs.getColumnCount();

                for (int i = 0; i < numberOfColumns; ++i) {

                    System.out.print(metaDataRs.getColumnName(i + 1) + ", ");
                    csvPrinter.print(metaDataRs.getColumnName(i + 1));
                }
                System.out.println();
                csvPrinter.println();

                while (rs.next()) {

                    for (int i = 0; i < numberOfColumns; ++i) {

                        System.out.print(rs.getString(i + 1) + ", ");
                        csvPrinter.print(rs.getString(i + 1));
                    }
                    System.out.println();
                    csvPrinter.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //clean up environment
                out.flush();
                out.close();
                csvPrinter.close();
                System.out.println("Your report has been generated.");
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
