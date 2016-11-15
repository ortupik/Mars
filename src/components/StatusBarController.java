package components;
/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author chris
 */
public class StatusBarController extends JApplet implements Initializable {
  
    private static JFXPanel fxContainer;


    @FXML private Button top;



    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
    
        add(fxContainer, BorderLayout.CENTER);
      
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                createScene();
            }
        });

    }
    
    private void createScene() {

        
        Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/res/statusBar.fxml"));
		
        Scene scene = new Scene(root);
       
        fxContainer.setScene(scene);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}



    
}
