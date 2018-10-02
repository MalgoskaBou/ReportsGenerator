package com.reports.reportmaker;

import java.sql.ResultSet;

import static com.reports.reportmaker.UserMenu.getCustomerIdentifier;

class GenerateReportsOptions {


    static void generateReportList(Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        ResultSet rs = DBHelper.showWholeData(query);
        SaveToCsvFile.saveFileFromResultSet(rs);
    }

    static void generateReportCount(Boolean customerIdentifierFilter, String typeOfCount, String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";
        String reportName = "The " + typeOfCount + " of the " + columnId + " of all orders:";

        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
            reportName = reportName + " where clientID is - " + customerIdentifier;
        }
        String res = DBHelper.showCalculateData(query, columnId);
        SaveToCsvFile.saveFileWithASingleValue(String.valueOf(res), reportName);
    }
}
