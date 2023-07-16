/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mytrial.udpmulticastserverdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author reinh
 */
public class Client {
    MulticastSocket socket = null;
    byte[] buffer = null;
    DatagramPacket packet = null;
    InetAddress ip = null;
    
    public static void main(String[] args){
        Client client = new Client();
        client.initializeVariable();
        client.connecting();
    }
    private void initializeVariable(){
        try{
            socket = new MulticastSocket(Constants.PORT);
            ip = InetAddress.getByName(Constants.IP);
            buffer = new byte[Constants.BUFFER_SIZE];
        } catch(IOException ex){
            log("InitializeVariable : " + ex.toString());
        }             
    }
    
    private String receiveData(){
        String line = "";
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            line = new String(packet.getData(), 0, packet.getLength());
        } catch (IOException ex) {
            log("receiveData : " + ex.toString());
        }
        return line;
    }
    
    private void joinGroup(){
        try {
            socket.joinGroup(ip);
            log("Client Running...");
        } catch (IOException ex) {
            log("JoinGroup : " + ex.toString());
        }
    }
    
    private void connecting(){
        joinGroup();
        while(true){
            String line = receiveData();
            log("Client Recieved : " + line);
        }
    }
    
    public void log(String message){
        System.out.println(message);
    }
}
