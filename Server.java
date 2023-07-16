/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mytrial.udpmulticastserverdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.ServerException;
import java.util.Scanner;

/**
 *
 * @author reinh
 */
public class Server {
    MulticastSocket socket = null;
    byte[] buffer = null;
    DatagramSocket receive = null;
    Scanner scan = null;
    
    public static void main(String[] args) {
        Server server = new Server();
        server.initializeVariable();
        server.connecting();
    }
    
    private void initializeVariable(){
        try {
            socket = new MulticastSocket();
            buffer = new byte[Constants.BUFFER_SIZE];
            scan = new Scanner(System.in);
            
            log("Server Running...");
        } catch (ServerException ex) {
            log("InitializeVariable : " + ex.toString());
        } catch (IOException ex) {
            log("InitializeVariable :" + ex.toString());
        }
    }
    
    private String readFromKeyboard(){
        String line = scan.nextLine();
        return line;
    }
    
    private void send(String message) {
        try {
            InetAddress ip = InetAddress.getByName(Constants.IP);
            
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);
            
            socket.send(packetSend);
            log("Message Sent");
            
        } catch (IOException ex){
            log("Send : " + ex.toString());
        }
    }
    
    private void connecting(){
        while(true){
            String data = readFromKeyboard();
            send(data);
            
            buffer = new byte[Constants.BUFFER_SIZE];
        }
    }
    
    public void log(String message){
        System.out.println(message);
    }
}
