package com.reports.reportmaker;

import java.sql.ResultSet;

import static com.reports.reportmaker.DBHelper.getDataByQueryFunction;
import static com.reports.reportmaker.DBHelper.getDataByQuery;
import static com.reports.reportmaker.SaveToCsvFile.*;
import static com.reports.reportmaker.UserMenu.getCustomerIdentifier;

//todo reformat
class GenerateReportsOptions {


    static void generateReportList(Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        ResultSet rs = getDataByQuery(query);
        saveFileFromResultSet(rs);
    }

    static void generateReportCount(Boolean customerIdentifierFilter, String typeOfCount, String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";
        String reportName = "The " + typeOfCount + " of the " + columnId + " of all orders:";

        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
            reportName = reportName + " where clientID is - " + customerIdentifier;
        }
        String res = getDataByQueryFunction(query, columnId);
        saveFileWithASingleValue(String.valueOf(res), reportName);
    }

    static void showData(ResultSet rs) {
        try {
            while (rs.next()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
