
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpdataclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafat
 */
public class TCPDataClient {
    public static void main(String[] args) {
        try {
            TCPDataClient obj=new TCPDataClient();
            Socket obj_client=new Socket(InetAddress.getByName("192.168.0.106"),6578);
            DataInputStream din=new DataInputStream(obj_client.getInputStream());
            DataOutputStream dout=new DataOutputStream(obj_client.getOutputStream());
           byte[] buffer = obj.CreateDataPacket("CodeVlog".getBytes("UTF8"));
           dout.write(buffer);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCPDataClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPDataClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private byte[] CreateDataPacket(byte[] data){
        byte[] packet=null;
        try {
            byte[] initialize=new byte[1];
            initialize[0]=2;
            byte[] separator=new byte[1];
            separator[0]=4;
            byte[] data_lenghth=String.valueOf(data.length).getBytes("UTF8");
            packet=new byte[initialize.length+separator.length+data_lenghth.length+data.length];
            
            System.arraycopy(initialize,0,packet,0,initialize.length);
            System.arraycopy(data_lenghth,0,packet,initialize.length,data_lenghth.length);
            System.arraycopy(separator,0,packet,initialize.length+data_lenghth.length,separator.length);
            System.arraycopy(data,0,packet,initialize.length+data_lenghth.length+separator.length,data.length);
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TCPDataClient.class.getName()).log(Level.SEVERE, null,ex);
        }
        return packet;
    }

}
