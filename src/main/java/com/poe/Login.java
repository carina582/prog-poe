package com.poe;

import java.util.regex.Pattern;

/**
 * Classe responsável por gerenciar o registro e login de usuários
 * conforme os requisitos de validação técnica.
 */
public class Login {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;

    // Getters e Setters para facilitar o teste e uso
    public void setRegistrationData(String username, String password, String firstName, String lastName, String phone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhoneNumber = phone;
    }

    /**
     * Verifica se o username contém um underline e tem no máximo 5 caracteres.
     */
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Verifica a complexidade da senha:
     * - Mínimo 8 caracteres
     * - Letra maiúscula
     * - Número
     * - Caractere especial
     */
    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (password.length() < 8) return false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Valida o número de telefone usando Regex.
     * Deve conter código internacional e não ter mais de 10 dígitos após o código.
     */
    public boolean checkCellPhoneNumber(String phoneNumber) {
        // Regex simplificada para validar código internacional (ex: +27) e dígitos
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return Pattern.matches(regex, phoneNumber);
    }

    /**
     * Retorna mensagens de status baseadas na validação dos campos.
     */
    public String registerUser(String username, String password, String phone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User registered successfully.";
    }

    /**
     * Verifica se os dados de login batem com os registrados.
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(this.username) && enteredPassword.equals(this.password);
    }

    /**
     * Retorna a mensagem de status final do login.
     */
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}