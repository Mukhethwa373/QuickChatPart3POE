/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchatpart3poe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
/**
 *
 * @author Student
 */
public class Part3Test {

    private QuickChatPart3POE chat;

    @BeforeEach
    public void setUp() {
        chat = new QuickChatPart3POE();
        chat.addSampleMessages();
    }

    // Test 1: Sent messages array
    @Test
    public void testSentMessagesArray() {

        Part3Message[] sent = chat.getSentMessages();

        assertEquals(2, sent.length);
        assertEquals("Did you get the cake?",
                sent[0].getMessage());
        assertEquals("It is dinner time!",
                sent[1].getMessage());
    }

    // Test 2: Longest stored message
    @Test
    public void testLongestMessage() {

        String expected =
                "Where are you? You are late! I have asked you to be on time.";

        assertEquals(expected, chat.getLongestMessage());
    }

    // Test 3: Search using the supplied number
    @Test
    public void testSearchMessageID() {

        Part3Message result = chat.searchMessageID("0838884567");

        assertNotNull(result);
        assertEquals("It is dinner time!", result.getMessage());
    }

    // Test 4: Search by recipient
    @Test
    public void testSearchRecipient() {

        Part3Message[] results =
                chat.searchRecipient("+27838884567");

        assertEquals(2, results.length);
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                results[0].getMessage());
        assertEquals("Ok, I am leaving without you.",
                results[1].getMessage());
    }

    // Test 5: Delete a message using its hash
    @Test
    public void testDeleteMessage() {

        Part3Message message = chat.searchMessageID("MSG002");

        assertNotNull(message);

        String hash = message.getMessageHash();

        assertTrue(chat.deleteMessage(hash));
        assertNull(chat.searchMessageID("MSG002"));
    }

    // Test 6: Display message report
    @Test
    public void testDisplayReport() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            chat.displayReport();

        } finally {
            System.setOut(original);
        }

        String report = output.toString();

        Part3Message firstSent = chat.searchMessageID("MSG001");

        assertTrue(report.contains("MESSAGE REPORT"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains(firstSent.getMessageHash()));
    }
}

