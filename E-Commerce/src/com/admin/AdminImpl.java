package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import com.Registerer;
import com.shopping.ConnectionClassShop;
import com.shopping.ProductList;

public class AdminImpl implements Admin {
    public void run(Scanner sc) throws Exception {
        adminFuctions(sc);
    }

    public void adminFuctions(Scanner sc) throws Exception {
        System.out.println("*** list of operations that admin can perform***");
        System.out.println("press 1:product list");
        System.out.println("press 2:add new product");
        System.out.println("press 3:delete particular product from list ");
        System.out.println("press 4:see registered users");
        System.out.println("press 5: remove particular user");
        System.out.println("press 6:make particular user as admin ");
        System.out.println("press 7:see purchase History users ");
        System.out.println("press 8:see purchase History of particular user");

        int option = sc.nextInt();
        switch (option) {
            case 1:
                ProductList.run();
                break;
            case 2:
                addProduct(sc);
                break;
            case 3:
                removeProduct(sc);
                break;
            case 4:
                userList();
                break;
            case 5:
                removeUser(sc);
                break;
            case 6:
                makeAdmin(sc);
                break;
            case 7:
                purchaseHistory(sc);
                break;
            case 8:
                purchaseHistoryOfUser(sc);
                break;
            default:
                System.out.println("enter valid number");
                adminFuctions(sc);
        }

    }

    public void purchaseHistoryOfUser(Scanner sc) throws Exception {
        userList();
        System.out.println("****enter  email Id of user whose purchase history u want to check*****");
        String mail = sc.next();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select p_name,p_desc,p_price from history where mail=?;");
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rmsd = rs.getMetaData();
        int cls = rmsd.getColumnCount();
        System.out.printf("%10s %20s %16s  %n", "NAME", "DESCRIPTION", "PRICE");
        System.out.println(
                "    --------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            for (int i = 1; i <= cls; i++) {
                System.out.print(String.format("%10s", rs.getString(i)) + "      ");

            }
            System.out.println();
            System.out.println(
                    "    --------------------------------------------------------------------------------------------------------");

        }

    }

    public void addProduct(Scanner sc) throws Exception {
        System.out.println("enter product name ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.println("enter product desc in short");
        String desc = sc.nextLine();
        // sc.nextLine();
        System.out.println("enter product price");
        int price = sc.nextInt();
        System.out.println("enter product quantity");
        int qty = sc.nextInt();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con
                .prepareStatement("insert into product_list(p_name,p_desc,p_price,p_quantity) values(?,?,?,?);");
        ps.setString(1, name);
        ps.setString(2, desc);
        ps.setInt(3, price);
        ps.setInt(4, qty);
        int count = ps.executeUpdate();
        System.out.println(count + " is added");

    }

    @Override
    // method to remove product
    public void removeProduct(Scanner sc) throws Exception {
        ProductList.run();
        System.out.println("select id of product u want remove");
        int in = sc.nextInt();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("delete from product_list where id=?");
        ps.setInt(1, in);
        boolean status = ps.execute();
        if (status == true) {
            System.out.println("product removed successfully");
        } else {
            System.out.println("not able to remove, this product may be not there");
        }

    }

    @Override
    public void userList() throws Exception {
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select id,name,mail from userdata;");
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if (rs.isBeforeFirst()) {
            System.out.println("----user list----");
            System.out.printf("%15s %15s %20s %n", "ID", "NAME", "EMAIL-ID");
            System.out.println("    --------------------------------------------------------------------------");
            // System.out.println("id name email");
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(String.format("%15s", rs.getString(i)) + "   ");
                }
                System.out.println();
                System.out.println("    ------------------------------------------------------------------------");
                // System.out.print(rs.getInt(1) + " ");
                // System.out.print(rs.getString(2) + " ");
                // System.out.println(rs.getString(3));

            }
        } else {
            System.out.println("no users are present");
        }
    }

    @Override
    public void removeUser(Scanner sc) throws Exception {
        userList();
        System.out.println("***enter id of user u want remove**");
        int in = sc.nextInt();

        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("delete from userdata where id=?");
        ps.setInt(1, in);
        int count = ps.executeUpdate();
        System.out.println(count + " user is removed");

    }

    @Override
    public void makeAdmin(Scanner sc) throws Exception {
        System.out.println("enter email of user whom u want to make admin");
        String mail = sc.next();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select mail from userdata where mail=?;");
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst() == true) {
            PreparedStatement ps1 = con.prepareStatement("update userdata set isAdmin=1 where mail=?");
            ps1.setString(1, mail);
            int count = ps1.executeUpdate();
            if (count == 1) {
                System.out.println("successful");
            } else {
                System.out.println("unsuccessful");
            }

        } else {
            System.out.println(">>>>user is not registered ");
            System.out.println("if u want to make this user as admin.... enter name of the user");
            String name = sc.next();
            System.out.println("create password");
            String pass = sc.next();
            (new Registerer()).doRegister(name, mail, pass, sc);
            System.out.println("user registered");
            System.out.print("now re ");
            makeAdmin(sc);

        }
    }

    @Override
    public void purchaseHistory(Scanner sc) throws Exception {

        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select p_name,p_desc,p_price,mail from history ;");

        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rmsd = rs.getMetaData();
        int cls = rmsd.getColumnCount();
        System.out.printf("%10s %20s %16s %16s %n", "NAME", "DESCRIPTION", "PRICE", "MAIL");
        System.out.println(
                "    --------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            for (int i = 1; i <= cls; i++) {
                System.out.print(String.format("%10s", rs.getString(i)) + "      ");

            }
            System.out.println();
            System.out.println(
                    "    --------------------------------------------------------------------------------------------------------");

        }

    }
}
