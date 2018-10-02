package com.reports.reportmaker;

class DataLineObj {

    private String clientID;
    private String requestID;
    private String name;
    private String quantity;
    private String price;
    private String errorIdent;


    DataLineObj(String clientID, String requestID, String name, String quantity, String price, String errorIdent) {
        this.clientID = clientID;
        this.requestID = requestID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.errorIdent = errorIdent;
    }


    DataModel validateData() {

        try {
            //parse strings to correct formats and delete white-spaces from clientID
            String validateClientID = clientID.replaceAll("\\s", "");
            long validateRequestID = Long.parseLong(requestID);
            int validateQuantity = Integer.parseInt(quantity);

            //makes sure that the entry price has 2 decimal places - I use ROUND_HALF_UP!!!
            double validatePrice = Double.parseDouble(price);
            validatePrice = Math.round(validatePrice * 100) / 100D;


            //checking if these values are within the varchar range
            if (validateClientID.length() > 6) {
                throw new Exception("too long clientID");
            }
            if (name.length() > 255) {
                throw new Exception("too long name of product");
            }

            return new DataModel(validateClientID, validateRequestID, name, validateQuantity, validatePrice);

        } catch (Exception e) {
            //throw parse exception for wrong format data, and too long value in varChar
            System.out.println(requestID + " probably wrong data format in: " + errorIdent);
            e.printStackTrace();
        }

        return null;
    }
}
