package com.shopping;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClassShop {
    private static Connection con;
    private static final String url = "jdbc:mysql://localhost:3306/shop";
    private static final String user = "root";
    private static final String password = "Prithvi@7200";
    private static final String className = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {

        try {
            // loading driver class
            if (con == null) {
                Class.forName(className);
            }

            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;

    }
}
