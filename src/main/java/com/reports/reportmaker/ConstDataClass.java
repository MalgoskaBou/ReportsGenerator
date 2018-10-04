package com.reports.reportmaker;

/**
 * Storing constant data.
 */
final class ConstDataClass {

    /**
     * Private constructor.
     */
    private ConstDataClass() {
        //do nothing
        //This prevents the default parameter-less constructor
        //from being used elsewhere in your code.
    }

    /**
     * Database JDBC drivers.
     */
    static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * Database URL and name.
     */
    static final String DB_URL = "jdbc:h2:mem:db1";

    /**
     * Database user name.
     */
    static final String USER = "";

    /**
     * Database user password.
     */
    static final String PASS = "";


    /**
     * xml nodes - client id.
     */
    static final String CLIENT_ID = "clientId";
    /**
     * xml nodes - request id.
     */
    static final String REQUEST_ID = "requestId";
    /**
     * xml nodes - name of client.
     */
    static final String NAME = "name";
    /**
     * xml nodes - quantity of product.
     */
    static final String QUANTITY = "quantity";
    /**
     * xml nodes - price of product.
     */
    static final String PRICE = "price";

    /**
     * xml nodes - root element of xml document.
     */
    static final String ELEMENT = "request";

    /**
     * Max choices in mainMenu.
     */
    static final int MAX_CHOICES_IN_MAIN_MENU = 9;

    /**
     * Max choices in report generate menu.
     */
    static final int MAX_CHOICES_IN_REPORT_GENERATE_MENU = 3;

    /**
     * Max char in user name.
     */
    static final int MAX_CHAR_IN_USER_NAME = 255;

    /**
     * Max chars in client ID.
     */
    static final int MAX_CHAR_IN_USER_ID = 6;

    /**
     * User menu - Total number of orders.
     */
    static final int UM_TOTAL_NUMBER_OF_ORDERS = 1;

    /**
     * User menu - Total number of orders from given Customer ID.
     */
    static final int UM_TOTAL_NUMBER_OF_ORDERS_CID = 2;

    /**
     * User menu - Total amount of orders.
     */
    static final int UM_TOTAL_AMOUNT_OF_ORDERS = 3;

    /**
     * User menu - Total amount of orders from given Customer ID.
     */
    static final int UM_TOTAL_AMOUNT_OF_ORDERS_CID = 4;

    /**
     * User menu - List of all orders.
     */
    static final int UM_LIST_OF_ALL_ORDERS = 5;

    /**
     * User menu - List of all orders from given Customer ID.
     */
    static final int UM_LIST_OF_ALL_ORDERS_CID = 6;

    /**
     * User menu - Average value of all orders.
     */
    static final int UM_AVG_ORDERS = 7;

    /**
     * User menu - Average value of all orders from given Customer ID.
     */
    static final int UM_AVG_ORDERS_CID = 8;

    /**
     * User menu - exit.
     */
    static final int UM_EXIT = 9;


}
