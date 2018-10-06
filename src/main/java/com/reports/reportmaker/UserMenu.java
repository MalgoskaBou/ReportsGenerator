package com.reports.reportmaker;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.reports.reportmaker.ConstDataClass.MAX_CHAR_IN_USER_ID;
import static com.reports.reportmaker.ConstDataClass.MAX_CHOICES_IN_MAIN_MENU;
import static com.reports.reportmaker.ConstDataClass.MAX_CHOICES_IN_REPORT_GENERATE_MENU;

/**
 * menuMain support in the console.
 */
final class UserMenu {

    /**
     * Private constructor.
     */
    private UserMenu() {
        // do nothing
        // This prevents the default parameter-less
        // constructor from being used elsewhere in your code.
    }

    /**
     * Main menuMain.
     *
     * @return selected value by the user (int)
     */
    static int menuMain() {

        System.out.println("Choose report to generate and push enter");
        System.out.println("-------------------------\n");
        System.out.println("1 - Total number of orders");
        System.out.println("2 - Number of orders from the customer with the given identifier");
        System.out.println("3 - Total amount of orders");
        System.out.println("4 - The total amount of orders to the customer with the indicated identifier");
        System.out.println("5 - List of all orders");
        System.out.println("6 - List of orders from the customer with the given identifier");
        System.out.println("7 - Average value of the order");
        System.out.println("8 - Average value of the order from the customer with the given identifier");
        System.out.println("9 - exit");

        System.out.print("Choose report to generate by entering a number from the menuMain: ");

        int choice = getIntChoice();
        while (choice > MAX_CHOICES_IN_MAIN_MENU || choice <= 0) {
            System.out.print("There is no such option! Try one more time or choose 9 to exit: ");
            choice = getIntChoice();
        }

        return choice;
    }

    //todo only characters input - now I can put . , )...
    //todo - if(returned choice is not in database) Goodbye!

    /**
     * additional information about customer ID.
     *
     * @return selected customer ID by user (String)
     */
    static String menuGetCustomerIdentifier() {

        System.out.println("Choose client ID: ");

        String choice = getStringChoice();
        while (choice.length() > MAX_CHAR_IN_USER_ID
                || choice.contains(" ")) {
            System.out.print("Client ID can have only 6 chars without spaces and special characters: ");
            choice = getStringChoice();
        }
        return choice;
    }

    /**
     * additional information about report to generate.
     *
     * @return selected option in menu (int)
     */
    static int menuGenerateReportOptions() {

        System.out.println("1 - Save report to file");
        System.out.println("2 - Show report");
        System.out.println("3 - Save to file and show");

        int choice = getIntChoice();
        while (choice > MAX_CHOICES_IN_REPORT_GENERATE_MENU || choice <= 0) {
            System.out.print("There is no such option! Try one more time: ");
            choice = getIntChoice();
        }
        return choice;
    }

    /**
     * Get user choice in String.
     *
     * @return user choice in string
     */
    private static String getStringChoice() {
        String selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextLine();
        return selection;
    }

    /**
     * Get user choice in int.
     *
     * @return user choice in int or 0 if catch InputMismatchException
     * @throws InputMismatchException secured
     */
    private static int getIntChoice() {
        int selection = 0;
        Scanner input = new Scanner(System.in);
        try {
            selection = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.print("Only integers, ");
        }
        return selection;
    }
}
