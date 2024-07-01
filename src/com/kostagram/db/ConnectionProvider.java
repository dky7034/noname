package com.kostagram.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectionProvider {

    //static method, no parameter, returns connection, and name to 'getConnection'
    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:oracle:thin:@localhost:XE";
        String username = "c##noname";
        String password = "noname";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //close Connection, Statement, ResultSet parameters
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rslt) {
        try {
            if (rslt != null) {
                rslt.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //close Connection, Statement parameters
    public static void closeConnection(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
