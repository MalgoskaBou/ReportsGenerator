package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * the class responsible for reading information from CSV file (using commons-csv library)
 */
class ReadFileCSV {

    /**
     * Processes and validates data from a file to an object that is ready to write to the database
     *
     * @param file file patch
     * @return list of {@link DataModel} objects which represent individual rows of the database
     */
    static ArrayList<DataModel> readData(String file) {

        DataLineObj dataLine;
        ArrayList<DataModel> listOfDataFromFile = new ArrayList<>();

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(file));
            reader.readLine();
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {

                String errorIdent = "Line: " + csvRecord.getRecordNumber() + " File: " + file;

                try {
                    // Accessing Values by Column Index
                    dataLine = new DataLineObj(csvRecord.get(0),
                            csvRecord.get(1),
                            csvRecord.get(2),
                            csvRecord.get(3),
                            csvRecord.get(4),
                            errorIdent);

                    DataModel validatedDataLine = dataLine.validateData();
                    if (validatedDataLine != null) {
                        listOfDataFromFile.add(validatedDataLine);
                    }

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
        return listOfDataFromFile;
    }
}