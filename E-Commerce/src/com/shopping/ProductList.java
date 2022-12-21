package com.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ProductList {
    public static void run() throws Exception {
        Connection con = ConnectionClassShop.getConnection();
        System.out.println("here is the list of product>>>>>");
        System.out.println(
                "    --------------------------------------------------------------------------------------------------------");
        System.out.printf("%14s %15s %20s %16s %21s %n", "ID", "NAME", "DESCRIPTION", "PRICE", "QUANTITY");
        System.out.println(
                "    --------------------------------------------------------------------------------------------------------");
        // System.out.println("id name Description price qty");
        PreparedStatement pst = con.prepareStatement("select*from product_list;");
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(String.format("%18s", rs.getString(i) + "     "));// print one element of row
            }
            System.out.println("");
            System.out.println(
                    "       -------------------------------------------------------------------------------------------------");
            // System.out.print(rs.getInt(1) + " ");
            // System.out.print(rs.getString(2) + " ");
            // System.out.print(rs.getString(3) + " ");
            // System.out.print(rs.getInt(4) + " ");
            // System.out.println(rs.getInt(5));
        }

    }

}
