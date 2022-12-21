package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.shopping.ConnectionClassShop;

public class IsAdminOrUserChecker {
    public void adminRun(Scanner sc) throws Exception {

        System.out.println("enter email id ");
        String mail = sc.next();
        System.out.println("enter password");
        String pass = sc.next();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement ps = con.prepareStatement("select mail,pass,isAdmin from userdata where mail=?;");
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst() == true) {
            while (rs.next()) {

                if (mail.equals(rs.getString(1)) && pass.equals(rs.getString(2)) && (rs.getInt(3)) == 1) {
                    System.out.println("Sign in successful");
                    (new AdminImpl()).run(sc);

                }
                if (mail.equals(rs.getString(1)) && pass.equals(rs.getString(2)) && (rs.getInt(3)) == 0) {
                    System.out.println("you r not admin contact developer");

                } else if (mail.equals(rs.getString(1)) && pass.equals(rs.getString(2)) == false) {
                    System.out.println("oops wrong password");
                    System.out.println("try again with right password");
                    adminRun(sc);
                }

            }
        } else {
            System.out.println("wrong email");
            System.out.println("1>>continue to admin sign in");
            System.out.println("2>>exit");
            System.out.println("press 1 to continue and 2 to exit");
            int option = sc.nextInt();
            if (option == 1) {
                adminRun(sc);
            }
        }

    }
}
