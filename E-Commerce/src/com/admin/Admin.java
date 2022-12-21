package com.admin;

import java.util.Scanner;

public interface Admin {
    public void run(Scanner sc) throws Exception;

    void adminFuctions(Scanner sc) throws Exception;

    void addProduct(Scanner sc) throws Exception;

    void removeProduct(Scanner sc) throws Exception;

    void userList() throws Exception;

    void removeUser(Scanner sc) throws Exception;

    void makeAdmin(Scanner sc) throws Exception;

    void purchaseHistory(Scanner sc) throws Exception;

    void purchaseHistoryOfUser(Scanner sc) throws Exception;
}
