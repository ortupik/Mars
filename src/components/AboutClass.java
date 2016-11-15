package components;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JApplet;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.TextFlow;

public class AboutClass extends JApplet implements Initializable {
	

	private JFXPanel fxContainer;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	
	}
	  @Override
	    public void init() {
	        fxContainer = new JFXPanel();
	        int width =  MediaShare.getMainFrame().getWidth();
	       //   fxContainer.setPreferredSize(new Dimension(width,30));
	        add(fxContainer, BorderLayout.CENTER);
	        // create JavaFX scene
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
	            root = FXMLLoader.load(getClass().getResource("/res/about.fxml"));

	            Scene scene = new Scene(root);
	            
	            fxContainer.setScene(scene);
	            // new SinglePlayer().startControls();


	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

}
