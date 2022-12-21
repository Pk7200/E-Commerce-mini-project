package com.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Scanner;

public class CartList {
    public static void run(Scanner sc) throws Exception {
        ArrayList al1 = new ArrayList<>();
        Connection con = null;
        con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "select product_list.product_id,p_name,p_desc,p_price from product_list inner join cart on product_list.product_id=cart.product_id;");
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst() == true) {
            while (rs.next()) {
                ArrayList al = new ArrayList<>();
                al.add(rs.getString(2));
                al.add(rs.getInt(4));
                al.trimToSize();
                al1.add(al);

            }
        }
        for (int i = 0; i < al1.size(); i++) {
            System.out.println(al1.get(i));
        }
        System.out.println();
        System.out.println("cart value is");

        PreparedStatement ps1 = con.prepareStatement(
                "select sum(product_list.p_price) from product_list inner join cart on product_list.product_id=cart.product_id;");
        ResultSet rs1 = ps1.executeQuery();
        while (rs1.next()) {
            System.out.println("------------------------------------");
            System.out.println("----------" + rs1.getInt(1));
            System.out.println("------------------------------------");
        }
        System.out.println("place order  yes/no");
        String s = sc.next();
        String name = null;
        String desc = null;
        int price = 0;
        if (s.equals("yes")) {

            PreparedStatement ps7 = con.prepareStatement(
                    "select product_list.p_name,p_desc,p_price from product_list inner join cart on product_list.product_id=cart.product_id;");
            ResultSet rs3 = ps7.executeQuery();

            if (rs3.isBeforeFirst() == true) {
                while (rs3.next()) {
                    name = rs3.getString(1);
                    desc = rs3.getString(2);
                    price = rs3.getInt(3);
                    PreparedStatement ps6 = con.prepareStatement(
                            "insert into history(p_name,p_desc,p_price,mail) values(?,?,?,?);");
                    ps6.setString(1, name);
                    ps6.setString(2, desc);
                    ps6.setInt(3, price);
                    ps6.setString(4, RegistrationImpl.mail);
                    ps6.execute();

                    ResultSet rs2 = ps.executeQuery();

                }
            }

            System.out.println("congratulations order placed");
            System.out.println("-----Thanks for shopping with us----");
            PreparedStatement ps2 = con.prepareStatement("truncate table cart;");
            ps2.execute();

        } else if (s.equals("no")) {
            PreparedStatement ps2 = con.prepareStatement("truncate table cart;");
            ps2.execute();
            System.out.println("-----Thanks for shopping with us----");
        }

    }
}
