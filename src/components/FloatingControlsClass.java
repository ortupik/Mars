package components;
/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.prism.paint.Color;

import player.MainPlayer;

/**
 *
 * @author chris
 */
public class FloatingControlsClass extends MediaShare implements Initializable {

    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;
    private static ExecutorService executorService;
    private static Boolean isSet = false;
    private static String time;

    @FXML
    private ImageView next;

    @FXML
    private Text currentTime;

    @FXML
    private Slider volumeSlider;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView stop;

    @FXML
    private ImageView prev;

    @FXML
    private Slider positionSlider;

    @FXML
    private ImageView pause;

    @FXML
    private ImageView playli;
    @FXML
    private ImageView volumeOn;
    @FXML
    private ImageView snapshot;
    @FXML
    private ImageView settings;
    @FXML
    private ImageView fullscreen;
    private static int count = 1;
    @FXML  private ImageView equalizer;
  
    @FXML
    private VBox vbox;
    
    private  FControlDialog controlDialog;

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
            root = FXMLLoader.load(getClass().getResource("/res/floatingControls.fxml"));

            Scene scene = new Scene(root);
            
            fxContainer.setScene(scene);
            // new SinglePlayer().startControls();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void mouseDragged(ActionEvent event) {
        //  int x = event.getClass().
        //  int y = event.getYOnScreen();

    }


    public void setCurrentTime(String time) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  init();
        int num = 10;
        initializeStuff();
    }
    
    public  void onPlayControls(){
    	if(mediaPlayerX.isPlaying()) {
            Image image = new Image("/res/play.png");
            pause.setImage(image);
            mediaPlayerX.pause();
            new MediaShare().onPauseGraphics();
        }else{
            Image image = new Image("/res/pause.png");
            pause.setImage(image);
            mediaPlayerX.play();
            new MediaShare().onPlayGraphics();
        }

    }
    public void setlocation(int x, int y){
    	controlDialog.setLocation(x, y);
    }
    
    public void initializeStuff(){

        // executorService.scheduleAtFixedRate(new UpdateRunnable(new SinglePlayer().getMediaPlayer1()), 0L, 1L, TimeUnit.SECONDS);
        // positionSlider.setValue(50);
    	

    	hbox.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println("enterd");
			       
			}
    		
    	});
    	hbox.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// int x = (int) e.getX();
				// int y = (int) e.getY();
                  PointerInfo a = MouseInfo.getPointerInfo();
                 Point p = a.getLocation();
                 int x = (int) ((int) p.getX()-405/2);
                 int y = (int) ((int) p.getY()-38/2);
                 new MediaShare().setFControlLocation(x,y);
				System.out.println("x "+x+"y "+y);
			}
			
		});
/*
        pause.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(mediaPlayer1.isPlaying()) {
                    Image image = new Image("/res/play.png");
                    pause.setImage(image);
                    mediaPlayer1.pause();
                    new MediaShare().onPauseGraphics();
                }else{
                    Image image = new Image("/res/pause.png");
                    pause.setImage(image);
                    mediaPlayer1.play();
                    new MediaShare().onPlayGraphics();
                }

            }
        });
        stop.addEventHandler(MouseEvent.MOUSE_PRESSED , new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/stop.png");
                stop.setImage(image);

            }
        });
        stop.addEventHandler(MouseEvent.MOUSE_RELEASED , new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/stop.png");
                stop.setImage(image);
                 mediaPlayer1.stop();
            }
        });
        stop.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET , new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/stop.png");
                stop.setImage(image);

            }
        });
        stop.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET , new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/stop.png");
                stop.setImage(image);

            }
        });

        prev.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/prev.png");
                prev.setImage(image);

            }
        });
        prev.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/prev.png");
                prev.setImage(image);

            }
        });
        prev.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/prev.png");
                prev.setImage(image);
                   mediaListPlayer1.playPrevious();
            }
        });
        prev.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/prev.png");
                prev.setImage(image);

            }
        });
        next.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/next.png");
                next.setImage(image);

            }
        });
        next.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/next.png");
                next.setImage(image);

            }
        });
        next.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/next.png");
                next.setImage(image);
                mediaListPlayer1.playNext();

            }
        });
        next.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Image image = new Image("/res/hover/next.png");
                next.setImage(image);


            }
        });
*/
        fullscreen.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                new MediaShare().fullscreen();

            }
        });




        volumeOn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(!mediaPlayerX.isMute()) {
                    Image image = new Image("/res/volume-muted.png");
                    volumeOn.setImage(image);
                    mediaPlayerX.mute(true);
                    volumeSlider.setDisable(true);
                }else{
                    Image image = new Image("/res/volume-high.png");
                    volumeOn.setImage(image);
                    mediaPlayerX.mute(false);
                    volumeSlider.setDisable(false);
                }

            }
        });



        snapshot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File dir = new File(System.getProperty("user.home") + "\\Videos\\MediaShare\\snapshots");
                dir.mkdirs();
               String savePath = dir.getAbsolutePath();

                	count++;
                	
                
                mediaPlayerX.saveSnapshot(new File(savePath+"\\"+"snapshot"+count+".png"));
                count++;
            }
        });
        equalizer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                JOptionPane.showMessageDialog(mainFrame,"Sorry Equalizer is not supported Yet","Play,Stream & Share - Media Share",JOptionPane.ERROR_MESSAGE,new ImageIcon(getClass().getResource("/res/xmp.png")));

                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/res/equalizer/equalizer.fxml"));
                    Scene scene = new Scene(root,600,263);
                    Stage primaryStage = new Stage();
                 //   scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Play,Stream & Share - Media Share");
                    primaryStage.centerOnScreen();
                   primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.setAlwaysOnTop(true);
                    primaryStage.show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        
      
       // hbox.setPrefWidth(1000.0);
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				
				arg0.consume();
				System.out.println("focused");
			}
        	
        });

       // positionSlider.setDisable(true);
        positionSlider.setMaxHeight(10);
        positionSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

              System.out.println("mouse clicked");
               // setSliderBasedPosition();
                //
            }
        });
        positionSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSliderBasedPosition();
            }
        });
        positionSlider.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
              //  setSliderBasedPosition();
            }
        });

        positionSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSliderBasedPosition();
            }
        });

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                int volumeValue = (int) (volumeSlider.getValue()) * 2;
                if (volumeValue > 200) {
                    volumeValue = 200;
                }

                new MediaShare().updateVolume(volumeValue);


            }
        });
        positionSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {







            }
        });

       //  executorService.scheduleAtFixedRate(new UpdateRunnable(getCurrentTime()), 0L, 1L, TimeUnit.SECONDS);
        executorService = Executors.newFixedThreadPool(1);


        Timeline timeline;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    currentTime.setText(time);
                   positionSlider.setValue(mediaPlayerX.getPosition()*100.0f);
                }
            });

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    private void setSliderBasedPosition() {
        if(!mediaPlayerX.isSeekable()) {
            return;
        }

        float positionValue = (float)positionSlider.getValue() / 100.0f;
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }


        mediaPlayerX.setPosition(positionValue);

    }

    public static void updatePosition(int value) {
//      positionSlider.setValue(value/1000.0f);

    }

    public void setTime() {

    }

    public void displayCurrentTime(String mytime) {

        time = mytime;

    }

	
}



