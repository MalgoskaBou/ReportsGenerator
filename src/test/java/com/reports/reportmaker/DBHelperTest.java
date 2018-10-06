package com.reports.reportmaker;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

import static com.reports.reportmaker.ConstDataClass.*;
import static org.junit.Assert.*;

public class DBHelperTest {

    private static Connection conn;
    private static Statement stmt;

    @BeforeClass
    public static void openDB() throws SQLException {
        DBHelper.openDBConnection();

        conn = DriverManager.getConnection(ConstDataClass.DB_URL,
                ConstDataClass.USER, ConstDataClass.PASS);
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
    }

    @Test
    public void testOpenDBConnection() {
        assertNotNull(conn);
    }

    @Test
    public void testCreateTable() throws SQLException {
        DBHelper.createTable();

        //todo check how to access to columns - you have this in your programm
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getColumns(null, null, TABLE_NAME, null);
        String expected[] = {ID, CLIENT_ID, REQUEST_ID, NAME, QUANTITY, PRICE};
        int iterator = 0;
        while (rs.next()) {
            assertEquals(expected[iterator].toUpperCase(), rs.getString("COLUMN_NAME"));
            iterator++;
        }
    }

    @Test
    public void testInsertData() {
    }

    @Test
    public void testGetData() {
    }

    @Test
    public void testCloseDB() {
        DBHelper.closeDB();

    }

    @AfterClass
    static public void closeDB() {
        DBHelper.closeDB();
    }
}