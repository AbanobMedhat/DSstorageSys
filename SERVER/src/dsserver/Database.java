/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsserver;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author PC
 */
public class Database {  
     /** 
     * Connect to a sample database 
     */  
    static Connection conn = null;
    private static void connect() throws SQLException
    {
        if (conn == null)
        {
         String url = "jdbc:sqlite:C:/sqlite/JTP.db";  
            // create a connection to the database  
         conn = DriverManager.getConnection(url);  
        }        
    }
    public static void disconnect() throws SQLException
    { 
        if (conn != null) {  
                    conn.close();  
         }
    }
    public static void init() throws SQLException {
         connect();
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS users (email VARCHAR(255) PRIMARY KEY, username VARCHAR(255) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL);");
            System.out.println("Connection to SQLite has been established.");  
    }
    public static String createUsername(String email) throws SQLException
    {
         connect();
        int index = 1;
        List<String> takenUsers = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE username like ? \n";  
        String[] splits = email.split("@");
        PreparedStatement pstmt = conn.prepareStatement(sql);  
        pstmt.setString(1, splits[0]+"%");  
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
        {
            takenUsers.add(rs.getString("username"));
        }
        while (takenUsers.contains(splits[0]+""+(index <= 1? "" : index))) index++;
        return splits[0]+""+(index <= 1? "" : index);
    }
    public static void insertRecord(String email, String username, String password) throws SQLException
    {
         connect();
        String sql = "INSERT INTO users(email,username,password) VALUES(?,?,?)";  
        PreparedStatement pstmt = conn.prepareStatement(sql);  
        pstmt.setString(1, email);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.executeUpdate(); 
    }
    public static ResultSet getRow(String[] columns, String[] values) throws SQLException
    {
        //emailaddress, email
         connect();
        String sql = "SELECT * FROM users";
        if (columns.length > 0) sql += " WHERE ";
        for (String column : columns)
        {
            sql += " "+ column +"=? AND";
        }
        sql = sql.replaceAll("AND$", "");
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int index = 1;
        for (String value : values)
        {
            pstmt.setString(index, value);  
            index++;
        }
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }
}  
