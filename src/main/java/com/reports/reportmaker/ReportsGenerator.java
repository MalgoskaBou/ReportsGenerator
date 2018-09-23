package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

import java.sql.ResultSet;

public class ReportsGenerator {


    public static void main(String[] args) {

        DBHelper.openDBConnection();
        DBHelper.createTable();

        System.out.println("There are " + args.length + " files to load\n");

        if (args.length > 0) {
            //load files
            for (String arg : args) {
                System.out.print("Loading file: " + arg + "\n");
                switch (FilenameUtils.getExtension(arg)) {
                    case "xml":
                        ReadFileXML.readData(arg);
                        break;
                    case "csv":
                        ReadFileCSV.readData(arg);
                        break;
                    default:
                        System.out.println("Wrong file format - expected .csv or .xml");
                        break;
                }
            }

            //show menu
            int userChoice = UserMenu.menu();
            String query;
            String query2;
            String columnLabel = "price";
            String customerIdentifier;
            ResultSet rs;

            //take the user's choice
            switch (userChoice) {
                case 1:
                    query = "SELECT COUNT(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    SaveToCsvFile.saveFileWithASingleValue(String.valueOf(DBHelper.calculateData(query, columnLabel)));
                    break;
                case 2:
                    customerIdentifier = UserMenu.getCustomerIdentifier();
                    query = "SELECT * FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    rs = DBHelper.showWholeData(query);
                    SaveToCsvFile.saveFileFromResultSet(rs);
                    break;
                case 3:
                    query = "SELECT SUM(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    SaveToCsvFile.saveFileWithASingleValue(String.valueOf(DBHelper.calculateData(query, columnLabel)));
                    break;
                case 4:
                    customerIdentifier = UserMenu.getCustomerIdentifier();
                    query = "SELECT SUM(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    SaveToCsvFile.saveFileWithASingleValue(String.valueOf(DBHelper.calculateData(query, columnLabel)));
                    break;
                case 5:
                    query = "SELECT * FROM RAPPORTS";
                    rs = DBHelper.showWholeData(query);
                    SaveToCsvFile.saveFileFromResultSet(rs);
                    break;
                case 6:
                    customerIdentifier = UserMenu.getCustomerIdentifier();
                    query = "SELECT * FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    rs = DBHelper.showWholeData(query);
                    SaveToCsvFile.saveFileFromResultSet(rs);
                    break;
                case 7:
                    query = "SELECT SUM(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS";
                    query2 = "SELECT COUNT(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS";
                    double resultPrice = DBHelper.calculateData(query, columnLabel) / DBHelper.calculateData(query2, columnLabel);
                    System.out.println(resultPrice);
                    SaveToCsvFile.saveFileWithASingleValue(String.valueOf(resultPrice));

                    break;
                case 8:
                    customerIdentifier = UserMenu.getCustomerIdentifier();
                    query = "SELECT SUM(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    query2 = "SELECT COUNT(" + columnLabel + ") AS " + columnLabel + " FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    double resultPrice2 = DBHelper.calculateData(query, columnLabel) / DBHelper.calculateData(query2, columnLabel);
                    System.out.println(resultPrice2);
                    SaveToCsvFile.saveFileWithASingleValue(String.valueOf(resultPrice2));
                    break;
                default:
                    System.out.println("There is no such option on the menu");
            }
        }

        //close DB and clear environment
        DBHelper.closeDB();
    }
}
