package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.shopping.ConnectionClassShop;

public class QuantityUpdater {
    public static void quantityUpdate(int in, Scanner sc) throws Exception {
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select p_quantity from product_list where product_id=?");
        ps.setInt(1, in);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int qty = rs.getInt(1);
            if (qty <= 1) {
                quantityInitalizaer(in, sc);
            } else {
                int newQty = qty - 1;

                PreparedStatement ps1 = con.prepareStatement("update product_list set p_quantity=? where product_id=?");
                ps1.setInt(1, newQty);
                ps1.setInt(2, in);
                ps1.executeUpdate();
            }
        }
    }

    public static void quantityInitalizaer(int in, Scanner sc) throws Exception {
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("update product_list set p_quantity=10 where product_id=?");
        ps.setInt(1, in);
        ps.executeUpdate();
    }
}
