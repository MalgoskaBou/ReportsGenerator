package com.reports.reportmaker;

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

    static String getCustomerIdentifier() {

        String selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose client identifier: ");

        selection = input.next();
        return selection;
    }
}
