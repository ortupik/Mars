package mp3Player;
/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author chris
 */
public class Mp3DetailApplet extends JApplet implements Initializable{
    
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    @FXML
    private Text albumField;

    @FXML
    private ImageView mp3Image;

    @FXML
    private Text bitrateField;

    @FXML
    private Text titleField;

    @FXML
    private Text artistField;

    @FXML
    private Text genreField;
    
    
    private static String  Artist,Title,Album;
	 private static String [] mp3Details;
    /**
     * @param args the command line arguments
     */

    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
      
     //   fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
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
			root = FXMLLoader.load(getClass().getResource("/res/mp3Details.fxml"));
		
        Scene scene = new Scene(root);
       
        fxContainer.setScene(scene);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mp3Details = new MP3(null).getDetails();
		Title = mp3Details[0];
		Artist = mp3Details[1];
		Album = mp3Details[2];
		
		titleField.setText(Title);
		artistField.setText(Artist);
		albumField.setText(Album);
		
		
	}

    
}
