package share;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by chris on 1/13/2016.
 */
public class SavedNoticeController implements Initializable {
    @FXML
    private Button close;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
             close.setOnAction(new EventHandler<ActionEvent>() {
                 @Override
                 public void handle(ActionEvent event) {
                     new PcReceiveController().closeStage();
                 }
             });
    }
}
