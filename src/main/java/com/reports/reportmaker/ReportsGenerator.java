package com.reports.reportmaker;

import org.apache.commons.io.FilenameUtils;

import static com.reports.reportmaker.ConstDataClass.UM_AVG_ORDERS;
import static com.reports.reportmaker.ConstDataClass.UM_LIST_OF_ALL_ORDERS;
import static com.reports.reportmaker.ConstDataClass.UM_LIST_OF_ALL_ORDERS_CID;
import static com.reports.reportmaker.ConstDataClass.UM_AVG_ORDERS_CID;
import static com.reports.reportmaker.ConstDataClass.UM_EXIT;
import static com.reports.reportmaker.ConstDataClass.UM_TOTAL_AMOUNT_OF_ORDERS;
import static com.reports.reportmaker.ConstDataClass.UM_TOTAL_AMOUNT_OF_ORDERS_CID;
import static com.reports.reportmaker.ConstDataClass.UM_TOTAL_NUMBER_OF_ORDERS;
import static com.reports.reportmaker.ConstDataClass.UM_TOTAL_NUMBER_OF_ORDERS_CID;

import static com.reports.reportmaker.DBHelper.createTable;
import static com.reports.reportmaker.DBHelper.getData;
import static com.reports.reportmaker.DBHelper.insertData;
import static com.reports.reportmaker.DBHelper.closeDB;
import static com.reports.reportmaker.DBHelper.openDBConnection;
import static com.reports.reportmaker.GenerateReportsOptions.generateQuery;
import static com.reports.reportmaker.GenerateReportsOptions.generateReport;
import static com.reports.reportmaker.ReadFile.readDataFromCSV;
import static com.reports.reportmaker.ReadFile.readDataFromXML;


/**
 * Main.
 */
//@SpringBootApplication
final class ReportsGenerator {

    /**
     * Private constructor.
     */
    private ReportsGenerator() {
        // do nothing
        // This prevents the default parameter-less
        // constructor from being used elsewhere in your code.
    }

    /**
     * Main class.
     *
     * @param args arguments for class
     */
    public static void main(final String[] args) {
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


            //run user menuMain and get selected item
            int userChoice = UserMenu.menuMain();
            String query;
            //take the user's choice
            switch (userChoice) {
                case UM_TOTAL_NUMBER_OF_ORDERS:
                    query = generateQuery(false, "COUNT", "id");
                    generateReport(getData(query), true, true);
                    break;
                case UM_TOTAL_NUMBER_OF_ORDERS_CID:
                    query = generateQuery(true, "COUNT", "id");
                    generateReport(getData(query), true, true);
                    break;
                case UM_TOTAL_AMOUNT_OF_ORDERS:
                    query = generateQuery(false, "SUM", "price");
                    generateReport(getData(query), true, true);
                    break;
                case UM_TOTAL_AMOUNT_OF_ORDERS_CID:
                    query = generateQuery(true, "SUM", "price");
                    generateReport(getData(query), true, true);
                    break;
                case UM_LIST_OF_ALL_ORDERS:
                    query = generateQuery(false);
                    generateReport(getData(query), true, true);

                    break;
                case UM_LIST_OF_ALL_ORDERS_CID:
                    query = generateQuery(true);
                    generateReport(getData(query), true, true);
                    break;
                case UM_AVG_ORDERS:
                    query = generateQuery(false, "AVG", "price");
                    generateReport(getData(query), true, true);
                    break;
                case UM_AVG_ORDERS_CID:
                    query = generateQuery(true, "AVG", "price");
                    generateReport(getData(query), true, true);
                    break;
                case UM_EXIT:
                    //do nothing - just waiting for close the database
                    break;
                default: //do nothing
                    break;

            }
        }

        //close DB and clear environment
        closeDB();
    }
}
