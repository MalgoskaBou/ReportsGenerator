package com.reports.reportmaker;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import static com.reports.reportmaker.UserMenu.getCustomerIdentifier;

class GenerateReportsOptions {

    /**
     * Generate query with function
     *
     * @param customerIdentifierFilter if need custom ID filter
     * @param typeOfCount              type of function (SUM, COUNT, AVG)
     * @param columnId                 column on which operations will be performed
     * @return query as a String
     */
    static String generateQuery(Boolean customerIdentifierFilter, String typeOfCount, String columnId) {
        String query = "SELECT " + typeOfCount + " (" + columnId + ") AS " + columnId + " FROM RAPPORTS";

        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    /**
     * Generate query for whole columns
     *
     * @param customerIdentifierFilter if need custom ID filter
     * @return query as a String
     */
    static String generateQuery(Boolean customerIdentifierFilter) {
        String query = "SELECT * FROM RAPPORTS";
        if (customerIdentifierFilter) {
            String customerIdentifier = getCustomerIdentifier();
            query = query + " WHERE clientId='" + customerIdentifier + "'";
        }
        return query;
    }

    static void showData(ResultSet rs) {
        try {

            //reset cursor for any case
            rs.beforeFirst();

            //get number of columns from metadata
            ResultSetMetaData metaDataRs = rs.getMetaData();
            int numberOfColumns = metaDataRs.getColumnCount();

            //show headers
            for (int i = 0; i < numberOfColumns; ++i) {

                System.out.print(metaDataRs.getColumnName(i + 1) + ", ");
            }
            System.out.println();

            //show data
            while (rs.next()) {

                for (int i = 0; i < numberOfColumns; ++i) {

                    System.out.print(rs.getString(i + 1) + ", ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
