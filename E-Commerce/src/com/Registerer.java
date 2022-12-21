package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Registerer {
    public void doRegister(String name, String mail, String pass, Scanner sc) throws Exception {
        Connection con = null;
        PreparedStatement pst = con.prepareStatement("insert into userData(name,mail,pass,isAdmin) values(?,?,?,0);");
        pst.setString(1, name);
        pst.setString(2, mail);
        pst.setString(3, pass);
        int count = pst.executeUpdate();
    }
}
