package com.poe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordComplexitySuccess() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityFailure() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneNumberSuccess() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testPhoneNumberFailure() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}