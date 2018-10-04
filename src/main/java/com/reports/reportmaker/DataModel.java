package com.reports.reportmaker;

import java.math.BigDecimal;

/**
 * Represents one row in the database.
 * - returned by {@link DataLineObj#validateData()}
 */
class DataModel {

    /**
     * Client ID.
     */
    private String mClientID;
    /**
     * Request ID.
     */
    private long mRequestID;
    /**
     * Name of client.
     */
    private String mName;
    /**
     * Quantity of product.
     */
    private int mQuantity;
    /**
     * Price of product.
     */
    private BigDecimal mPrice;

    /**
     * Constructor.
     *
     * @param clientID  client ID (String - no spaces and no more than 6 chars)
     * @param requestID request ID (long)
     * @param name      mName of client (String - no longer than 255 chars)
     * @param quantity  mQuantity of product (int)
     * @param price     mPrice of product (BigDecimal) - cash values
     */
    DataModel(final String clientID,
              final long requestID,
              final String name,
              final int quantity,
              final BigDecimal price) {

        mClientID = clientID;
        mRequestID = requestID;
        mName = name;
        mQuantity = quantity;
        mPrice = price;
    }

    /**
     * @return clinet ID
     */
    String getmClientID() {
        return mClientID;
    }

    /**
     * @return request ID
     */
    long getmRequestID() {
        return mRequestID;
    }

    /**
     * @return mName od client
     */
    String getmName() {
        return mName;
    }

    /**
     * @return mQuantity of product
     */
    int getmQuantity() {
        return mQuantity;
    }

    /**
     * @return mPrice of product
     */
    BigDecimal getmPrice() {
        return mPrice;
    }

}
