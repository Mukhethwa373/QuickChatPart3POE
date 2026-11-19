/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3poe;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Student
 */
class Part3Message {
    private String messageID;
    private String sender;
    private String recipient;
    private String message;
    private String messageHash;
    private String flag;
    public String getMessage;
    

    public Part3Message(String messageID, String sender,
            String recipient, String message, String flag) {

        this.messageID = messageID;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;

        this.messageHash = createHash(messageID + message);
    }

    private String createHash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] bytes = md.digest(
                    text.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

            for (byte b : bytes) {
                result.append(String.format("%02x", b));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to create message hash", e);
        }
    }

    public String getMessageID() {
        return messageID;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}



