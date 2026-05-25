package com.poe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {

    private String messageID;
    private int numMessagesSent;
    private String recipientNumber;
    private String messageContent;
    private String messageHash;
    private String deliveryStatus;

    // Static list for tracking messages sent during execution.
    private static List<Message> messageStore = new ArrayList<>();

    // Construtor
    public Message() {}

    public Message(String messageID, int numMessagesSent, String recipientNumber, String messageContent, String messageHash, String deliveryStatus) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipientNumber = recipientNumber;
        this.messageContent = messageContent;
        this.messageHash = messageHash;
        this.deliveryStatus = deliveryStatus;
    }

    // Getters and Setters for general use and unit testing
    public String getMessageID() { return messageID; }
    public int getNumMessagesSent() { return numMessagesSent; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public String getDeliveryStatus() { return deliveryStatus; }

    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
    public void setRecipientNumber(String recipientNumber) { this.recipientNumber = recipientNumber; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setNumMessagesSent(int numMessagesSent) { this.numMessagesSent = numMessagesSent; }

    // Checks if the message does not exceed 250 characters
    public String checkMessageLength(String message) {
        if (message == null) return "Please enter a message of less than 250 characters.";
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceededBy = message.length() - 250;
            return "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
        }
    }

    // Checks if the generated ID has a maximum of 10 characters.
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    // Check if the recipient's number meets the requirements.
    public String checkRecipientCell(String cellNumber) {
        if (cellNumber == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }

        // checks the format with a '+' sign followed by the country's area code and the number.
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        boolean isValid = Pattern.matches(regex, cellNumber);

        if (isValid) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Creates and returns the message hash.
    public String createMessageHash(String id, int msgNum, String messageText) {
        if (id == null || id.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "00:0:INVALID";
        }

        String firstTwoId = id.substring(0, 2);

        String cleaned = messageText.trim().replaceAll("[\\?\\!\\.\\,\\;]", "");
        String[] words = cleaned.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String combinedWords = (firstWord + lastWord).toUpperCase();

        return firstTwoId + ":" + msgNum + ":" + combinedWords;
    }

    // Allows the user to choose whether to send the message.
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.deliveryStatus = "Sent";
                return "Message successfully sent.";
            case 2:
                this.deliveryStatus = "Discarded";
                return "Press 0 to delete the message.";
            case 3:
                this.deliveryStatus = "Stored";
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    // Returns a String containing all the formatted data from the messages sent during the session.
    public String printMessages() {
        if (messageStore.isEmpty()) {
            return "No messages sent yet.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("= ALL MESSAGES SENT =\n");
        for (Message msg : messageStore) {
            builder.append("Message ID: ").append(msg.getMessageID()).append("\n");
            builder.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
            builder.append("Recipient: ").append(msg.getRecipientNumber()).append("\n");
            builder.append("Message Content: ").append(msg.getMessageContent()).append("\n");
            builder.append("Delivery Decision: ").append(msg.getDeliveryStatus()).append("\n");
            builder.append("---------------------------------------------------\n");
        }
        return builder.toString();
    }

    // Returns the total number of messages registered in the list.
    public int returnTotalMessagess() {
        return messageStore.size();
    }

    // Adds a completion message to the session's in-memory repository.
    public void addMessageToStore(Message msg) {
        messageStore.add(msg);
    }

    // Search method for persisting messages in a JSON file using Gson.
    public boolean storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File("messages.json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(messageStore, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving JSON file: " + e.getMessage());
            return false;
        }
    }

     // generates a random 10-digit number for the message ID..
    public static String generateRandomMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
}