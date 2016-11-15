package tester;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PlaylistClass  extends JApplet implements Initializable {

	private static Stage mainStage;
	@FXML
	private TextField searchField;
	@FXML
	private TableView playlistTable;

	@FXML
	private TableColumn titleColumn;
	private static JFXPanel fxContainer;

	private ObservableList<Driverplaylist> Dataplaylist;

	/*@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/res/playlist.fxml"));

		primaryStage.setScene(new Scene(root, 406, 552));
		primaryStage.setFilename("playlistTable SYSTEM");

		mainStage = primaryStage;
		mainStage.centerOnScreen();
		mainStage.show();
	}


		public void close(){
			mainStage.close();
		}

		public static void main(String[] args) {
			launch(args);
		}
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
			root = FXMLLoader.load(getClass().getResource("/res/playlist.fxml"));

			Scene scene = new Scene(root);

			fxContainer.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


      @Override

    public void initialize(URL url, ResourceBundle rb) {
		  getPlaylist ();

	}

    public class Driverplaylist {
		private String title;

		public Driverplaylist() {
			this.title = "";
		}

		public Driverplaylist(String title) {
			this.title = title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	}



		public ObservableList<Driverplaylist> getPlaylist () {

			 Dataplaylist = FXCollections.observableArrayList();

			final String work = System.getProperty("user.dir");
			File Directory = new File("E://Music");
			File[] flist = Directory.listFiles();
			for (File vfile : flist) {
				if (vfile.isFile()) {
					Dataplaylist.add(new Driverplaylist(vfile.getName().toString()));
				}
			}

			titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
			// 1. Wrap the ObservableList in a FilteredList (initially display all data).
			FilteredList<Driverplaylist> filteredData = new FilteredList<>(Dataplaylist, p -> true);

			// 2. Set the filter Predicate whenever the filter changes.
			searchField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(t -> {
                    // If filter text is empty, display all
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
					String lowerCaseFilter = newValue.toLowerCase();
					if (t.title.toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches title
					}
                    return false; // Does not match.
                });
			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Driverplaylist> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(playlistTable.comparatorProperty());
			// 5. Add sorted (and filtered) data to the table.
			playlistTable.setItems(sortedData);
		

			return Dataplaylist;
		}

	}


        