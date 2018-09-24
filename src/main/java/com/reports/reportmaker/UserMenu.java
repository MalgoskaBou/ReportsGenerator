package com.reports.reportmaker;

import java.sql.ResultSet;
import java.util.Scanner;

class UserMenu {

    static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose report to generate");
        System.out.println("-------------------------\n");
        System.out.println("1 - Total number of orders");
        System.out.println("2 - Number of orders from the customer with the given identifier");
        System.out.println("3 - Total amount of orders");
        System.out.println("4 - The total amount of orders to the customer with the indicated identifier");
        System.out.println("5 - List of all orders");
        System.out.println("6 - List of orders from the customer with the given identifier");
        System.out.println("7 - Average value of the order");
        System.out.println("8 - Average value of the order from the customer with the given identifier");

        System.out.print("Choose report to generate by entering a number from the menu: ");

        selection = input.nextInt();
        return selection;
    }

    private static String getCustomerIdentifier() {

        String selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose client identifier: ");

        selection = input.next();
        return selection;
    }

    static void generateReportList(Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        ResultSet rs = DBHelper.showWholeData(query);
        SaveToCsvFile.saveFileFromResultSet(rs);
    }

    static void generateReportCount(Boolean customerIdentifierFilter, String typeOfCount, String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";
        String reportName = "The " + typeOfCount + " of the " + columnId + " of all orders:";

        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
            reportName = reportName + " where clientID is - " + customerIdentifier;
        }
        System.out.println(DBHelper.calculateData(query, columnId));
        SaveToCsvFile.saveFileWithASingleValue(String.valueOf(DBHelper.calculateData(query, columnId)), reportName);
    }
}
