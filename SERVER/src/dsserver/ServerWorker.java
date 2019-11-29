/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package dsserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.SQLException;
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
			writeLine(session.getEmail());
		}
		else if ("/u".equals(input))
		{
			writeLine(session.getUsername());
		}
		else if ("pwd".equals(input))
		{
			writeLine(session.getCwd());
		}
		else if ("ls".equals(input) || input.startsWith("ls"))
		{
			String[] files = FileServer.ls("", session);
			writeLine("/#list");
			for (String file : files)
			{
				writeLine(FileServer.filterPath(file, session));
			}
			writeLine("#list/");
		}
		else if (cmd.length > 0 && cmd[0].equals("cd"))
		{
			String target = input.replaceAll("^cd ", "");
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
		else if (cmd.length > 0 && cmd[0].equals("mkdir"))
		{
			String target = input.replaceAll("^mkdir ", "");
			if (FileServer.mkdir(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("Directory already exists or bad permissions.");
			}
		}
                else if (cmd.length > 0 && cmd[0].equals("rmdir"))
		{
			String target = input.replaceAll("^rmdir ", "");
			if (FileServer.rmdir(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("Directory does not exist or bad permissions.");
			}
		}
                else if (cmd.length > 0 && cmd[0].equals("rm"))
		{
			String target = input.replaceAll("^rm ", "");
			if (FileServer.rm(target, session)){
				writeLine("OK");
				execCommand("ls");
			}
			else{
				writeLine("File does not exist or bad permissions.");
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
		while ((input = readLine()).length() > 0)
		{
			if (!execCommand(input)) break;
		}
	}
}
