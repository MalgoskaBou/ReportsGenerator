package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

class ReadFileCSV {

    static void readData(String file) {

        DataModel dataLine;
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(file));
            reader.readLine();
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {

                String errorIdent = "Line: " + csvRecord.getRecordNumber() + " File: " + file;

                try {
                    // Accessing Values by Column Index
                    dataLine = new DataModel(csvRecord.get(0),
                            csvRecord.get(1),
                            csvRecord.get(2),
                            csvRecord.get(3),
                            csvRecord.get(4));

                    DBHelper.checkCorrectDataAndSafe(dataLine, errorIdent);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //catch exception if missing element
                    e.printStackTrace();
                    System.out.println("Missing items in: " + errorIdent);
                }
            }

            reader.close();
            csvParser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}