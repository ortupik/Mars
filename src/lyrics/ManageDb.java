package lyrics;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

import mp3Player.MP3;




public class ManageDb {
	
	private Connection connection;
	private static List <String> songLyrics;
	 private static String  Artist,Title,Album;
	 private static String [] mp3Details;

		
	
	public ManageDb(){
		connection = new DbConnect().connectDb();
	}

	
	
	public void insertLyrics(List <String> lyrics){
		
		mp3Details = new MP3(null).getDetails();
		Title = mp3Details[0];
		Artist = mp3Details[1];
		Album = mp3Details[2];
		
		String SQL = "INSERT INTO `lyrics` (title,artist,album,lyrics) values(?,?,?,?)";
        try {
        	PreparedStatement  statement = connection.prepareStatement(SQL);
            statement.setString(1,Title.substring(7, Title.length()));
            statement.setString(2, Artist.substring(8, Artist.length()));
            statement.setString(3, Album.substring(7, Album.length()));
            statement.setObject(4, lyrics.toString());
            statement.execute();
         
            JOptionPane.showMessageDialog(null,"\n Has been saved in the database");

        } catch (SQLException ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(null,ex);
        }
	}
     public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
