package com.poe;

import java.util.Scanner;

public class Main  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login app = new Login();

        System.out.println("--- Registration ---");
        System.out.print("Enter First Name: ");
        String fname = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lname = scanner.nextLine();
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter Cell Phone (+27...): ");
        String phone = scanner.nextLine();

        String regMessage = app.registerUser(user, pass, phone);
        System.out.println(regMessage);

        if (regMessage.equals("User registered successfully.")) {
            app.setRegistrationData(user, pass, fname, lname, phone);

            System.out.println("\n--- Login ---");
            System.out.print("Username: ");
            String loginUser = scanner.nextLine();
            System.out.print("Password: ");
            String loginPass = scanner.nextLine();

            boolean success = app.loginUser(loginUser, loginPass);
            System.out.println(app.returnLoginStatus(success));
        }
    }
}