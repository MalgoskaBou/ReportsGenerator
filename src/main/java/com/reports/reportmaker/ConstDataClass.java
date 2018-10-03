package com.reports.reportmaker;

/**
 * Class for storing constant data
 * Database - database information
 * xml nodes -  xml tree information
 */
final class ConstDataClass {

    /**
     * Database JDBC drivers
     */
    static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * Database URL and name
     */
    static final String DB_URL = "jdbc:h2:mem:db1";

    /**
     * Database user name
     */
    static final String USER = "";

    /**
     * Database user password
     */
    static final String PASS = "";


    /**
     * xml nodes - client id
     */
    static final String CLIENT_ID = "clientId";
    /**
     * xml nodes - request id
     */
    static final String REQUEST_ID = "requestId";
    /**
     * xml nodes - name of client
     */
    static final String NAME = "name";
    /**
     * xml nodes - quantity of product
     */
    static final String QUANTITY = "quantity";
    /**
     * xml nodes - price of product
     */
    static final String PRICE = "price";

    /**
     * xml nodes - root element of xml document
     */
    static final String ELEMENT = "request";

}
