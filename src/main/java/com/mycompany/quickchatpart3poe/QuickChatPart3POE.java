/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchatpart3poe;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
/**
/**
 *
 * @author Student
 */
public class QuickChatPart3POE {
    
    public String searchMessageID;
    public String getString;
    
    private Part3Message[] messages = new Part3Message[100];
private int messageCount = 0;
private Scanner input = new Scanner(System.in);
// Add a message to the array
public void addMessage(Part3Message message) {
if (messageCount < messages.length) {
messages[messageCount] = message;
messageCount++;
} else {
System.out.println("Message storage is full.");
}
}

public Part3Message[] getSentMessages() {
Part3Message[] sent = new Part3Message[messageCount];
int count = 0;
for (int i = 0; i < messageCount; i++) {
if (messages[i].getFlag().equalsIgnoreCase("Sent")) {
sent[count] = messages[i];
count++;
}
}
Part3Message[] result = new Part3Message[count];
for (int i = 0; i < count; i++) {
result[i] = sent[i];
}
return result;
}

public Part3Message[] getStoredMessages() {
Part3Message[] stored = new Part3Message[messageCount];
int count = 0;
for (int i = 0; i < messageCount; i++) {
if (messages[i].getFlag().equalsIgnoreCase("Stored")) {
stored[count] = messages[i];
count++;
}
}
Part3Message[] result = new Part3Message[count];
for (int i = 0; i < count; i++) {
result[i] = stored[i];
}
return result;
}

public void saveMessages() {
JSONArray jsonArray = new JSONArray();
for (int i = 0; i < messageCount; i++) {
JSONObject object = new JSONObject();
object.put("messageID", messages[i].getMessageID());
object.put("sender", messages[i].getSender());
object.put("recipient", messages[i].getRecipient());
object.put("message", messages[i].getMessage());
object.put("messageHash", messages[i].getMessageHash());
object.put("flag", messages[i].getFlag());
jsonArray.put(object);
}
try (FileWriter writer = new FileWriter("messages.json")) {
writer.write(jsonArray.toString(4));
System.out.println("Messages saved successfully.");
} catch (IOException e) {
System.out.println("Error saving messages: " + e.getMessage());
}
}

public void loadMessages() {
File file = new File("messages.json");
if (!file.exists()) {
System.out.println("The messages.json file does not exist.");
return;
}
try {
String data = new String(
Files.readAllBytes(file.toPath()));
JSONArray jsonArray = new JSONArray(data);
messageCount = 0;
for (int i = 0; i < jsonArray.length(); i++) {
JSONObject object = jsonArray.getJSONObject(i);
Part3Message message = new Part3Message(
object.getString("messageID"),
object.getString("sender"),
object.getString("recipient"),
object.getString("message"),
object.getString("flag")
);
addMessage(message);
}
System.out.println("Messages loaded successfully.");
} catch (IOException e) {
System.out.println("Error loading messages: " + e.getMessage());
} catch (Exception e) {
System.out.println("Invalid JSON data: " + e.getMessage());
}
}

public void displayMessage(Part3Message message) {
System.out.println("Message ID: " + message.getMessageID());
System.out.println("Sender: " + message.getSender());
System.out.println("Recipient: " + message.getRecipient());
System.out.println("Message: " + message.getMessage());
System.out.println("Message Hash: " + message.getMessageHash());
System.out.println("Status: " + message.getFlag());
System.out.println("--------------------------------");
}

public void displaySentMessages() {
System.out.println("--- SENT MESSAGES ---");
Part3Message[] sent = getSentMessages();
for (int i = 0; i < sent.length; i++) {
displayMessage(sent[i]);
}
System.out.println("Total sent messages: " + sent.length);
}

public void displayStoredMessages() {
System.out.println("--- STORED MESSAGES ---");
Part3Message[] stored = getStoredMessages();
for (int i = 0; i < stored.length; i++) {
displayMessage(stored[i]);
}
System.out.println("Total stored messages: " + stored.length);
}
// Find the longest stored message
public String getLongestMessage() {
String longest = "";
for (int i = 0; i < messageCount; i++) {
if (messages[i].getFlag().equalsIgnoreCase("Stored")) {
if (messages[i].getMessage().length() > longest.length()) {
longest = messages[i].getMessage();
}
}
}
return longest;
}

public void displayLongestMessage() {
String longest = getLongestMessage();
if (longest.isEmpty()) {
System.out.println("No stored messages found.");
} else {
System.out.println("Longest stored message:");
System.out.println(longest);
}
}

public Part3Message searchMessageID(String searchValue) {
for (int i = 0; i < messageCount; i++) {
if (messages[i].getMessageID().equalsIgnoreCase(searchValue)) {
return messages[i];
}
}

for (int i = 0; i < messageCount; i++) {
if (messages[i].getRecipient().equalsIgnoreCase(searchValue)) {
return messages[i];
}
}
return null;
}

public Part3Message[] searchRecipient(String recipient) {
Part3Message[] found = new Part3Message[messageCount];
int count = 0;
for (int i = 0; i < messageCount; i++) {
if (messages[i].getRecipient().equalsIgnoreCase(recipient)) {
found[count] = messages[i];
count++;
}
}
Part3Message[] result = new Part3Message[count];
for (int i = 0; i < count; i++) {
result[i] = found[i];
}
return result;
}

public boolean deleteMessage(String hash) {
for (int i = 0; i < messageCount; i++) {
if (messages[i].getMessageHash().equalsIgnoreCase(hash)) {
for (int j = i; j < messageCount - 1; j++) {
messages[j] = messages[j + 1];
}
messages[messageCount - 1] = null;
messageCount--;
saveMessages();
return true;
}
}
return false;
}

public int countMessages(String status) {
int count = 0;
for (int i = 0; i < messageCount; i++) {
if (messages[i].getFlag().equalsIgnoreCase(status)) {
count++;
}
}
return count;
}

public void displayReport() {
System.out.println("========== MESSAGE REPORT ==========");
for (int i = 0; i < messageCount; i++) {
displayMessage(messages[i]);
}
System.out.println("Total messages: " + messageCount);
System.out.println("Sent messages: " + countMessages("Sent"));
System.out.println("Stored messages: " + countMessages("Stored"));
System.out.println("Disregarded messages: "
        +countMessages("Disregard"));
System.out.println("====================================");
}

public void addSampleMessages() {
addMessage(new Part3Message(
"MSG001", "QuickChat User", "+27834557896",
"Did you get the cake?", "Sent"));
addMessage(new Part3Message(
"MSG002", "QuickChat User", "+27838884567",
"Where are you? You are late! I have asked you to be on time.",
"Stored"));
addMessage(new Part3Message(
"MSG003", "QuickChat User", "+27834484567",
"Yohoooo, I am at your gate.", "Disregard"));
addMessage(new Part3Message(
"MSG004", "QuickChat User", "0838884567",
"It is dinner time!", "Sent"));
addMessage(new Part3Message(
"MSG005", "QuickChat User", "+27838884567",
"Ok, I am leaving without you.", "Stored"));
}

public void showMenu() {
int choice = 0;
do {
System.out.println("========== QUICKCHAT ==========");
System.out.println("1. Display sent messages");
System.out.println("2. Display disregarded messages");
System.out.println("3. Display stored messages");
System.out.println("4. Display longest stored message");
System.out.println("5. Search message by ID");
System.out.println("6. Search messages by recipient");
System.out.println("7. Delete message using hash");
System.out.println("8. Display message report");
System.out.println("9. Save messages to JSON");
System.out.println("10. Load messages from JSON");
System.out.println("11. Exit");
System.out.print("Enter your choice: ");
if (!input.hasNextInt()) {
System.out.println("Please enter a number.");
input.nextLine();
continue;
}
choice = input.nextInt();
input.nextLine();
switch (choice) {
case 1:
displaySentMessages();
break;
case 2:
System.out.println("--- DISREGARDED MESSAGES ---");
for (int i = 0; i < messageCount; i++) {
if (messages[i].getFlag().equalsIgnoreCase("Disregard")) {
displayMessage(messages[i]);
}
}
break;
case 3:
displayStoredMessages();
break;
case 4:
displayLongestMessage();
break;
case 5:
System.out.print("Enter message ID: ");
Part3Message found = searchMessageID(input.nextLine());
if (found != null) {
displayMessage(found);
} else {
System.out.println("Message not found.");
}
break;
case 6:
System.out.print("Enter recipient number: ");
Part3Message[] results =
searchRecipient(input.nextLine());
if (results.length == 0) {
System.out.println("No messages found.");
} else {
for (int i = 0; i < results.length; i++) {
displayMessage(results[i]);
}
}
break;
case 7:
System.out.print("Enter message hash: ");
if (deleteMessage(input.nextLine())) {
System.out.println("Message deleted successfully.");
} else {
System.out.println("Message hash not found.");
}
break;
case 8:
displayReport();
break;
case 9:
saveMessages();
break;
case 10:
loadMessages();
break;
case 11:
System.out.println("Thank you for using QuickChat.");
break;
default:
System.out.println("Invalid choice.");
}
} while (choice != 11);
}
public static void main(String[] args) {
QuickChatPart3POE chat = new QuickChatPart3POE();
chat.addSampleMessages();
chat.saveMessages();
chat.showMenu();
}
}



    

