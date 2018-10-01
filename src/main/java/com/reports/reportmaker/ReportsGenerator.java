package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

import static com.reports.reportmaker.GenerateReportsOptions.generateReportCount;
import static com.reports.reportmaker.GenerateReportsOptions.generateReportList;

public class ReportsGenerator {


    public static void main(String[] args) {


        //data base connection and create table
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

            //run user menu and get selected item
            int userChoice = UserMenu.menu();

            //take the user's choice
            switch (userChoice) {
                case 1:
                    generateReportCount(false, "COUNT", "id");
                    break;
                case 2:
                    generateReportCount(true, "COUNT", "id");
                    break;
                case 3:
                    generateReportCount(false, "SUM", "price");
                    break;
                case 4:
                    generateReportCount(true, "SUM", "price");
                    break;
                case 5:
                    generateReportList(false);
                    break;
                case 6:
                    generateReportList(true);
                    break;
                case 7:
                    generateReportCount(false, "AVG", "price");
                    break;
                case 8:
                    generateReportCount(true, "SUM", "price");
                    break;
                default:
                    System.out.println("There is no such option on the menu");
            }
        }

        //close DB and clear environment
        DBHelper.closeDB();
    }
}
