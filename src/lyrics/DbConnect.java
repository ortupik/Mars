package lyrics;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import javax.swing.*;


/**
 *
 * @author CHRISX-K
 */
public class DbConnect {
    
    private static Connection connection = null;
    
    public static Connection connectDb(){
        
        String host="jdbc:mysql://localhost:3306";
        String uName="root";
        String uPass="";
        
        try{
           
              connection = DriverManager.getConnection(host,uName,uPass);                    
       
        String createdb ="CREATE DATABASE IF NOT EXISTS `MediaShare`";
        PreparedStatement pst = connection.prepareStatement(createdb);
        pst.execute();
        Statement stt=connection.createStatement();
        stt.execute("USE MediaShare");


        String lyricsTable ="CREATE TABLE IF NOT EXISTS `lyrics` ( `title` varchar(50) NOT NULL,  `artist` varchar(50) "
                + " DEFAULT NULL,  `album` varchar(50) DEFAULT NULL,  `lyrics` varchar(5000) DEFAULT NULL,  PRIMARY KEY (`title`)) ";
        PreparedStatement pst1 = connection.prepareStatement(lyricsTable);
        pst1.execute();
        
       
        
        }
        catch(SQLException err){
           JOptionPane.showMessageDialog(null, err);
        }
       return connection;
       
    }
    
}
