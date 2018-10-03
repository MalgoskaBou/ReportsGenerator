package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

import static com.reports.reportmaker.DBHelper.*;
import static com.reports.reportmaker.GenerateReportsOptions.*;
import static com.reports.reportmaker.SaveToCsvFile.saveFileFromResultSet;

/**
 * Main
 */
//@SpringBootApplication
public class ReportsGenerator {

    public static void main(String[] args) {
        //SpringApplication.run(ReportsGenerator.class, args);

        openDBConnection();
        createTable();
        ReadFile dataReader = new ReadFile();

        System.out.println("There are " + args.length + " files to load\n");

        if (args.length > 0) {
            //load files
            for (String arg : args) {
                System.out.print("Loading file: " + arg + "\n");
                switch (FilenameUtils.getExtension(arg)) {
                    case "xml":
                        insertData(dataReader.readDataFromXML(arg));
                        //saveData
                        break;
                    case "csv":
                        insertData(dataReader.readDataFromCSV(arg));
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
                    showData(getData(query));
                    saveFileFromResultSet(getData(query));
                    //generateReportCount(false, "COUNT", "id");
                    break;
                case 2:
                    query = generateQuery(true, "COUNT", "id");
                    showData(getData(query));
                    break;
                case 3:
                    query = generateQuery(false, "SUM", "price");
                    showData(getData(query));
                    break;
                case 4:
                    query = generateQuery(true, "SUM", "price");
                    showData(getData(query));
                    break;
                case 5:
                    query = generateQuery(false);
                    showData(getData(query));
                    saveFileFromResultSet(getData(query));
                    break;
                case 6:
                    query = generateQuery(true);
                    showData(getData(query));
                    break;
                case 7:
                    query = generateQuery(false, "AVG", "price");
                    showData(getData(query));
                    break;
                case 8:
                    query = generateQuery(true, "SUM", "price");
                    showData(getData(query));
                    break;
                default:
                    System.out.println("There is no such option on the menu");
            }
        }

        //close DB and clear environment
        closeDB();
    }
}
