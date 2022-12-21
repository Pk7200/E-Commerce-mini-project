package com.shopping;

import java.lang.Thread.State;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RegistrationImpl {
    static String mail;

    public static boolean reg(Scanner sc) throws Exception {
        boolean result = false;

        System.out.println("enter your name");
        String name = sc.next();
        System.out.println("enter your email id");
        mail = sc.next();
        System.out.println("enter password ");
        String pass = sc.next();

        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement psp = con.prepareStatement("select mail from userData where mail=?");
        psp.setString(1, mail);
        ResultSet rs = psp.executeQuery();
        if (rs.isBeforeFirst()) {
            System.out.println("this email id already present");
            System.out.println("plz Sign in");
            result = check(sc);
        } else {
            PreparedStatement pst = con
                    .prepareStatement("insert into userData(name,mail,pass,isAdmin) values(?,?,?,0);");
            pst.setString(1, name);
            pst.setString(2, mail);
            pst.setString(3, pass);
            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println("Sign Up successful");
                result = true;
            }

            System.out.println("sign In now");
            result = RegistrationImpl.check(sc);// calling for sign in
        }

        return result;
    }

    // method to check whether user info is in database or not
    private static boolean check(Scanner sc) throws Exception {
        String mailDb = null;
        String passDb = null;

        boolean result = false;
        System.out.println("enter your email id");
        mail = sc.next();
        System.out.println("enter password ");
        String pass = sc.next();
        Connection con = ConnectionClassShop.getConnection();
        PreparedStatement pst = con.prepareStatement("select mail,pass from userData where mail=? and pass=?");
        pst.setString(1, mail);
        pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                mailDb = rs.getString(1);
                passDb = rs.getString(2);

            }

            if (mail.equals(mailDb) || pass.equals(passDb)) {
                System.out.println("Sign in Successful");
                result = true;
            } else {
                System.out.println("Either mail id or password is wrong");
                System.out.println("continue");
                System.out.println("exit");
                System.out.println("press 1 to continue and 2 to exit");
                int in = sc.nextInt();
                if (in == 1) {
                    RegistrationImpl.check(sc);
                } else if (in == 2) {
                    System.out.println("Thank u Visit again");
                    result = false;
                }
            }
        } else if (!rs.isBeforeFirst()) {
            System.out.println("your are not registered Sign Up first");
            System.out.println("continue");
            System.out.println("exit");
            System.out.println("press 1 to continue and 2 to exit");
            int in = sc.nextInt();
            if (in == 1) {
                reg(sc);
            } else {
                result = false;
            }
        }

        return result;
    }

    public static boolean controll(Scanner sc) throws Exception {

        boolean result = false;

        System.out.println("choose below");
        System.out.println("1>>Sign Up");
        System.out.println("2>>Sign In");
        System.out.println("press 1 for sign up press 2 for sign in");
        int in = sc.nextInt();
        if (in == 1) {

            result = RegistrationImpl.reg(sc);
        } else if (in == 2) {

            result = RegistrationImpl.check(sc);
        }

        // System.out.println("successful");

        return result;
    }

}
