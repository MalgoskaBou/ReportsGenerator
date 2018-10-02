package com.reports.reportmaker;

class DataModel {

    private String clientID;
    private long requestID;
    private String name;
    private int quantity;
    private double price;


    public DataModel(String clientID, long requestID, String name, int quantity, double price) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
