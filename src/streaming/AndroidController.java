package streaming;

import components.MediaShare;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Created by chris on 1/13/2016.
 */
public class AndroidController extends MediaShare implements Initializable{

    @FXML
    private Text ipAddress;

    @FXML
    private Text connectStatus;

    @FXML
    private Button connectButton;

    @FXML
    private Text host;




    private static String hostName;
    private static String ip;
    private static String screenName;
    private static String thePort;
    private static Boolean isConnected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             hostName =InetAddress.getLocalHost().getHostName().toString().toUpperCase();
             ip =InetAddress.getLocalHost().getHostAddress().toString();
            screenName = "1";
            thePort = "8050";
            ipAddress.setText(ip);

            host.setText(hostName);

            connectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    connectStatus.setText("Server started .. ");
                    connectStatus.setFill(Color.GREEN);

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            androidCommandServer();
                 
                        }
                    });
                   t.start();
                   Thread t2 = new Thread(new Runnable() {
                       @Override
                       public void run() {
                  
                           startPlaylistServer();
                       }
                   });
                  t2.start();


                   //
                      /*  if(isConnected==true) {
                    }else{
                        connectStatus.setText("NOT Connected ");
                        connectStatus.setFill(Color.RED);
                    }*/
                }
            });

        } catch (UnknownHostException e) {
        e.printStackTrace();
    }
    }
}
