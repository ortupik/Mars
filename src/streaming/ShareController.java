package streaming;

import components.MediaShare;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import share.Share;

import javax.swing.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Created by chris on 1/13/2016.
 */
public class ShareController implements Initializable{
    @FXML
    private Text port;

    @FXML
    private Text ipAddress;

    @FXML
    private Text screen;

    @FXML
    private Text shareStatus;
    @FXML
    private Text host;






    private static String hostName;
    private static String ip;
    private static String screenName;
    private static String thePort;
    private static  Boolean isShared = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             hostName =InetAddress.getLocalHost().getHostName().toString().toUpperCase();
             ip =InetAddress.getLocalHost().getHostAddress().toString().trim();
            screenName = "1";
            thePort = "8050";
            port.setText(thePort);
            ipAddress.setText(ip);
            screen.setText(screenName);
            host.setText(hostName);

            if(isShared == false) {
                new StreamServer().receiveDetails(ip, thePort, screenName);
                String path = new MediaShare().getCurrentMediaPath();
                System.out.println("from share controller " + path);
                new Share().onStart(path);
                isShared = true;
                shareStatus.setText(" Shared ");
                shareStatus.setFill(Color.GREEN);
            }else{
                shareStatus.setText("ALREADY SHARED !");
                shareStatus.setFill(Color.RED);
              //  JOptionPane.showMessageDialog(MediaShare.getMainFrame(), "Already shared", "Play,Stream & Share - Media Share", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/res/xmp.png")));

            }

        } catch (UnknownHostException e) {
        e.printStackTrace();
    }
    }
}
