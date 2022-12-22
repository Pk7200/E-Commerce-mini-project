package com.shopping;

import java.util.Scanner;

import com.admin.IsAdminOrUserChecker;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("1>>Admin ");
        System.out.println("2>>User");
        System.out.println("press 1 for admin press 2 for user");
        int option = sc.nextInt();
        if (option == 1) {
            (new IsAdminOrUserChecker()).adminRun(sc);
        } else if (option == 2) {
            boolean result = RegistrationImpl.controll(sc);// sign in or signup
            if (result == true) {
                ProductList.run(); // show product list
                Cart.run(sc);
                CartList.run(sc);// list of products added into cart
            } else {
                System.out.println("Thank u");
            }
        }

        sc.close();

    }
}
