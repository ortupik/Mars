package mp3Player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import components.MediaShare;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import lyrics.DbConnect;
import lyrics.Lyrics;



public class MainStage extends MediaShare{

	 private static JFXPanel fxContainer;
	private static MediaPlayer mediaPlayer;
	private static AudioSpectrumListener spectrumListener;
    private DoubleProperty leftVU = new SimpleDoubleProperty(0);
    private DoubleProperty rightVU = new SimpleDoubleProperty(0);
    private static  Spectrum[] spectrum = new Spectrum[20];
    private static Stage primaryStage;
    
	private JPanel lyricPanel;
	private  JTextPane lyricPane;
	private  JScrollPane s;
	private	JPanel mp3Info ;
	private JTextPane mp3Details;
	  private static  JLabel	mp3Pic;
	  private static  String mediaPath;
	

	  public MainStage(String mediaPath){
		  this.mediaPath = mediaPath;
	  }
	public static void play(){
		if(mediaPath.equals(""))
			mediaPath = MediaShare.getCurrentMediaPath();
		
		final Media media = new Media(("file:///"+mediaPath).trim().replaceAll("\\\\", "//"));
		System.out.println("from mainstage ="+("file:///"+mediaPath).trim().replaceAll("\\\\", "//") );
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
      mediaPlayer.setMute(false);
        mediaPlayer.setOnError(new Runnable() {
            @Override public void run() {
                System.out.println("mediaPlayer.getError() = " + mediaPlayer.getError());
            }
        });
        createSpectrum();
	}
	public static void createSpectrum(){

        for (int i=0; i<20; i++) spectrum[i] = new Spectrum();
        
        spectrumListener = new AudioSpectrumListener() {
            @Override public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                double avarage = 0;
                for (int i=0; i<20; i++) {
                    spectrum[i].setValue((60+magnitudes[i])/60);
                    if (i<3) avarage += ((60+magnitudes[i])/60);
                }
               
            }
        };
        for (int i=0; i<20; i++) {
            spectrum[i].setLayoutX(50+(58*i));
            spectrum[i].setLayoutY(100);
        }
		  mediaPlayer.setAudioSpectrumNumBands(20);
	      mediaPlayer.setAudioSpectrumListener(spectrumListener);
	      mediaPlayer.setAudioSpectrumInterval(1d/30d);
	      
	        
	}
	 @Override
	    public void init() {
	        fxContainer = new JFXPanel();
	        fxContainer.setBackground(java.awt.Color.WHITE);
	        int width =  MediaShare.getMainFrame().getWidth();
	       //   fxContainer.setPreferredSize(new Dimension(width,30));
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

	        play();  
	        Group root = new Group();  
	        root.getChildren().addAll(spectrum); 
	        Scene scene = new Scene(root,width*3/4,100,Color.BLACK);
	        fxContainer.setScene(scene);

	    }
	    
	    public JPanel loadMp3gui(){
			  
		 	 try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception e){} 
			  
			  JPanel mp3Panel = new JPanel();
			  mp3Panel.setBackground(java.awt.Color.BLACK);
			
			  Thread t1= new Thread(new Runnable(){
	   				public void run(){	
	   				 JInternalFrame picPanel = new JInternalFrame();
			        	mp3Pic = new JLabel(new ImageIcon("E://tempMp3//mp3Image.png"));
			             mp3Pic = new MP3(null).getMp3label();
			        	
			        	
			        	
			             picPanel.setContentPane(mp3Pic);
			             picPanel.setVisible(true);
			             picPanel.setPreferredSize(new Dimension(300,500));
			            // mp3Panel.add(picPanel,BorderLayout.SOUTH);
			            
	   				} 
	   			});
	   			t1.start();
			 
			  Thread tMain= new Thread(new Runnable(){
					public void run(){
					  

			            mp3Info = new JPanel();
			            mp3Info.setBackground(new java.awt.Color(229,227,224));

			            String ourMp3details;
			            ourMp3details =new MP3(null).getMp3details();
			             mp3Details = new JTextPane();		           
			            mp3Details.setText(ourMp3details ); 			             
			            mp3Details.setEditable(false);
			           mp3Details.setBackground(new java.awt.Color(47,79,79) );
			        mp3Details.setForeground(java.awt.Color.WHITE.darker());
			            mp3Details.setFont(new Font("Comic Sans Ms",Font.PLAIN,15));	          
			            mp3Info.add(mp3Details);
			         //   mp3Panel.add(mp3Info,BorderLayout.NORTH);
			          
			          

						
						 lyricPanel = new JPanel();
						lyricPanel.setLayout(new BorderLayout());
						 lyricPane = new JTextPane();
						lyricPane.setEditable(false);
						lyricPane.removeAll();
						
						//lyricPane.setParagraphAttributes(attribs,true);
						
						lyricPane.setBackground( java.awt.Color.BLACK);
						lyricPane.setForeground(java.awt.Color.GRAY);
						lyricPane.setFont(new Font("Century",Font.PLAIN,17));	
						//lyricPane.setForeground(  java.awt.Color.WHITE); 
						
						
						StyledDocument doc = lyricPane.getStyledDocument();
						SimpleAttributeSet center = new SimpleAttributeSet();
						StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
						doc.setParagraphAttributes(0, doc.getLength(), center, false);
								
						//s.add(textArea,BorderLayout.CENTER);
						
						 s = new JScrollPane();
						s.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
						 s.setViewportBorder(null);
						s.setViewportView(lyricPane);
						lyricPanel.add(s,BorderLayout.CENTER);
					
						//lyricPanel.setBackground(new java.awt.Color(229,227,224));//229,227,224
						lyricPanel.setPreferredSize(new Dimension(width*3/4,height*3/4));
						mp3Panel.add(lyricPanel,BorderLayout.CENTER);
			

					          lyricPane.removeAll();
					   		lyricPane.setText("");
					   	    String [] mp3Details;
					   		mp3Details = new MP3(null).getDetails();
							
					   		String title = mp3Details[0].substring(7, mp3Details[0].length());
					   		
					        String SQL = "SELECT `lyrics` FROM `lyrics` WHERE `title` = '"+title+"' ";
					        java.sql.Connection connection = new DbConnect().connectDb();
					        
					        try {    
					          PreparedStatement  statement = connection.prepareStatement(SQL);
					          ResultSet resultset = statement.executeQuery();
					          if(resultset.next()){
					        	  String lyrics = resultset.getString("lyrics");
					        	  lyricPane.setText("");
					        	 
								//  lyricPane.setText(lyrics.substring(585,lyrics.length()));//.substring(596,new Lyrics().getLyrics().toString().length())
                            System.out.println(lyrics);
					          }else{
					        	  
								//lyricPane.setText(new Lyrics().getLyrics().toString().substring(596,new Lyrics().getLyrics().toString().length()));//.substring(596,new Lyrics().getLyrics().toString().length())
					        	  new Lyrics().getLyrics().toString();
					        	  if(resultset.next()){
						        	  String lyrics = resultset.getString("lyrics");
									  lyricPane.setText(lyrics.substring(585,lyrics.length()));//.substring(596,new Lyrics().getLyrics().toString().length())
	                              System.out.println(lyrics);
					          }
					        }
					        } catch (SQLException ex) {
					            JOptionPane.showMessageDialog(null, ex);
					        }
					   					
			   			
			   			
			       
					}
				});
			  tMain.start();
		try {
			tMain.join();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			  
			      
			   			
			            	
			         
       
          Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					
					int maxScroll = s.getVerticalScrollBar().getMaximum();
		         	int maxTime = new MP3(null).getmp3Length();
		         	//Duration Time = mediaPlayer.getCurrentTime();
		         	int currentTime = 1;
		         	int currentScroll = (maxScroll*currentTime)/maxTime;
		                   int remainScroll = maxScroll -currentScroll;
							int remainTime = maxTime - currentTime;
							int scrollSize = remainScroll/remainTime;
							System.out.println("Current time "+currentTime);
							System.out.println("MAx time "+maxTime);
							
					// TODO Auto-generated method stub
					s.getVerticalScrollBar().setValue(currentScroll);
					System.out.println("current scrool "+currentScroll);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					for(int i = 0; i<s.getVerticalScrollBar().getMaximum(); i++){
						s.getVerticalScrollBar().setValue(i*13);
						
						try {
						
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
					
				}
				
			});t.start();
			
			
			
			           
			          
			             
			             return mp3Panel;
	          
		}
	
}
