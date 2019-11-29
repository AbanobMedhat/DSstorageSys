/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author PC
 */
public class SocketHelper {
    Socket client;
    DataOutputStream os;
    DataInputStream is;
    
    public SocketHelper(Socket client)
    {
        this.client = client;
        try{
            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
        }
        catch (Exception ex)
        {

        }
    }
    public String readLine() 
    {
        try{
        return is.readLine();
        }catch (Exception ex){}
        return "";
    }
    public void writeLine(String line) 
    {
        try{
        os.writeBytes(line+"\n");
        }catch (Exception ex){}
    }
    public void Disconnect()
    {
        try{
        client.close();
        }catch (Exception ex){}
    }
}
