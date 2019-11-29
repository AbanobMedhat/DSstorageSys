/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsserver;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PC
 */
public class DSServer {

    public static final String filesPath = "C:\\DSys\\";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try
       {
           Database.init();
           ServerSocket serverSocket = new ServerSocket(9991);
           System.out.println("Started server, listening on " + serverSocket.getInetAddress() + ":"+serverSocket.getLocalPort());
           while (true){
           Socket client = serverSocket.accept();
           ServerWorker worker = new ServerWorker(client);
           worker.start();
           }
       }
       catch (Exception ex)
       {
           System.out.println(ex.getMessage());
       }
        
    }
    
}
