package components;
/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author chris
 */
public class VolumeAnimClass extends MediaShare implements Initializable {

    private static JFXPanel fxContainer;
    private static  VolumeAnim  voulumeanim = new VolumeAnim ();

    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setOpaque(false);
         fxContainer.setPreferredSize(new Dimension(344,30));
        add(fxContainer, BorderLayout.CENTER);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
            }
        });

    }

    private void createScene() {

          Group root = new Group();
           voulumeanim = new VolumeAnim();
          
           voulumeanim.setLayoutX(3);
           voulumeanim.setLayoutY(500);
           double value =(double)mediaPlayerX.getVolume()/200;
           voulumeanim.setValue(value);
            root.getChildren().add(voulumeanim);

           Scene scene = new Scene(root,40,510, Color.TRANSPARENT);
         
         
            fxContainer.setScene(scene);

    }
    public static void updateVolume(){
    	Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				  
				  double value =(double)mediaPlayerX.getVolume()/200;
				  System.out.println("updateVolume "+value);
    	          voulumeanim.setValue(value);
			}
    		
    	});
    	 
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}



