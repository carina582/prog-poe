# Student

Carina dos Santos - ST10536970 - PROG5121 - DISD0601

# Overview

QuickChat is a Java-based console application that simulates a secure user authentication system and a message management platform. It allows users to register with strict credential validation, log in securely, and process, store, and export messages locally using JSON.

# Features

- Secure User Registration & Authentication:
  - Validates username constraints (max 5 characters, must contain an underscore _).
  - Enforces password complexity (minimum 8 characters, at least one uppercase letter, one number, and one special character).
  - Validates international phone numbers using Regular Expressions (Regex).

- Message Processing:
  - Generates a unique 10-digit random Message ID.
  - Validates recipient phone numbers.
  - Enforces a 250-character limit on message content.
  - Generates a unique Message Hash based on the message ID, sequence number, and concatenated first and last words of the content.

- Data Persistence:
  - Session messages are kept in an in-memory list.
  - Messages can be exported and saved to a local messages.json file using the Google Gson library.
 
# Project Structure
- Main.java: The driver of the application, managing the CLI menus, user input via Scanner, and application flow.

- Login.java: Handles the logic for user registration, authentication, and credential validation (Regex and string manipulation).

- Message.java: Manages message creation, tracking, hashing, and JSON persistence using Gson

# Reference

Google, 2023. Gson (Version 2.10.1) [Source code]. Available at: https://github.com/google/gson [Accessed 25 May 2026].

Oracle, 2026. Java Platform, Standard Edition 17 API Specification. [online] Available at: https://docs.oracle.com/en/java/javase/17/docs/api/index.html [Accessed 25 May 2026].

Oracle, 2026. Lesson: Regular Expressions. [online] Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 25 May 2026].
