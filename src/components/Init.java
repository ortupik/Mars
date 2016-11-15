package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;



public class Init {
	
	/*For read file*/

    
    /*For Menu*/
       private  JMenuBar menuBar;
	   private  JMenu Playlistmenu;
	   private  JMenuItem exit;
	   private  JMenu help;
	   private  JMenuItem about;
	   private  JMenu player;
	   private  JMenuItem terms;
	   private  JMenuItem settings;
	   private  JMenu tool;
	   private  JMenu  add;

	
		 		 
	   
	
	//Getting menu
	
	public JMenuBar menu ( ){
		
		   
		   try{
	 	    	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	 	    }catch(Exception e){
	 	    	
	 	    }  
		

          menuBar = new JMenuBar();
          menuBar.setBounds(5,21, 1200,18);
		 // menuBar.setBackground(new Color(229,227,224)  );
          menuBar.setFont(new Font("Century", Font.PLAIN, 11));
		   
		  try{
	 	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 	    }catch(Exception e){
	 	    	
	 	   }   
	
           Playlistmenu = new JMenu ("Playlist");
       //    Playlistmenu.setForeground(Color.blue);
       //    Playlistmenu.setBackground(Color.blue);
           //Playlistmenu.setFont(new Font("System", Font.PLAIN, 12));
	       menuBar.add(Playlistmenu);
	          
	        exit = new JMenuItem ("EXIT");
	        exit.setForeground(Color.red);
	        Playlistmenu.add(exit);
	        
	        help = new JMenu ("Help");
	      // help.setForeground(Color.blue);
	        menuBar.add(help);
	        
	        about = new JMenuItem ("ABOUT");
	    //    about.setForeground(Color.blue);
	        help.add(about);
	        
	        terms = new JMenuItem ("TERMS & CONDITIONS");
	        help.add(terms);
	      //  terms.setForeground(Color.blue);
	       
	        add = new JMenu ("Add Frame");
	      //  add.setForeground(Color.blue);
	        menuBar.add(add);
	         
	       
	        tool = new JMenu("Tools");
	       // tool.setForeground(Color.blue);
	        menuBar.add(tool);
	        
	        player = new JMenu("Players");
	      //  player.setForeground(Color.blue);
	        menuBar.add( player);
	        
	        settings = new JMenuItem ("Settings");
	       //settings.setForeground(Color.blue);
	        tool.add(settings);
 
	        /*about.addActionListener (
	 	           new ActionListener (){
	 	               
						@Override
						public void actionPerformed(ActionEvent e) {
								
					    Info i = new Info(MediaShare.mainFrame);
					    i.setUndecorated(true);
					    i.setLocation(100, 100);
					    i.setSize(757, 46);
	 	                 i.setVisible(true);
	                    
	           	 
	 	           
	 	               /// i.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	                  
					   }      
	 	            });*/
	        return menuBar;
	        
	             }         
              }





