package com.reports.reportmaker;

class DataModel {

    private String clientID;
    private String requestID;
    private String name;
    private String quantity;
    private String price;


    DataModel(String clientID, String requestID, String name, String quantity, String price) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    String getClientID() {
        return clientID;
    }

    String getRequestID() {
        return requestID;
    }

    String getName() {
        return name;
    }

    String getQuantity() {
        return quantity;
    }

    String getPrice() {
        return price;
    }
}
