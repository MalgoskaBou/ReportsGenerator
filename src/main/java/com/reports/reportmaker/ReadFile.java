package com.reports.reportmaker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.reports.reportmaker.ConstDataClass.CLIENT_ID;
import static com.reports.reportmaker.ConstDataClass.ELEMENT;
import static com.reports.reportmaker.ConstDataClass.REQUEST_ID;
import static com.reports.reportmaker.ConstDataClass.QUANTITY;
import static com.reports.reportmaker.ConstDataClass.NAME;
import static com.reports.reportmaker.ConstDataClass.PRICE;



/**
 * the class responsible for reading information from files.
 */
final class ReadFile {

    /**
     * Private constructor.
     */
    private ReadFile() {
        // do nothing
        // This prevents the default parameter-less
        // constructor from being used elsewhere in your code.
    }

    /**
     * {@link DataLineObj}.
     */
    private static DataLineObj dataLine;
    /**
     * {@link DataModel}.
     */
    private static ArrayList<DataModel> listOfDataFromFile = new ArrayList<>();

    /**
     * Processes and validates data from a file to an object that is ready to write to the database.
     * (For CSV files)
     *
     * @param file file patch
     * @return list of {@link DataModel} objects which represent individual rows of the database
     */
    static ArrayList<DataModel> readDataFromCSV(final String file) {

        listOfDataFromFile.clear();
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

    /**
     * Processes and validates data from a file to an
     * object that is ready to write to the database.
     *(For XML files)
     * @param file file patch
     * @return list of {@link DataModel} objects which represent individual rows of the database
     */
    static ArrayList<DataModel> readDataFromXML(final String file) {

        File inputFile = new File(file);
        listOfDataFromFile.clear();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(ELEMENT);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                String errorIdent = "Line: " + (temp + 1) + " File: " + file;

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    //checking if there is no missing elements
                    if ((eElement.getElementsByTagName(CLIENT_ID).item(0)) == null
                            || (eElement.getElementsByTagName(REQUEST_ID).item(0)) == null
                            || (eElement.getElementsByTagName(NAME).item(0)) == null
                            || (eElement.getElementsByTagName(QUANTITY).item(0)) == null
                            || (eElement.getElementsByTagName(PRICE).item(0)) == null) {
                        System.out.println("Missing one or more elements in: " + errorIdent);

                    } else {

                        dataLine = new DataLineObj(eElement.getElementsByTagName(CLIENT_ID).item(0).getTextContent(),
                                eElement.getElementsByTagName(REQUEST_ID).item(0).getTextContent(),
                                eElement.getElementsByTagName(NAME).item(0).getTextContent(),
                                eElement.getElementsByTagName(QUANTITY).item(0).getTextContent(),
                                eElement.getElementsByTagName(PRICE).item(0).getTextContent(),
                                errorIdent);

                        DataModel validatedDataLine = dataLine.validateData();
                        if (validatedDataLine != null) {
                            listOfDataFromFile.add(validatedDataLine);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("probably wrong file format: " + file);
            e.printStackTrace();
        }
        return listOfDataFromFile;
    }
}
