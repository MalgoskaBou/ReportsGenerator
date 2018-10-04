package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

import static com.reports.reportmaker.DBHelper.*;
import static com.reports.reportmaker.GenerateReportsOptions.*;
import static com.reports.reportmaker.ReadFile.*;

/**
 * Main
 */
//@SpringBootApplication
public class ReportsGenerator {

    public static void main(String[] args) {
        //SpringApplication.run(ReportsGenerator.class, args);

        openDBConnection();
        createTable();

        System.out.println("There are " + args.length + " files to load\n");

        if (args.length > 0) {
            //load files
            for (String arg : args) {
                System.out.print("Loading file: " + arg + "\n");
                switch (FilenameUtils.getExtension(arg)) {
                    case "xml":
                        insertData(readDataFromXML(arg));
                        //saveData
                        break;
                    case "csv":
                        insertData(readDataFromCSV(arg));
                        break;
                    default:
                        System.out.println("Wrong file format - expected .csv or .xml");
                        break;
                }
            }

            //run user menu and get selected item
            int userChoice = UserMenu.menu();
            String query;
            //take the user's choice
            switch (userChoice) {
                case 1:
                    query = generateQuery(false, "COUNT", "id");
                    generateReport(getData(query), true, true);
                    //generateReportCount(false, "COUNT", "id");
                    break;
                case 2:
                    query = generateQuery(true, "COUNT", "id");
                    generateReport(getData(query), true, true);
                    break;
                case 3:
                    query = generateQuery(false, "SUM", "price");
                    generateReport(getData(query), true, true);
                    break;
                case 4:
                    query = generateQuery(true, "SUM", "price");
                    generateReport(getData(query), true, true);
                    break;
                case 5:
                    query = generateQuery(false);
                    generateReport(getData(query), true, true);

                    break;
                case 6:
                    query = generateQuery(true);
                    generateReport(getData(query), true, true);
                    break;
                case 7:
                    query = generateQuery(false, "AVG", "price");
                    generateReport(getData(query), true, true);
                    break;
                case 8:
                    query = generateQuery(true, "SUM", "price");
                    generateReport(getData(query), true, true);
                    break;
                default:
                    System.out.println("There is no such option on the menu");
            }
        }

        //close DB and clear environment
        closeDB();
    }
}
