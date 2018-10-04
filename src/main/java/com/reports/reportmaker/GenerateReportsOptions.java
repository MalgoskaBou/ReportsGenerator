package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.reports.reportmaker.UserMenu.menuGetCustomerIdentifier;

/**
 * Generates queries to obtain data to report
 * and display data, save to a file or both.
 */
final class GenerateReportsOptions {

    /**
     * Private constructor.
     */
    private GenerateReportsOptions() {
        //do nothing
        //This prevents the default parameter-less constructor
        //from being used elsewhere in your code.
    }

    /**
     * Generates data depending on the selected option.
     *
     * @param rs       ResultSet with input data
     * @param saveFile select the truth if you want to save the data to a file
     * @param showData select the truth if you want to show data on the screen
     */
    static void generateReport(final ResultSet rs,
                               final Boolean saveFile,
                               final Boolean showData) {
        //todo boolean depend from user choice!


        if (saveFile && !showData) {
            saveFile(rs);
        }

        if (showData && !saveFile) {
            showData(rs);
        }

        if (showData || saveFile) {
            saveFileAndSHowData(rs);
        }
    }

    /*
     * QUERY OPTIONS
     */

    /**
     * Generate query with SQL function.
     *
     * @param customerIdentifierFilter if need custom ID filter
     *                                 -info from {@link UserMenu#menuGetCustomerIdentifier()}
     * @param typeOfCount              type of function (SUM, COUNT, AVG)
     * @param columnId                 column on which operations will be performed
     * @return query as a String
     */
    static String generateQuery(final Boolean customerIdentifierFilter,
                                final String typeOfCount,
                                final String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";

        if (customerIdentifierFilter) {
            String customerIdentifier = menuGetCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    /**
     * Generate query for whole columns.
     *
     * @param customerIdentifierFilter if need custom ID filter
     *                                 -info from {@link UserMenu#menuGetCustomerIdentifier()}
     * @return query as a String
     */
    static String generateQuery(final Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = menuGetCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    /*
     * REPORT OPTIONS
     */

    /**
     * Writes the data on the screen from ResultSet.
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void showData(final ResultSet rs) {
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
     * Save a csv file with data from ResultSet with columns headers.
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void saveFile(final ResultSet rs) {

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
     * Save a csv file and show whole data from ResultSet with columns headers.
     *
     * @param rs gets the ResultSet with data returned by {@link DBHelper#getData(String)}
     */
    private static void saveFileAndSHowData(final ResultSet rs) {

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
