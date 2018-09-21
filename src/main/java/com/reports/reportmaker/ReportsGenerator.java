package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

public class ReportsGenerator {



    public static void main(String[] args) {
        args = new String[2];
        args[0]="report1.csv";
        args[1]="report2.xml";

        DBHelper.openDBConnection();
        DBHelper.createTable();

        System.out.println("There are " + args.length + " files to load\n");

        if (args.length > 0) {
            //load files
            for (String arg : args) {
                System.out.print("Loading file: " + arg+"\n");
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
            String columnLabel;
            String customerIdentifier;

            //take the user's choice
            switch (userChoice) {
                case 1:
                    columnLabel = "id";
                    query = "SELECT COUNT("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    break;
                case 2:
                    customerIdentifier= UserMenu.getCustomerIdentifier();
                    query = "SELECT id, clientId, requestId, name, quantity, price FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    DBHelper.showWholeData(query);
                    break;
                case 3:
                    columnLabel = "price";
                    query = "SELECT SUM("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    break;
                case 4:
                    customerIdentifier= UserMenu.getCustomerIdentifier();
                    columnLabel = "price";
                    query = "SELECT SUM("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    System.out.println(DBHelper.calculateData(query, columnLabel));
                    break;
                case 5:
                    String sqlWholeData = "SELECT id, clientId, requestId, name, quantity, price FROM RAPPORTS";
                    DBHelper.showWholeData(sqlWholeData);
                    break;
                case 6:
                    customerIdentifier= UserMenu.getCustomerIdentifier();
                    query = "SELECT id, clientId, requestId, name, quantity, price FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    DBHelper.showWholeData(query);
                    break;
                case 7:
                    columnLabel = "price";
                    query = "SELECT SUM("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS";
                    query2 = "SELECT COUNT("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS";
                    System.out.println((DBHelper.calculateData(query, columnLabel)/DBHelper.calculateData(query2, columnLabel)));
                    break;
                case 8:
                    customerIdentifier= UserMenu.getCustomerIdentifier();
                    columnLabel = "price";
                    query = "SELECT SUM("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    query2 = "SELECT COUNT("+columnLabel+") AS "+columnLabel+" FROM RAPPORTS WHERE clientId='" + customerIdentifier + "'";
                    System.out.println((DBHelper.calculateData(query, columnLabel)/DBHelper.calculateData(query2, columnLabel)));
                    break;
            }
        }

        //close DB and clear environment
        DBHelper.closeDB();
    }
}
