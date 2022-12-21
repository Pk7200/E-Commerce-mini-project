package com.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Scanner;

import com.QuantityUpdater;

public class Cart {
    // public static int stockChecker(int qty) throws Exception {
    // Connection con = null;
    // int stock = 0;
    // Scanner sc = new Scanner(System.in);

    // // checking stock of particular product
    // con = ConnectionClassShop.getConnection();
    // PreparedStatement ps = con.prepareStatement("select p_quantity from
    // product_list where product_id=?");
    // ps.setInt(1, qty);
    // ResultSet rs = ps.executeQuery();
    // while (rs.next()) {
    // stock = rs.getInt(1);
    // }
    // return stock;
    // }

    public static void addIntoCart(int in, Scanner sc) throws Exception {
        Connection con = null;
        int count = 0;

        con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into cart(product_id) values(?);");
        ps.setInt(1, in);
        count = ps.executeUpdate();

        System.out.println(count + " product is added into ur cart");
        QuantityUpdater.quantityUpdate(in, sc);

    }

    public static void run(Scanner sc) throws Exception {

        int num = 0;

        System.out.println("enter how many products u want to add in cart");
        num = sc.nextInt();

        for (int i = 0; i < num; i++) {
            System.out.println("select product id of product that  u want to add in cart ");

            int in = sc.nextInt();
            if (in <= 10) {
                addIntoCart(in, sc);

            } else {
                System.out.println("invalid input");
                System.out.println("enter valid product id");
                num++;

            }
        }

    }
}
