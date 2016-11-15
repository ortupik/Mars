package share;

import components.MediaShare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by chris on 2/27/2016.
 */
public class DownloadController implements Initializable {


    private static String path;
    private static HashMap<String ,String> playlistMap = new HashMap<String ,String>();
    @FXML private TableView downloadTable;


    @FXML  private TableColumn filenameColumn;
    @FXML  private TableColumn sizeColumn;
    @FXML  private TableColumn rateColumn;
    @FXML  private TableColumn statusColumn;
    @FXML  private TableColumn timeColumn;
    private static JFXPanel fxContainer;

    private ObservableList<DriverDownload> DataDownloadsList;
    private static FilenameFilter mediaFilter;


    public ObservableList<DriverDownload> getPlaylist() {

        DataDownloadsList = FXCollections.observableArrayList();

        Thread playlistThread = new Thread(new Runnable() {
            @Override
            public void run() {

                DefaultListModel listModel = new DefaultListModel();

                final String work = System.getProperty("user.home") + "\\Videos\\MediaShare\\videos";

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
                        String filename1 = file.getName().toString();
                        String filepath1 = file.getName().toString();
                        playlistMap.put(file.getName().substring(0, filename1.length() - 4).trim(), file.getAbsolutePath().trim());
                        String filename = file.getName().toString().substring(0,filename1.length()-4);
                        double size = ((double) file.length() / 1000000);
                        String fileSize = String.valueOf(size + " MB");
                   //     System.out.println(file.getName().substring(0,filename1.length()-4).trim()+"\t " + file.getAbsolutePath().trim());
                        DataDownloadsList.add(new DriverDownload(filename.substring(0,filename1.length()-4),fileSize,"complete","0 Sec",""));


                    }
                }

            }

        });
        playlistThread.start();

        filenameColumn.setCellValueFactory(new PropertyValueFactory("filename"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory("fileSize"));
        statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
        rateColumn.setCellValueFactory(new PropertyValueFactory("rate"));



        downloadTable.setItems(DataDownloadsList);
        downloadTable.setFocusTraversable(true);
        downloadTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                    int pos = downloadTable.getSelectionModel().getSelectedIndex();
                    if (pos >= 0) {

                        String data = filenameColumn.getCellData(pos).toString();
                        System.out.println(data);
                         path = (playlistMap.get(data.trim()));
                        System.out.println("path "+path);
                        new MediaShare().playByPath(path);
                        new MediaShare().getDisplayMediatitle().setText(data);
                    }

                }

            }
        });


        return DataDownloadsList;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPlaylist();
    }

    public class DriverDownload {
        private String filename;
        private String fileSize;
        private String status;
        private String time;
        private String rate;


        public String getFileSize() {
            return fileSize;
        }

        public void setSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public DriverDownload()
        {
            this.filename = "";
            this.fileSize = "";
            this.status = "";
            this.time = "";
            this.rate = "";

        }

        public DriverDownload(String filename, String fileSize,String status,String time,String rate) {

            this.filename = filename;
            this.fileSize = fileSize;
            this.status = status;
            this.time = time;
            this.rate = rate;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }
    }

    public static void setMediaFilter(FilenameFilter mediaFilter) {

        DownloadController.mediaFilter = mediaFilter;
    }

    public static FilenameFilter getMediaFilter() {

        return mediaFilter;
    }

}
