/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsserver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FileServer {
    
    public static boolean mkdir(String filePath )
    {
        return new File(filePath).mkdirs();
    }
    private static String legalPath(String filePath, UserSession session)
    {
        try {
            String canonicalPath = new File(DSServer.filesPath + session.getUsername() + "\\" + (filePath.startsWith("/")? filePath : session.getCwd() + "\\" + filePath)).getCanonicalPath().toString();
            if (canonicalPath.endsWith(DSServer.filesPath + session.getUsername()) || canonicalPath.startsWith(DSServer.filesPath + session.getUsername() + "\\"))
            {
                return canonicalPath;
            }
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
}
    public static boolean mkdir(String filePath, UserSession session)
    {
        String canonicalPath = legalPath(filePath, session);
        if (canonicalPath.length() > 0)
           return mkdir(canonicalPath);
        else return false;
    }
    public static String[] ls(String filePath, UserSession session)
    {
        String canonicalPath = legalPath(filePath, session);
        if (canonicalPath.length() > 0)
            return new File(canonicalPath).list();
        else return new String[]{};
    }
    public static String filterPath(String path, UserSession session)
    {
        return path.replaceAll("^"+DSServer.filesPath.replace("\\", "\\\\")+session.getUsername(), "");
    }
    public static String cd(String filePath, UserSession session)
    {
        String _cwd = legalPath(filePath, session);
        if (_cwd.length() > 0 && new File(_cwd).exists())
        {
            String output = filterPath(_cwd, session);
            if (output.length() == 0) return "/";
            return output;
        }
        return session.cwd;
    }
     private static boolean delete(File file)
    	throws IOException{
 
    	if(file.isDirectory()){
 
    		//directory is empty, then delete it
    		if(file.list().length==0){
    			
    		   return file.delete();
    		   
    			
    		}else{
    			
    		   //list all the directory contents
        	   String files[] = file.list();
     
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
        		 
        	      //recursive delete
        	     delete(fileDelete);
        	   }
        		
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     return file.delete();
        	   }
                   return false;
    		}
    		
    	}
        else
        {
            return file.delete();
        }
    }
    public static boolean rm(String filePath, UserSession session)
    {
        try {
            String canonicalPath =legalPath(filePath, session);
            File file = new File(canonicalPath);
            if (canonicalPath.length() > 0 &&file.exists() && !file.isDirectory())
                return delete(file);
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean rmdir(String filePath, UserSession session)
    {
        try {
            String canonicalPath = legalPath(filePath, session);
            File file = new File(canonicalPath);
            if (canonicalPath.length() > 0 && file.exists() && file.isDirectory())
                return delete(file);
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
