package com.reports.reportmaker;

class DataModel {

    private String clientID;
    private long requestID;
    private String name;
    private int quantity;
    private double price;


    DataModel(String clientID, long requestID, String name, int quantity, double price) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    String getClientID() {
        return clientID;
    }

    long getRequestID() {
        return requestID;
    }

    String getName() {
        return name;
    }

    int getQuantity() {
        return quantity;
    }

    double getPrice() {
        return price;
    }

}
