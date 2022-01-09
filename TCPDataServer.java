/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpdataserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static java.time.Clock.system;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.BindException;

/**
 *
 * @author rafat
 */
public class TCPDataServer {
    public static void main(String[] args) {
        try {
            ServerSocket server_socket = new ServerSocket(6578);
            while(true){
               new Thread(new ClientWorker(server_socket.accept())).start();

            }
        } catch (IOException ex) {
            Logger.getLogger(TCPDataServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
class ClientWorker implements Runnable{
    private Socket target_socket;
    private DataInputStream din;
    private DataOutputStream dout;

    public ClientWorker(Socket recv_socket){
        try {
            
            target_socket=recv_socket;
            din=new DataInputStream(target_socket.getInputStream());
            dout=new DataOutputStream(target_socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while(true){
            byte[] initialize=new byte[1];
            try {
                din.read(initialize, 0, initialize.length);
                if(initialize[0]==2){
                  System.out.println(new String(Readstream()));
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
   
    private byte[] Readstream(){
        byte[] data_buff=null;
      
        try {
              int b=0;
              String length_buff="";
            while((b=din.read()) !=4){
                length_buff+=(char)b;
           
            }
            int data_length = Integer.parseInt(length_buff);
            data_buff=new byte[Integer.parseInt(length_buff)];
            int byte_read=0;
            int byte_offset=0;
            while(byte_offset < data_length){
                byte_read = din.read(data_buff, byte_offset, data_length-byte_offset);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data_buff;
    }


}

   
