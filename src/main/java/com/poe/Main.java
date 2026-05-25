package com.poe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login authSystem = new Login();
        Message msgSystem = new Message();

        System.out.println("= Registration =");
        System.out.print("First Name: ");
        String fName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lName = scanner.nextLine();

        System.out.print("Username (max 5 chars, must contain '_'): ");
        String username = scanner.nextLine();

        System.out.print("Password (min 8 chars, uppercase, number, symbol): ");
        String password = scanner.nextLine();

        System.out.print("Cell Phone (+27...): ");
        String phone = scanner.nextLine();

        // Registration proccess
        String registrationResult = authSystem.registerUser(username, password, phone);
        System.out.println("\nSystem Response: " + registrationResult);

        if (!registrationResult.equals("User registered successfully.")) {
            System.out.println("Registration failed. Please restart and use valid credentials.");
            return;
        }

        // Store valid authentication data.
        authSystem.setRegistrationData(username, password, fName, lName, phone);

        // Login Proccess
        System.out.println("\n= Login =");
        System.out.print("Username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Password: ");
        String loginPass = scanner.nextLine();

        boolean loggedIn = authSystem.loginUser(loginUser, loginPass);
        System.out.println(authSystem.returnLoginStatus(loggedIn));

        // It only allows loops to proceed if the login is successful.
        if (!loggedIn) {
            System.out.println("Access denied. Please check your credentials.");
            return;
        }

        // Loop principal do Menu da Parte 2
        boolean appRunning = true;
        System.out.println("\nWelcome to QuickChat.");

        while (appRunning) {
            System.out.println("\n-------------------------------------------------");
            System.out.println("Please choose an option from the numeric menu:");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.print("Choice: ");

            String choiceInput = scanner.nextLine();
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (menuChoice) {
                case 1:
                    // Set the number of messages
                    System.out.print("How many messages do you wish to enter? ");
                    int totalLimit;
                    try {
                        totalLimit = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Returning to main menu.");
                        break;
                    }

                    for (int i = 0; i < totalLimit; i++) {
                        System.out.println("\n- Entering Message " + (i + 1) + " of " + totalLimit + " -");

                        // Generation of the unique 10-digit ID
                        String generatedID = Message.generateRandomMessageID();
                        System.out.println("Message ID generated: " + generatedID);

                        // Recipient entry with validation
                        String recipient;
                        while (true) {
                            System.out.print("Recipient Number (+27...): ");
                            recipient = scanner.nextLine();
                            String validationMsg = msgSystem.checkRecipientCell(recipient);
                            System.out.println(validationMsg);
                            if (validationMsg.equals("Cell phone number successfully captured.")) {
                                break;
                            }
                        }

                        // Message input with a 250-character limit validation.
                        String content;
                        while (true) {
                            System.out.print("Message (max 250 characters): ");
                            content = scanner.nextLine();
                            String sizeValidation = msgSystem.checkMessageLength(content);
                            System.out.println(sizeValidation);
                            if (sizeValidation.equals("Message ready to send.")) {
                                break;
                            }
                        }

                        // Generate Message Hash
                        int currentCounter = msgSystem.returnTotalMessagess();
                        String hash = msgSystem.createMessageHash(generatedID, currentCounter, content);
                        System.out.println("Message Hash: " + hash);

                        // Message Action Choice
                        System.out.println("Choose an action for this message:");
                        System.out.println("1 - Send Message");
                        System.out.println("2 - Disregard Message");
                        System.out.println("3 - Store Message to send later");
                        System.out.print("Action: ");

                        int actionChoice;
                        try {
                            actionChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            actionChoice = 2;
                        }

                        Message tempMsg = new Message(generatedID, currentCounter, recipient, content, hash, "");
                        String actionResponse = tempMsg.SentMessage(actionChoice);
                        System.out.println(actionResponse);

                        // Save the message to the app's memory
                        msgSystem.addMessageToStore(tempMsg);

                        // Displays details of the processed message
                        System.out.println("\n- Message Summary Details -");
                        System.out.println("Message ID: " + tempMsg.getMessageID());
                        System.out.println("Message Hash: " + tempMsg.getMessageHash());
                        System.out.println("Recipient: " + tempMsg.getRecipientNumber());
                        System.out.println("Message: " + tempMsg.getMessageContent());
                        System.out.println("-------------------------------");
                    }

                    // Persists all session messages to the JSON file
                    boolean saved = msgSystem.storeMessage();
                    if (saved) {
                        System.out.println("\nAll active messages compiled and stored locally in JSON format!");
                    }

                    System.out.println("Total messages sent / processed: " + msgSystem.returnTotalMessagess());
                    break;

                case 2:
                    System.out.println("\nSystem Response: Coming Soon.");
                    System.out.println(msgSystem.printMessages());
                    break;

                case 3:
                    System.out.println("Goodbye! Exiting QuickChat.");
                    appRunning = false;
                    break;

                default:
                    System.out.println("Invalid selection. Try again.");
                    break;
            }
        }
        scanner.close();
    }
}