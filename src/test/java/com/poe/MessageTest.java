package com.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {

    private Message messageValidator;

    @BeforeEach
    public void setUp() {
        messageValidator = new Message();
    }

    @Test
    public void testCheckMessageLengthSuccess() {
        String testMessage = "Hi Mike, can you join us for dinner tonight?";
        String expected = "Message ready to send.";
        assertEquals(expected, messageValidator.checkMessageLength(testMessage));
    }

    @Test
    public void testCheckMessageLengthFailure() {
        // 260 'a's
        String longMessage = "a".repeat(260);
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expected, messageValidator.checkMessageLength(longMessage));
    }

    @Test
    public void testCheckRecipientCellSuccess() {
        String validNumber = "+27718693002";
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, messageValidator.checkRecipientCell(validNumber));
    }

    @Test
    public void testCheckRecipientCellFailure() {
        String invalidNumber = "08575975889";
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expected, messageValidator.checkRecipientCell(invalidNumber));
    }

    @Test
    public void testCreateMessageHashSuccess() {
        String mockID = "0012345678";
        int messageCount = 0;
        String messageText = "Hi Mike, can you join us for dinner tonight?";

        String expectedHash = "00:0:HITONIGHT";
        String actualHash = messageValidator.createMessageHash(mockID, messageCount, messageText);

        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testMessageIDCreated() {
        String randomID = Message.generateRandomMessageID();
        assertTrue(messageValidator.checkMessageID(randomID));
        assertEquals(10, randomID.length());
    }

    @Test
    public void testSentMessageChoices() {
        Message testMsg = new Message();

        // Send Message
        assertEquals("Message successfully sent.", testMsg.SentMessage(1));
        assertEquals("Sent", testMsg.getDeliveryStatus());

        // Disregard Message
        assertEquals("Press 0 to delete the message.", testMsg.SentMessage(2));
        assertEquals("Discarded", testMsg.getDeliveryStatus());

        // Store Message
        assertEquals("Message successfully stored.", testMsg.SentMessage(3));
        assertEquals("Stored", testMsg.getDeliveryStatus());
    }
}