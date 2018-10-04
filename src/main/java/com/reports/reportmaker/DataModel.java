package com.reports.reportmaker;

import java.math.BigDecimal;

/**
 * Represents one row in the database
 * - returned by {@link DataLineObj#validateData()}
 */
class DataModel {

    private String clientID;
    private long requestID;
    private String name;
    private int quantity;
    private BigDecimal price;

    /**
     * Constructor
     *
     * @param clientID  client ID (String - no spaces and no more than 6 chars)
     * @param requestID request ID (long)
     * @param name      name of client (String - no longer than 255 chars)
     * @param quantity  quantity of product (int)
     * @param price     price of product (double)
     */
    DataModel(String clientID, long requestID, String name, int quantity, BigDecimal price) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * @return clinet ID
     */
    String getClientID() {
        return clientID;
    }

    /**
     * @return request ID
     */
    long getRequestID() {
        return requestID;
    }

    /**
     * @return name od client
     */
    String getName() {
        return name;
    }

    /**
     * @return quantity of product
     */
    int getQuantity() {
        return quantity;
    }

    /**
     * @return price of product
     */
    BigDecimal getPrice() {
        return price;
    }

}
