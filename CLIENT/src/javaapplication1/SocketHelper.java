/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class SocketHelper {
    Socket client;
    DataOutputStream os;
    DataInputStream is;
    String email = null;
    String password = null; //dangerous: in production, we should use a session ID to resume the connection. user data can be accessed through memory otherwise
    boolean reconnecting = false;
    private int resumeSession() throws IOException
    {
        int _return = -1;
        if (reconnecting) return _return;
        reconnecting = true;
            if (client.isConnected() && !client.isClosed()) client.close();
            while (true) //try to reconnect forever
            {
                try
                {
                client = new Socket(client.getInetAddress(), client.getPort());
                break;
                }
                catch (Exception ex)
                {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            newClient(client);
            if (email != null && password != null)
            {
                _return = authenticate(email, password, false)? 1 : 0;
            }
            else { 
         _return= 1; }
            reconnecting = false;
            return _return;
    }
    public boolean authenticate(String email, String password, boolean mode)
    {
        
     try{
         writeLine(mode? "/r" : "/l");
         if (readLine().equals("1"))
         {
             writeLine(email);
             if (readLine().equals("0"))
             {
                 GUI.msgBox(mode?"Invalid email supplied or already exists." : "The email entered does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
                 return false;
             }
             writeLine(password);
             if (readLine().equals("0"))
             {
                 GUI.msgBox(mode?"Server error. Please try again later.":"Invalid password supplied.", "ERROR", JOptionPane.ERROR_MESSAGE); //Invalid password or server error storing session
                 return false;
             }
             this.email = email;
             this.password = password;
             return true;
         }
         else
         {
             GUI.msgBox("Server is not ready for this operation yet. Please try again later.", "ERROR", JOptionPane.ERROR_MESSAGE);
             return false;
         }
     }
     catch (Exception ex)
     {
    
     }
     return false;
    }
    private void newClient(Socket client)
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
    public SocketHelper(Socket client)
    {
       newClient(client);
    }
    public DataInputStream getInputStream()
    {
        return is;
    }
    public DataOutputStream getOutputStream()
    {
        return os;
    }
    public String readLine() 
    {
        try{
        return is.readLine();
        }catch (IOException ex){
         try {
             int _resume = resumeSession();
             switch (_resume) {
                 case 1:
                     return readLine();
                 case 0:
                     GUI.msgBox("Failed to resume the connection: the credentials are no longer valid. Please relogin.", "ERROR", JOptionPane.ERROR_MESSAGE);
                     System.exit(0);
                     break;
                 default:
                     return ""; //ignore request, reconnection in progress.
             }
            } catch (IOException ex1) {
                Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return "";
    }
    
    public void writeLine(String line) 
    {
        try{
        os.writeBytes(line+"\n");
        }catch (IOException ex){
            try {
                 int _resume = resumeSession();
                 
             switch (_resume) {
                 case 1:
                     writeLine(line);
                     break;
                 case 0:
                     GUI.msgBox(_resume+"Failed to resume the connection: the credentials are no longer valid. Please relogin.", "ERROR", JOptionPane.ERROR_MESSAGE);
                     System.exit(0);
                     break;
                 default:
                     break; //ignore request, reconnection in progress.
             }
            } catch (IOException ex1) {
                Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    public void Disconnect()
    {
        try{
        client.close();
        }catch (Exception ex){}
    }
}
