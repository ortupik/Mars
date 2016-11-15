package components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class PlaylistClass  extends MediaShare implements Initializable {

	private static Stage  primaryStage;
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
	@FXML private Button undock;
	@FXML
	private Button close;
	@FXML
	private ImageView volumeOn;
	@FXML private VBox vboxMain;
	private Stage myStage;
	private static MainPlaylistDialog playlist ;

	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

	public HBox getHbox() {
		return hbox;
	}

	@FXML
	void mousePressed(MouseEvent event) {

	}

	@Override
	public void init() {
		fxContainer = new JFXPanel();

		int width = MediaShare.getMainFrame().getWidth() / 6;
		int height = MediaShare.getMainFrame().getHeight();
	    //fxContainer.setPreferredSize(new Dimension(width,height));
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
			
			FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/res/playlist2.fxml"));
			root = (Parent)loader.load();
			Scene scene = new Scene(root);
           
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
		startActivities();
	}
	public void closePlayslistStage(){

		playlist.hide();
	}
	public void setStage(Stage stage){
		myStage = stage;
	}
	public void setlocation(int x , int y){
		
		playlist.setLocation(x, y);
	}
	public void startActivities(){
		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				isPlaylistActivated =true;
				 isDockedPlaylistActivated =false;
				playlistCount = 100;
				frame.setVisible(false);
				mainFrame.revalidate();
				
			}
		});
			    
			
		
		undock.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				
				 
				
				 Platform.runLater(new Runnable() {

					  @Override
					  public void run() {
					

						  frame.setVisible(false);
						  mainFrame.revalidate();
						

						  if( isPlaylistActivated){
							  playlist= new MainPlaylistDialog(mainFrame);
							 playlist.setUndecorated(true);
							// playlist.getRootPane().setOpaque(false);
							// playlist.getContentPane().setBackground(new Color(0,0,0,0));
							// playlist.setBackground(new Color(0,0,0,0));
							  playlist.setSize(405, 532);
							  playlist.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-playlist.getWidth()/2
									  ,(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-playlist.getHeight()/2);
							 
							  playlist.setVisible(true);
							  playlist.show();
							  isPlaylistActivated = false;
							 /* try {
							  * 	  primaryStage = new Stage();
								  Parent root;
								  root = FXMLLoader.load(getClass().getResource("/res/playlist.fxml"));
								  primaryStage.initStyle(StageStyle.TRANSPARENT);
								  Scene scene = new Scene(root, 405, 532 ,javafx.scene.paint.Color.TRANSPARENT);
								  scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
								  primaryStage.setScene(scene);
								  primaryStage.setFilename("Play,Stream & Share - Media Share");
								  primaryStage.centerOnScreen();
								
								  isPlaylistActivated = false;

								  primaryStage.setAlwaysOnTop(true);


								  
	
								  primaryStage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
									  @Override
									  public void handle(javafx.stage.WindowEvent event) {

										  isPlaylistActivated = true;
										
									  }
								  });
								  
								  primaryStage.show();
							  } catch (IOException e1) {
								  e1.printStackTrace();
							  }

						
							  if (primaryStage.isIconified()) {
								  primaryStage.setIconified(false);

							  }*/
						  
					   }
					  }
					  

			  });
				 mainFrame.remove(applet2.getContentPane());
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
		PlaylistClass.mediaFilter = mediaFilter;
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
				//final String work ="D://Music";

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
						Dataplaylist.add(new Driverplaylist(" ▶  " + file.getName().toString()));//¶▶

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
						new MediaShare().getDisplayMediatitle().setText(data.replace(" ▶  ", ""));
						System.out.println(data);
						selectedMedia = data;
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


			}

		});
		playlistTable.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});
		playlistTable.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (event.getDragboard().hasString()) {
					String text = db.getString();
				//	JOptionPane.showMessageDialog(null, text);

					//Dataplaylist.add(new Driverplaylist(text));
					playlistTable.setItems(Dataplaylist);
					success = true;

				}


				event.setDropCompleted(success);
				event.consume();
			}
		});

		return Dataplaylist;
	}






}

        