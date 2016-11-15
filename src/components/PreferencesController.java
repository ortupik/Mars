package components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by chris on 1/20/2016.
 */
public class PreferencesController implements Initializable {
    @FXML
    private RadioButton manualProxy;


    @FXML
    private RadioButton systemProxy;

    @FXML
    private RadioButton noProxy;

    @FXML
    private CheckBox checkbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  new MediaLookCheckBoxSkin(checkbox);
    }
}
