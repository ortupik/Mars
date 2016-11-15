package streaming;

/**
 * Created by chris on 1/12/2016.
 */
import components.MediaShare;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;


public class ReceiveController implements Initializable {


        @FXML
        private Text filename;

        @FXML
        private TextField screen;

        @FXML
        private TextField ipField;

        @FXML
        private TextField portField;

        @FXML
        private Button connect;

    @FXML
    private CheckBox advanced;

    @FXML
    private Text localName;

    @FXML
    private Text hostName;

    @FXML
    private VBox advancedPanel;

   private static String text = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                       new MediaShare().playStream(ipField.getText(), portField.getText(), screen.getText());

            }
        });

    }
}

