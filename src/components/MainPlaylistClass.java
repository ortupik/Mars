package components;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainPlaylistClass extends MediaShare implements Initializable {

	private static Stage mainStage;
	@FXML
	private TextField searchField;
	@FXML
	private TableView playlistTable;

	@FXML
	private TableColumn titleColumn;
	private static JFXPanel fxContainer;

	private ObservableList<Driverplaylist> Dataplaylist;
	private static FilenameFilter mediaFilter;
	private static ExecutorService executorService;
	private static String searchText = "", selectedMedia = "";

	@FXML
	private Button top;
	@FXML
	private Text vol;


	public static String time;

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
	private ImageView close;
	@FXML private Button dock;

	@FXML
	private ImageView volumeOn;
    @FXML
    private Text playlistText;
	
    @FXML
    private HBox playListTitle;
    @FXML private VBox vboxMain;
    
    private static String hackedDragString;
    private static Boolean isDraggingPlaylist = true;
    
	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

	public HBox getHbox() {
		return hbox;
	}

	
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
	@FXML
	void mousePressed(MouseEvent event) {

	}

	@Override
	public void init() {
		fxContainer = new JFXPanel();

		fxContainer.setBackground(new java.awt.Color(0,0,0,0));
		int width = MediaShare.getMainFrame().getWidth() * 5 / 13;
		int height = MediaShare.getMainFrame().getHeight();
	    fxContainer.setPreferredSize(new Dimension(405, 532));
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

			Scene scene = new Scene(root,Color.TRANSPARENT);

			fxContainer.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override

	public void initialize(URL url, ResourceBundle rb) {
		playlistTable.getSelectionModel().select(3);

		getPlaylist();
		initializeStuff();
		startActivities();
	}
	
	public void startActivities(){
		playListTitle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// int x = (int) e.getX();
				// int y = (int) e.getY();
                  PointerInfo a = MouseInfo.getPointerInfo();
                 Point p = a.getLocation();
                 int x = (int) ((int) p.getX()-405/2);
                 int y = (int) ((int) p.getY()-38/2);
				 new PlaylistClass().setlocation(x,y);
				System.out.println("x "+x+"y "+y);
			}
			
		});
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				isPlaylistActivated =true;
				isDockedPlaylistActivated=false;
				playlistCount=100;
				new PlaylistClass().closePlayslistStage();

			}
		});
		dock.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				isPlaylistActivated = true;
				new PlaylistClass().closePlayslistStage();
				mainFrame.add(frame,BorderLayout.EAST);
				frame.setVisible(true);
				mainFrame.revalidate();

			}
		});
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

	public static void setMediaFilter(FilenameFilter mediaFilter) {
		MainPlaylistClass.mediaFilter = mediaFilter;
	}

	public static FilenameFilter getMediaFilter() {
		return mediaFilter;
	}


	public ObservableList<Driverplaylist> getPlaylist() {

		Dataplaylist = FXCollections.observableArrayList();

		Thread playlistThread = new Thread(new Runnable() {
			@Override
			public void run() {

				DefaultListModel listModel = new DefaultListModel();

				final String work = System.getProperty("user.dir");
				//final String work ="E://Music";

				setMediaFilter(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String lowerCasename = name.toLowerCase();
						if (lowerCasename.endsWith(".vob")) {

							return true;

						} else if (lowerCasename.endsWith(".mp4")) {
							return true;

						} else if (lowerCasename.endsWith(".mp3 files")) {
							return true;

						} else if (lowerCasename.endsWith(".mp3")) {

							return true;

						} else if (lowerCasename.endsWith(".avi")) {
							return true;

						} else if (lowerCasename.endsWith(".3gp")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg3")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg4")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg2")) {
							return true;

						} else if (lowerCasename.endsWith(".webm")) {
							return true;

						} else if (lowerCasename.endsWith(".VOB")) {
							return true;

						} else if (lowerCasename.endsWith(".flv")) {
							return true;

						} else if (lowerCasename.endsWith(".mkv")) {
							return true;

						} else {
							return false;
						}
					}
				});

				File currentDirectory = new File(work);
				File[] flist1 = currentDirectory.listFiles(getMediaFilter());


				for (File file : flist1) {
					if (file.isFile()) {
						Dataplaylist.add(new Driverplaylist(" ▶  " + file.getName().toString()));//▶

					}
				}
				//mediaShare.androidCommandServer();

			}

		});
		playlistThread.start();

		titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Driverplaylist> filteredData = new FilteredList<>(Dataplaylist, p -> true);

		searchField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					new MediaShare().setEventStatus(false);
				else if (oldValue)
					new MediaShare().setEventStatus(true);
			}
		});
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
		playlistTable.setFocusTraversable(true);
		playlistTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				if (event.getClickCount() == 2) {

					int pos = playlistTable.getSelectionModel().getSelectedIndex();
					if (pos >= 0) {

						String data = titleColumn.getCellData(pos).toString();
						new MediaShare().play(data.replace(" ▶  ", ""));
						new MediaShare().setTitle(data.replace(" ▶  ", ""));
						titleColumn.setText("Now Playing: "+data.replace(" ▶  ", ""));
						new MediaShare().getDisplayMediatitle().setText(data.replace(" ▶  ", ""));
						System.out.println(data);
						selectedMedia = data;
					}

				}else if (event.getClickCount() == 1){
					int pos = playlistTable.getSelectionModel().getSelectedIndex();
					if (pos >= 0) {
					hackedDragString = titleColumn.getCellData(pos).toString();
					}
				}

			}
		});


		playlistTable.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				String selected = playlistTable.getSelectionModel().getSelectedItem().toString();
				if (selected != null) {
					Dragboard db = playlistTable.startDragAndDrop(TransferMode.ANY);
					ClipboardContent cb = new ClipboardContent();
					cb.putString(selected);
					db.setContent(cb);
					arg0.consume();
				}
				isDraggingPlaylist = true;

			}

		});
		playlistTable.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				String data = hackedDragString;
				System.out.println("dragged text "+data);
				new MediaShare().play(data.replace(" ▶  ", ""));
				new MediaShare().setTitle(data.replace(" ▶  ", ""));
				titleColumn.setText("Now Playing: "+data.replace(" ▶  ", ""));
				new MediaShare().getDisplayMediatitle().setText(data.replace(" ▶  ", ""));
				//playlistTable.setItems(Dataplaylist);
				isDraggingPlaylist = false;
				event.consume();
			}
		});
		playlistTable.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				
				if (event.getDragboard().hasString()) {
				}
					

				
success = true;

				event.setDropCompleted(success);
				
				event.consume();
			}
		});

		return Dataplaylist;
	}
public static Boolean getDragPlaylisStatus(){
	return isDraggingPlaylist;
}
	public void finishStuff() {

		// searchText = searchField.getText();

		//	JOptionPane.showMessageDialog(null, selectedMedia);


	}

	public void onPlayControls() {
		if (mediaPlayerX.isPlaying()) {
			Image image = new Image("/res/play.png");
			pause.setImage(image);
			mediaPlayerX.pause();

		} else {
			Image image = new Image("/res/pause.png");
			pause.setImage(image);
			mediaPlayerX.play();

		}

	}

	public void initializeStuff() {



		// executorService.scheduleAtFixedRate(new UpdateRunnable(new SinglePlayer().getMediaPlayer1()), 0L, 1L, TimeUnit.SECONDS);
		// positionSlider.setValue(50);

		pause.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (mediaPlayerX.isPlaying()) {
					Image image = new Image("/res/play.png");
					pause.setImage(image);
					mediaPlayerX.pause();
				} else {
					Image image = new Image("/res/pause.png");
					pause.setImage(image);
					mediaPlayerX.play();
				}

			}
		});
		stop.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Image image = new Image("/res/stop.png");
				stop.setImage(image);

			}
		});
		stop.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Image image = new Image("/res/hover/stop.png");
				stop.setImage(image);
				mediaPlayerX.stop();
			}
		});
		stop.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Image image = new Image("/res/stop.png");
				stop.setImage(image);

			}
		});
		stop.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
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
				playlistTable.getSelectionModel().selectPrevious();
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
				playlistTable.getSelectionModel().selectNext();
			}
		});
		next.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Image image = new Image("/res/hover/next.png");
				next.setImage(image);


			}
		});


		volumeOn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (!mediaPlayerX.isMute()) {
					Image image = new Image("/res/volume-muted.png");
					volumeOn.setImage(image);
					mediaPlayerX.mute(true);
					volumeSlider.setDisable(true);
				} else {
					Image image = new Image("/res/volume-high.png");
					volumeOn.setImage(image);
					mediaPlayerX.mute(false);
					volumeSlider.setDisable(false);
				}

			}
		});


		// positionSlider.setDisable(true);
		positionSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				//    System.out.println(timeLabel);
				setSliderBasedPosition();
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
				// setSliderBasedPosition();
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
					positionSlider.setValue(mediaPlayerX.getPosition() * 100.0f);
				}
			});

		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}

	private void setSliderBasedPosition() {
		if (!mediaPlayerX.isSeekable()) {
			return;
		}

		float positionValue = (float) positionSlider.getValue() / 100.0f;
		if (positionValue > 0.99f) {
			positionValue = 0.99f;
		}


		mediaPlayerX.setPosition(positionValue);

	}

	public static void updatePosition(int value) {
//        positionSlider.setValue(value/1000.0f);

	}

	public void setTime() {

	}

	public void displayCurrentTime(String mytime) {

		time = mytime;

	}
}

        