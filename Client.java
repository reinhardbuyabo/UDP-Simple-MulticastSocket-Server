/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * This code represents a client for a UDP multicast server.
 * The client joins a multicast group, receives messages from the server, and logs them.
 */

package com.mytrial.udpmulticastserverdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {
    MulticastSocket socket = null;  // Socket for multicast communication
    byte[] buffer = null;           // Buffer to store received data
    DatagramPacket packet = null;   // Packet to hold received data
    InetAddress ip = null;          // IP address of the multicast group
    
    public static void main(String[] args) {
        Client client = new Client();
        client.initializeVariable();
        client.connecting();
    }
    
    // Initializes the necessary variables for the client
    private void initializeVariable() {
        try {
            socket = new MulticastSocket(Constants.PORT);  // Create a multicast socket
            ip = InetAddress.getByName(Constants.IP);      // Get the IP address of the multicast group
            buffer = new byte[Constants.BUFFER_SIZE];       // Create a buffer for received data
        } catch(IOException ex) {
            log("InitializeVariable : " + ex.toString());
        }
    }
    
    // Receives data from the server and returns it as a string
    private String receiveData() {
        String line = "";
        try {
            packet = new DatagramPacket(buffer, buffer.length);  // Create a packet for receiving data
            socket.receive(packet);                             // Receive data into the packet
            line = new String(packet.getData(), 0, packet.getLength());  // Convert the received data to a string
        } catch (IOException ex) {
            log("receiveData : " + ex.toString());
        }
        return line;
    }
    
    // Joins the multicast group specified by the IP address
    private void joinGroup() {
        try {
            socket.joinGroup(ip);   // Join the multicast group
            log("Client Running...");
        } catch (IOException ex) {
            log("JoinGroup : " + ex.toString());
        }
    }
    
    // Connects to the multicast group, receives data, and logs it indefinitely
    private void connecting() {
        joinGroup();
        while (true) {
            String line = receiveData();
            log("Client Received: " + line);
        }
    }
    
    // Logs the given message to the console
    public void log(String message) {
        System.out.println(message);
    }
}
