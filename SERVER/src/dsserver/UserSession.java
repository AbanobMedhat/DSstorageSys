/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsserver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class UserSession {
    String email;
    String username;
    String cwd;
    public UserSession(String email) throws SQLException{
        this.email = email;
        ResultSet rs = Database.getRow(new String[]{"email"}, new String[]{this.email});
        while (rs.next())
        {
            this.username = rs.getString("username");
        }
        cwd=  "/";
        if (!new File(DSServer.filesPath + username).exists())
        {
            FileServer.mkdir("", this);
        }
    }
    public String changeCwd(String target){
        String _cwd = FileServer.cd(target, this);
        if (_cwd.length() > 0)
        {
            cwd = _cwd;
        }
        return cwd;
    }
    public String getCwd() { return cwd; }
    public String getUsername() { return username;}
    public String getEmail(){return email;}
}
