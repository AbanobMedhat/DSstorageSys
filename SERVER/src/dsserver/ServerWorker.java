/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package dsserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
*
* @author PC
*/
public class ServerWorker extends Thread {

	Socket client;
	UserSession session;
	DataInputStream iStream;
	DataOutputStream oStream;
	public ServerWorker(Socket client)
	{
		this.client = client;
		try
		{
			this.iStream = new DataInputStream(client.getInputStream());
			this.oStream = new DataOutputStream(client.getOutputStream());
		}
		catch (Exception ex)
		{
			
		}
	}
	public void writeLine(String output)
	{
		try
		{
			oStream.writeBytes(output+"\n");
		}
		catch (Exception ex)
		{
		}
	}
	public String readLine()
	{
		try
		{
			return iStream.readLine();
		}
		catch (Exception ex)
		{
		} 
		return "";
	}
	public void close(String error )
	{
		try{
			writeLine("0");
			writeLine("ERROR:"+error);
			client.close();
			System.out.println("Client disconnected");
		}catch (Exception ex){}
	}
	public void close()
	{
		try{
			writeLine("0");
			client.close();
			System.out.println("Client disconnected");
		}catch (Exception ex){}
	}
	private boolean authenticateEmail(String email)
	{
		try{
			return Database.getRow(new String[]{"email"}, new String[]{email}).next();
		}
		catch (SQLException ex){
			close(ex.getMessage());
			System.out.println(ex.getMessage());
			return false;
		}
	}
	private boolean authenticatePassword(String email, String password)
	{
		try{
			return Database.getRow(new String[]{"email", "password"}, new String[]{email, password}).next();
		}
		catch (SQLException ex){
			close(ex.getMessage());
			System.out.println(ex.getMessage());
			return false;
		}
	}
        private String filterCommand(String input, String command)
        {
            return input.replaceAll("^"+command+" ", "");
        }
	private boolean execCommand(String input)
	{
		String[] cmd = {};
		if (input.contains(" ")) cmd = input.split(" ");
		if ("/q".equals(input)) { writeLine("Received disconnect from user. Bye."); return false; }
		else if ("/l".equals(input))
		{
			if (session == null)
			{
				writeLine("1");
				String emailSupplied = readLine();
				if (authenticateEmail(emailSupplied))
				{
					writeLine("1");
					if (authenticatePassword(emailSupplied, readLine()))
					{
						try {
							session = new UserSession(emailSupplied);
						} catch (SQLException ex) {
							close();
							return false;
						}
						writeLine("1");
					}
					else
					{
						close();
						return false;
					}
				}
				else
				{
					close();
					return false;
				}
			}
			else
			{
				writeLine("0");
			}
		}
		else if ("/r".equals(input))
		{
			if (session == null)
			{
				writeLine("1");
				String emailSupplied = readLine();
				String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; //TLD only
				Pattern pattern = Pattern.compile(regex);
				if (!authenticateEmail(emailSupplied) && pattern.matcher(emailSupplied).matches())
				{
					writeLine("1");
					try {
						Database.insertRecord(emailSupplied, Database.createUsername(emailSupplied), readLine());
						writeLine("1");
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
						close();
						return false;
					}
				}
				else
				{
					close();
					return false;
				}
			}
			else
			{
				writeLine("0");
			}
                        
		}
		else if ("/e".equals(input))
		{
			writeLine(session != null? session.getEmail() : "0");
		}
                else if ("/h".equals(input))
                {
                    writeLine("1");
                }
		else if ("/u".equals(input))
		{
			writeLine(session != null? session.getUsername(): "0");
		}
		else if ("pwd".equals(input))
		{
			writeLine(session != null? session.getCwd(): "0");
		}
		else if ("ls".equals(input) || input.startsWith("ls"))
		{
                    if (session != null)
                    {
			List<String> files = FileServer.ls("", session);
			writeLine("/#list");
			for (String file : files)
			{
				writeLine(FileServer.filterPath(file, session));
			}
			writeLine("#list/");
                    }
		}
		else if (cmd.length > 0 && cmd[0].equals("cd"))
		{if (session != null)
                    {
			String target = filterCommand(input, "cd");
			String _cwd = session.getCwd();
			session.changeCwd(target);
			if (!_cwd.equals(session.getCwd())){
				writeLine("#cwd:" + session.getCwd());
				execCommand("ls");
			}
			else{
				writeLine("Directory does not exist or bad permissions.");
			}
                    }
		}
		else if (cmd.length > 0 && cmd[0].equals("mkdir"))
		{if (session != null)
                    {
			String target = filterCommand(input, "mkdir");
			if (FileServer.mkdir(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("Directory already exists or bad permissions.");
			}
                    }
		}
                else if (cmd.length > 0 && cmd[0].equals("rmdir"))
		{
                    if (session != null)
                    {
			String target = filterCommand(input, "rmdir");
			if (FileServer.rmdir(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("Directory does not exist or bad permissions.");
			}
                    }
		}
                else if (cmd.length > 0 && cmd[0].equals("rm"))
		{
                    if (session != null)
                    {
			String target = filterCommand(input, "rm");
			if (FileServer.rm(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("File does not exist or bad permissions.");
			}
                    }
		}
                
                else if (cmd.length > 0 && (cmd[0].equals("mv") || cmd[0].equals("rnm")))
		{
                    if (session != null)
                    {
			if (FileServer.move(cmd[1], cmd[2], session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("Target file already exists or bad permissions.");
			}
                    }
		}
                
                else if (cmd.length > 0 && (cmd[0].equals("download")))
		{
                    if (session != null)
                    {
			String target = filterCommand(input, "download");
			if (FileServer.exists(target, session)){
                            try {
                                writeLine("/#download");
                                writeLine(Paths.get(target).getFileName().toString());
                                FileServer.passFile(target, session, this.client);
                               
                            } catch (IOException ex) {
                                writeLine("ERROR: An error occurred while trying to process your command. Please try again later.");
                            }
			}
			else{
				writeLine("Target file does not exist or bad permissions.");
                                execCommand("ls");
			}
                    }
                }
                
                else if (cmd.length > 0 && (cmd[0].equals("upload")))
		{
                    if (session != null)
                    {
			String target = filterCommand(input, "upload");
                        boolean _continue = true;
			if (FileServer.exists(target, session)){
                            writeLine("WARNING: File already exists. Respond with Y to overwrite. Type N or anything else to abort.");
                            if (!readLine().equals("Y"))
                            {
                                _continue = false;
                            }
                        }
                        if (_continue)
                        {
                            try {
                                writeLine("#upload");
                                FileServer.storeFile(target, session, iStream);                                
                            } catch (IOException ex) {
                                writeLine("ERROR: An error occurred while trying to process your command. Please try again later.");
                            }
                        }
                    }
                }
		else
		{
			writeLine("ERROR: Unrecognized command");
		}
		return true;
	}
	public void run(){
		String input;
		/*try{
	client.setSoTimeout(5000); //Disconnect inactive
	}
	catch (Exception ex) {}*/
		System.out.println("Client connected: " + client.getInetAddress());
		while ((input = readLine()) != null && input.length() > 0)
		{
			if (!execCommand(input)) break;
		}
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
