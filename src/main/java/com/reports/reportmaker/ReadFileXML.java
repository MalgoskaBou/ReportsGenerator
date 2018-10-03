package com.reports.reportmaker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;


import static com.reports.reportmaker.ConstDataClass.*;

/**
 * the class responsible for reading information from XML file
 */
class ReadFileXML {

    static ArrayList<DataModel> readData(String file) {
        File inputFile = new File(file);
        DataLineObj dataLine;
        ArrayList<DataModel> listOfDataFromFile = new ArrayList<>();

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
                    if ((eElement.getElementsByTagName(CLIENT_ID).item(0)) == null ||
                            (eElement.getElementsByTagName(REQUEST_ID).item(0)) == null ||
                            (eElement.getElementsByTagName(NAME).item(0)) == null ||
                            (eElement.getElementsByTagName(QUANTITY).item(0)) == null ||
                            (eElement.getElementsByTagName(PRICE).item(0)) == null) {
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
