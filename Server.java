/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * This code represents a server for UDP multicast communication.
 * The server sends messages to a multicast group based on user input.
 */

package com.mytrial.udpmulticastserverdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.ServerException;
import java.util.Scanner;

public class Server {
    MulticastSocket socket = null;     // Multicast socket for sending messages
    byte[] buffer = null;              // Buffer to hold the message
    DatagramSocket receive = null;     // Datagram socket for receiving data
    Scanner scan = null;               // Scanner for reading user input
    
    public static void main(String[] args) {
        Server server = new Server();
        server.initializeVariable();
        server.connecting();
    }
    
    // Initializes the necessary variables for the server
    private void initializeVariable() {
        try {
            socket = new MulticastSocket();                     // Create a multicast socket
            buffer = new byte[Constants.BUFFER_SIZE];           // Create a buffer for the message
            scan = new Scanner(System.in);                      // Create a scanner for user input
            
            log("Server Running...");
        } catch (ServerException ex) {
            log("InitializeVariable: " + ex.toString());
        } catch (IOException ex) {
            log("InitializeVariable: " + ex.toString());
        }
    }
    
    // Reads a line of text from the keyboard
    private String readFromKeyboard() {
        String line = scan.nextLine();
        return line;
    }
    
    // Sends a message to the multicast group
    private void send(String message) {
        try {
            InetAddress ip = InetAddress.getByName(Constants.IP);   // Get the multicast group IP
            
            buffer = message.getBytes();                            // Convert the message to bytes
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);  // Create a packet for sending
            
            socket.send(packetSend);                                // Send the packet to the multicast group
            log("Message Sent");
        } catch (IOException ex) {
            log("Send: " + ex.toString());
        }
    }
    
    // Sends messages to the multicast group based on user input
    private void connecting() {
        while (true) {
            String data = readFromKeyboard();   // Read user input
            send(data);                         // Send the message to the multicast group
            
            buffer = new byte[Constants.BUFFER_SIZE];  // Reset the buffer
        }
    }
    
    // Logs the given message to the console
    public void log(String message) {
        System.out.println(message);
    }
}
