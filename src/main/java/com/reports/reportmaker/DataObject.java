package com.reports.reportmaker;

class DataObject {

    private String clientID;
    private String requestID;
    private String name;
    private String quantity;
    private String price;
    private String errorIdentyficator;


    DataObject(String clientID, String requestID, String name, String quantity, String price, String errorIdentyficator) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.errorIdentyficator = errorIdentyficator;
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

    String getErrorIdentyficator() {
        return errorIdentyficator;
    }
}
