package share;

/**
 * Created by chris on 1/11/2016.
 */

import com.sun.org.apache.xpath.internal.operations.Bool;
import components.MediaShare;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PcReceiveController  implements Initializable{


    @FXML
    private Text filename;

    @FXML
    private Text fileSize;

    @FXML
    private Text savePath;

    @FXML
    private Button change;

    @FXML
    private Text host;

    @FXML
    private Button save;

    @FXML
    private Text filesize;


  private static Stage primaryStage;

    private static Socket socket;
    private ExecutorService executor;
    private ObjectInputStream ois;
    private Thread onStartThread = null;
    private FileOutputStream fos;
    private static int countFiles = 0;
    private static Boolean closeconnection = true;
    protected static String hostName;
    protected static String fileSizeValue;

    private static String filenameS  ;
    private static String fileSizeS;
    private static String hostS ;
    private static String ipS ;
    private static String ext;
    private  static String savePathS;
    private static Boolean pathChanged = false;
    private static String path;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        PcReceiveController.path = path;
    }


    public void onStart() {

        Thread detailsThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), 7878);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    System.out.println("pc Receive Details thread established");

                    String details [] =  (String[])ois.readObject();
                    final String [] finalDetails = details;

                        filenameS = finalDetails[0];
                         fileSizeS = finalDetails[1];
                        hostS = finalDetails[2];
                         ipS = finalDetails[3];
                         ext =finalDetails[4];
                        path = finalDetails[5];
                    setPath(path);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        detailsThread.start();
        try {
            detailsThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished joinning");
    }
    public void afterStart(){
        onStartThread = new Thread(new Runnable() {
            public void run() {
                try {
                    socket = new Socket(InetAddress.getLocalHost(), 8000);
                    System.out.println(InetAddress.getLocalHost());

                    hostName = socket.getInetAddress().getHostName();
                    System.out.println("pcReceive socket established");
                    startDownloadDialog();
                    onAcceptStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        onStartThread.start();

    }
    public void startDownloadDialog(){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new MediaShare().closeStage();
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("/share/res/download.fxml"));
                    primaryStage = new Stage();
                    primaryStage.initStyle(StageStyle.UTILITY);
                    primaryStage.setScene(new Scene(root, 910, 541));
                    primaryStage.setTitle("Play,Stream & Share - Media Share");
                    primaryStage.centerOnScreen();
                    primaryStage.setAlwaysOnTop(true);

                    primaryStage.centerOnScreen();
                    primaryStage.show();
                           /* Timeline timeline;
                            timeline = new Timeline(new KeyFrame(Duration.millis(2500), event -> {
                                Platform.runLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        primaryStage.close();
                                        new Share().onExit();

                                    }
                                });

                            }));
                            timeline.setCycleCount(1);
                            timeline.play();*/
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        });
    }

    public void onAcceptStream() {

        try {
            ois = new ObjectInputStream(socket.getInputStream());


            executor = Executors.newFixedThreadPool(1);
            executor.submit(new ReceiveFile());
            executor.shutdown();
            System.out.println("All Files received");

            executor.awaitTermination(1, TimeUnit.DAYS);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    private class ReceiveFile implements Runnable {
        private String path;

        public ReceiveFile() {
            countFiles++;
        }

        @Override
        public void run() {


            try {

                byte[] buffer = (byte[]) ois.readObject();
                System.out.println("reading files");
                if(pathChanged == false) {
                    File dir = new File(System.getProperty("user.home") + "\\Videos\\MediaShare\\videos");
                    dir.mkdirs();
                    savePathS = dir.getAbsolutePath();
                }
                fos = new FileOutputStream(savePathS+"\\"+filenameS + "." + ext);
                fos.write(buffer);
                System.out.println("writing files -- end finished");


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }finally {

               // JOptionPane.showMessageDialog(new MediaShare().getMainFrame(), "Finished Saving " + filenameS, "Save file - MediaShare", JOptionPane.DEFAULT_OPTION, new ImageIcon("/res/xmp.png"));


            }

        }
    }
public static void closeStage(){
    primaryStage.close();
}
    public void onExit() {

        try {
            closeconnection = false;
            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        host.setText(hostName.toUpperCase());
        fileSizeValue = new Share().getFileSizeValue();
        System.out.println(fileSizeValue);
        fileSize.setText(fileSizeValue);*/
       String pathS =System.getProperty("user.home")+"\\Videos\\MediaShare\\videos";
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
               // System.getProperty("videos");
                fileChooser.setInitialDirectory(new File(pathS));
                fileChooser.setInitialFileName(filenameS + "." + ext);
                fileChooser.setTitle("Change Save Folder - Media Share");
                File folder = fileChooser.showSaveDialog(null);
                savePath.setText(folder.getParent().toString());
                savePathS = folder.getParent().toString();
                pathChanged = true;
            }
        });

      new PcReceiveController().onStart();System.out.println("running againvbvbbfvfgfgf");
        filename.setText(filenameS);
        fileSize.setText(fileSizeS);
        host.setText(hostS.toUpperCase());

        savePath.setText(pathS);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                  new Share().afterStart();
                  new PcReceiveController().afterStart();

            }
        });
    }
}
