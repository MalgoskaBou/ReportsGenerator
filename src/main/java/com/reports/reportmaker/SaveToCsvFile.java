package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class   SaveToCsvFile {

    static void saveFileWithASingleValue(String result, String reportName) {

        try {
            FileWriter out = new FileWriter("report_generated.csv");
            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);

            csvPrinter.print(reportName);
            csvPrinter.println();
            csvPrinter.print(result);

            out.flush();
            out.close();
            csvPrinter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void saveFileFromResultSet(ResultSet result) {

        try {
            //cursor reset
            result.beforeFirst();

            //create file
            FileWriter out = new FileWriter("report_generated.csv");

            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);

            try {
                //get number of columns from metadata
                ResultSetMetaData metaDataRs = result.getMetaData();
                int numberOfColumns = metaDataRs.getColumnCount();

                for (int i = 0; i < numberOfColumns; ++i) {

                    csvPrinter.print(metaDataRs.getColumnName(i + 1));
                }
                csvPrinter.println();

                while (result.next()) {

                    for (int i = 0; i < numberOfColumns; ++i) {

                        csvPrinter.print(result.getString(i + 1));
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
}
