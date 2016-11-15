package tester;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.EventHandler;
import java.io.EOFException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;

import javax.swing.*;

import javafx.scene.text.Text;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;





public class SinglePlayer extends JApplet implements WindowListener, NativeKeyListener {



	private static  Boolean eventStatus; 
	//embedded media player
	protected  EmbeddedMediaPlayer  mediaPlayer1;
 
//media list
	private static MediaList mediaList1;
   //media Factories
  private   MediaPlayerFactory mediaPlayerFactory1;
 
//mediaListPlayers
	protected  MediaListPlayer mediaListPlayer1;
	private static Canvas frame;
		
	private static String mediaPath;
	private static JTextPane statusBar;
	private static  JPanel panel;
	 private  Timer timer;
	 private static  FilenameFilter mediaFilter ; 
	 private boolean isShiftPressed;
	 private static int countSnapshot;
	 
	  private	  static int height;
	  private	  static  int width;
	  private JPanel controls;
	  

        private   ObjectOutputStream outputStream1;
        private  ObjectInputStream inputStream1;
		private  ServerSocket server1;
		private  ServerSocket server2;
		private  Socket connection1;
		private   String androidcommand ;	
	 private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

          private boolean mousePressedPlaying = false;
          private   JLabel playLabel,pauseLabel;
          private Boolean setupComplete = false;
          private static  ComponentResizer cr = new ComponentResizer();
          
          private static final int JFXPANEL_WIDTH_INT = 300;
          private static final int JFXPANEL_HEIGHT_INT = 250;
          private static JFXPanel fxContainer;

	private static JSlider positionSlider;





	public static void main (String args[])    {
		
		 height   = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	     width =  (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	  Toolkit.getDefaultToolkit().setDynamicLayout(true); 
		  
		               panel = new JPanel();  
		               panel.setBackground(Color.black);
		               JFrame f = new JFrame();	
						  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
						f.setUndecorated(true);
						  f.setDefaultLookAndFeelDecorated(true);
						  f.setAlwaysOnTop(true);
						  final SinglePlayer s = new SinglePlayer();
						  s.framework(); 
					      f.setBounds(0,0,1200,749);//width*2/3,height*3/41200,749
						  f.setLocation(100, 100);
						  
			            
			               
						
						 f.setResizable(true);
						// f.setUndecorated(true);
					     cr.setMaximumSize(new Dimension(1200,749));
					     cr.setMinimumSize(new Dimension(200,59));
					     cr.setSnapSize(new Dimension(10,10));
					    cr.registerComponent(f);
					   
						
							 panel.setLayout(new BorderLayout());
							 frame = new Canvas();
							 frame.setBackground(Color.BLACK);
							 //f.add(applet1.getContentPane(),BorderLayout.NORTH);
							  
							   
							 //  applet.getContentPane().setBounds(0,700,1200,49);				
						//	 frame.setBounds(0,0,1200,749); 
							 panel.add(frame,BorderLayout.CENTER);
					         f.add(panel,BorderLayout.CENTER);
	                    	//f.add(applet3.getContentPane(),BorderLayout.WEST);
		                  //   f.add(applet2.getContentPane(),BorderLayout.EAST);
					        cr.registerComponent(frame);
					         cr.registerComponent(panel);
					          f.setVisible(true);
						// applet.start();
		               //  applet1.start();
	                	//applet2.start();
		         		 			
					          StringBuilder sb = new StringBuilder();
							        for (String ss : args){
							        	sb.append(ss);
							        }
							        
								     mediaPath = sb.toString().toUpperCase();
					        
						  Thread t = new Thread(new Runnable(){

							@Override
							public void run() {
							
								s.player(); 

								   JApplet applet = new SinglePlayer();
					               applet.init();
					                cr.registerComponent(applet.getContentPane());
					               f.add(applet.getContentPane(),BorderLayout.SOUTH);
					               cr.registerComponent(applet);
								   cr.registerComponent(applet.getContentPane());
					            
							}
							  
						  });t.start();//addWindowListener(f);
						  try {
							t.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						  Thread t2 = new Thread(new Runnable(){

							@Override
							public void run() {
							s.initStuff();
								 
							
					               
							}
							  
						  });//t2.start();
						  try {
								t2.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						  
						  Thread playlistThread = new Thread(new Runnable(){
								@Override
								public void run() {
									
									 DefaultListModel listModel = new DefaultListModel();
									  
									   final String work = System.getProperty("user.dir");
						  			 
						  			    	 setMediaFilter(new  FilenameFilter (){
						  				      	  public boolean accept (File dir,String name){
						  				      		 String lowerCasename = name.toLowerCase();
						  				      		 if(lowerCasename.endsWith(".vob")){
						  				      		
						  				      			 return true;
						  				      			 
						  				      		 }else if(lowerCasename.endsWith(".mp4")){	
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mp3 files")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mp3")){
						  				      			
						  				      			 return true;
						  				      			 			
						  				      		 }else if(lowerCasename.endsWith(".avi")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".3gp")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mpeg3")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mpeg4")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mpeg2")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".webm")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".VOB")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".flv")){
						  				      			 return true;
						  				      			
						  				      		 }else if(lowerCasename.endsWith(".mkv")){
						  				      			 return true;
						  				      			
						  				      		 }
						  				      		 else{
						  				      			 return false;
						  				      		 }
						  				      	  }
						  				        });
						  				        
						  				        File currentDirectory = new File(work);
						  				    	File []  flist1 = currentDirectory.listFiles(getMediaFilter());
						  				    	
						  				    
						  				     for (File file1 : flist1 ){
						  			  		    if(file1.isFile()){
						  			  			  mediaList1.addMedia(file1.getAbsolutePath());
						  			  			
						  			  		  }
						  				   }s.androidCommandServer();
						  				     
								}	
						    	 
						     });playlistThread.start();
						
			
	        }

	public SinglePlayer(){
		//super("Media Suite Speed Test - TechBay");
		ImageIcon icon = new ImageIcon(getClass().getResource("/res/xmp.png"));
	//	setIconImage(icon.getImage());
	}
	public void initStuff(){
		 controls = new JPanel();
	//	panel.add(controls,BorderLayout.SOUTH);
		controls.setBackground(new Color(229,227,224)  );
		

	          ImageIcon playIcon = new ImageIcon(getClass().getResource("/res/play.png")); 
	          ImageIcon pauseIcon = new ImageIcon(getClass().getResource("/res/pause.png"));  
	          
	          JPanel mediaControls = new JPanel(); 
	          mediaControls.setBackground(new Color(229,227,224)  );
	      // mediaControls.setLayout(null);
		      
		  playLabel = new JLabel(playIcon);
          playLabel.setBounds(0, 0, 0,0);
        
          mediaControls.add(playLabel);
          playLabel.addMouseListener(
          		 new  MouseAdapter() {

						@Override
						public void mouseClicked(
								java.awt.event.MouseEvent ev) {
							 if(!mediaPlayer1.isPlaying()){
								
								// playLabel.setBounds(0, 0, 0, 0);
								//	 pauseLabel.setBounds(62, -2, 30,30);   
									 mediaPlayer1.play();
								   }
							 
						}
          		 }

  	    		 );
             pauseLabel = new JLabel(pauseIcon );
        pauseLabel.setBounds(62, 3, 30,20);
        mediaControls.add( pauseLabel);
	         pauseLabel.addMouseListener(
	        		 new  MouseAdapter() {
							@Override
							public void mouseClicked(
									java.awt.event.MouseEvent ev) {
								 
								 if(mediaPlayer1.isPlaying()){
									  // pauseLabel.setBounds(0, 0, 0, 0);
									  // playLabel.setBounds(60, -2, 30,30); 
									   mediaPlayer1.pause();
								   }  
								 
							}

      	    		 }
      	    		 
      	    		 );
       
	         ImageIcon prevIcon = new ImageIcon(getClass().getResource("/res/prev.PNG")); 
         JLabel prevLabel = new JLabel(prevIcon );
         prevLabel.setBounds(34, -1, 30,30);
         mediaControls.add(prevLabel);
       prevLabel.addMouseListener(
      		 new  MouseAdapter() {
					@Override
					public void mouseClicked(
							java.awt.event.MouseEvent ev) {
						 
						  if(mediaListPlayer1.isPlaying()){
							  mediaListPlayer1.playPrevious();
							
							   
							  }
					}

	    		 }
	    		 
	    		 );
       
        
       ImageIcon stopIcon = new ImageIcon(getClass().getResource("/res/stop.PNG")); 
         JLabel stopLabel = new JLabel(stopIcon );
         stopLabel.setBounds(125, 6, 15,15);
         mediaControls.add(stopLabel);
        stopLabel.addMouseListener(
      			 new  MouseAdapter() {

						@Override
						public void mouseClicked(
								java.awt.event.MouseEvent ev) {
							 
							mediaPlayer1.stop();
						}

  	    		 }
  	    		 
  	    		 );
        
        ImageIcon nextIcon = new ImageIcon(getClass().getResource("/res/next.PNG")); 
         JLabel nextLabel = new JLabel(nextIcon );
         nextLabel.setBounds(87, -1, 30,30);
         mediaControls.add(nextLabel);
        nextLabel.addMouseListener(
      			 new  MouseAdapter() {

						@Override
						public void mouseClicked(
								java.awt.event.MouseEvent ev) {
							if(mediaListPlayer1.isPlaying()){
							
								  mediaListPlayer1.playNext();
						 
							}
						}

  	    		 }
  	    		 
  	    		 );
        
        ImageIcon playliIcon = new ImageIcon(getClass().getResource("/res/playli.PNG")); 
         JLabel playliLabel = new JLabel(playliIcon );
         playliLabel.setBounds(1, 7, 35,15);
         mediaControls.add(playliLabel);
     
		
		   try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					
					} catch (ClassNotFoundException ew) {
					// TODO Auto-generated catch block
					ew.printStackTrace();
					} catch (InstantiationException eww) {
					// TODO Auto-generated catch block
					eww.printStackTrace();
					} catch (IllegalAccessException ewww) {
					// TODO Auto-generated catch block
					ewww.printStackTrace();
					} catch (UnsupportedLookAndFeelException ewwww) {
					// TODO Auto-generated catch block
					ewwww.printStackTrace();
					}
		
	
	  positionSlider = new JSlider();
		positionSlider.setMinimum(0);
   	   positionSlider.setMaximum(1000);
   	   positionSlider.setValue(0);
   	   positionSlider.setToolTipText("Position");
   
		
		 positionSlider.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mousePressed(MouseEvent e) {
			
			    // setSliderBasedPosition();
			      
			 }
			 @Override
			 public void mouseReleased(MouseEvent e) {
					 setSliderBasedPosition();
					
			     }
			 });
		 positionSlider.addMouseListener(new MouseAdapter(){
  		   public void mouseClicked(MouseEvent e){
  			 setSliderBasedPosition();
  		  	  int v= positionSlider.getValue();
  		  	  int mouseX = e.getX();
  		  	
  		  	  
  		  	  int progressVal = (int)Math.round(((double)mouseX/(double)positionSlider.getWidth())*positionSlider.getMaximum());
  		  	  positionSlider.setValue(progressVal);

  		  	 float positionValue = positionSlider.getValue() / 1000.0f;
  		  	   if(positionValue > 0.99f) {
  		  	   positionValue = 0.99f;
  		  	   }
  		  	  
  		  	mediaPlayer1.setPosition(positionValue);

  		  
  		
  		   }

  		  });
		executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer1), 0L, 1L, TimeUnit.SECONDS);

		
	    statusBar = new JTextPane();
		  statusBar.setEditable(false);
		  statusBar.setBackground(new Color(229,227,224)  );
	     statusBar.setFont(new Font("Century",Font.PLAIN,15));//12
	      statusBar.setText(mediaPath.toUpperCase());
	      controls.setLayout(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	     
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	      gbc.gridwidth = 1;
	      gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
	 
	      gbc.insets = new Insets(10,0,0,5);
	      controls.add(mediaControls,gbc);
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      gbc.gridwidth = 3;
	      gbc.insets = new Insets(10,5,0,0);
	      gbc.fill = GridBagConstraints.HORIZONTAL;
		  controls.add(positionSlider,gbc);
		  gbc.gridx = 1;
		  gbc.gridwidth = 2;
	      gbc.gridy = 1;
	      gbc.insets = new Insets(10,5,0,5);
	      gbc.fill = GridBagConstraints.HORIZONTAL;
	  //    controls.add(statusBar,gbc);
	    
	  
	  	timer = new Timer(5000, new ActionListener() {
	        public void actionPerformed(ActionEvent ae) {
	        	// stopTimer();
	        }
	      });
    	timer.setRepeats(false);
    	
		/*  StyledDocument doc = statusBar.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);*/
			
			frame.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					
					if(e.getClickCount()== 2){
						if(mediaPlayer1.isFullScreen()){
							mediaPlayer1.setFullScreen(false);
							 //panel.add(controls,BorderLayout.SOUTH);
							 
					  }else if(!mediaPlayer1.isFullScreen()){
						mediaPlayer1.setFullScreen(true);		
						//panel.remove(controls);
						//panel.revalidate();
						//timer.stop();
					  }
					}
				}
			});
			 frame.addMouseWheelListener(
		    		  new MouseWheelListener(){

						public void mouseWheelMoved(MouseWheelEvent e) {
							
							int notches = e.getWheelRotation()*5;
							if(notches < 0){
							  if(mediaPlayer1.getVolume()<200)
								  mediaPlayer1.setVolume(mediaPlayer1.getVolume() -notches);
							      
							}else{
								
								mediaPlayer1.setVolume(mediaPlayer1.getVolume() -notches);
							      mediaPlayer1.setMarqueeText(String.valueOf(mediaPlayer1.getVolume()));
							      mediaPlayer1.setMarqueeTimeout(3000);
							}
							
						}
		    			  
		    		  }
		    		  );
			 
			     frame.addMouseMotionListener(
			    		new MouseMotionAdapter(){

						
							@Override
							public void mouseMoved(MouseEvent e) {
                   
							
							          if (!timer.isRunning()) { 
							        	  //startTimer();
							             // timer.start();
							          }
							
								
							}
						  }
								
			);
			
		  frame.setDropTarget(new DropTarget(){
 	    	public synchronized void drop(DropTargetDropEvent evt) {
 	            try {
 	                evt.acceptDrop(DnDConstants.ACTION_COPY);
 	                List<File> droppedFiles = (List<File>)
 	                    evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
 	                for (File file : droppedFiles) {
 	                	   System.out.println("File path is '" + file.getAbsolutePath() + "'.");
 	                	
	  		  			 	 mediaPlayer1.playMedia(file.getAbsolutePath());
	  		  			 		
 	               	      StringBuilder sb = new StringBuilder();
					       sb.append(file.getName().toUpperCase().trim());
					       sb.append("     STREAMING - ");
					       String parent = new File(file.getAbsolutePath().trim()).getParentFile().getAbsolutePath();
					       sb.append(parent.toUpperCase().trim());
					       String text = sb.toString();
					      
						 // statusBar.setText(text);
 	                }
 	            } catch (Exception ex) {
 	                ex.printStackTrace();
 	            }
 	        }
 	    });
	}
	
	
		
			public void framework(){	 
		        
		  		  NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),("C://lib"));  
		  		  Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);		
									 
			}
			 public static void setMediaFilter(FilenameFilter mediaFilter){
				  SinglePlayer.mediaFilter = mediaFilter;
			  }
			  
			  public static FilenameFilter getMediaFilter(){
				  return mediaFilter;
			  }
			 
			public void player(){
				
				 
				 mediaPlayerFactory1 = new MediaPlayerFactory();


		         mediaPlayer1 = mediaPlayerFactory1.newEmbeddedMediaPlayer();
		        
		        mediaPlayer1.setVideoSurface(mediaPlayerFactory1.newVideoSurface(frame));
		            
		        mediaPlayer1.setEnableMouseInputHandling(false);
		        mediaPlayer1.setEnableKeyInputHandling(false);

	     
		        mediaListPlayer1 = mediaPlayerFactory1.newMediaListPlayer();
		        mediaList1 = mediaPlayerFactory1.newMediaList();

		      
		        mediaListPlayer1.setMediaPlayer(mediaPlayer1);
		        mediaListPlayer1.setMediaList(mediaList1);
		        mediaListPlayer1.setMode(MediaListPlayerMode.LOOP);
		        
		        String[] standardMediaOptions = {"video-filter=logo", "logo-file=xmp.png", "logo-opacity=25"};
			    mediaPlayer1.setStandardMediaOptions(standardMediaOptions);
			   

			     mediaPlayer1.setLogoFile("/res/xmp.png");
			     mediaPlayer1.setVolume(200);
			     mediaPlayer1.setLogoOpacity(25);
			     mediaPlayer1.setLogoLocation(10, 10);
			     mediaPlayer1.enableLogo(true);
			     

		         mediaPath = "E:\\Music\\David Guetta and Showtek - Bad ft. Vassy (Official Audio).mp4";
			 //  mediaPlayer1.playMedia(mediaPath,standardMediaOptions);  	//"E:\\Music\\Tonight alive -The edge.VOB"
			     mediaPlayer1.setVolume(200);
			     

			        String media = "/sdcard/";
			        String options = formatRtpStream("192.168.43.1", 5555);

			        //System.out.println("Streaming '" + media + "' to '" + options + "'");
			     mediaPlayer1.playMedia(mediaPath  );


				//vol.setText("200");
			
			     }
			@Override
		    public void init() {
		        fxContainer = new JFXPanel();
		        cr.registerComponent(fxContainer);
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
					root = FXMLLoader.load(getClass().getResource("/res/controls.fxml"));
				
                Scene scene = new Scene(root);
		       
		        fxContainer.setScene(scene);


		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

			     private static String formatRtpStream(String serverAddress, int serverPort) {
			         StringBuilder sb = new StringBuilder(60);
			         sb.append(":sout=#rtp{dst=");
			         sb.append(serverAddress);
			         sb.append(",port=");
			         sb.append(serverPort);
			         sb.append(",mux=ts}");
			         return sb.toString();
			     }



	
			
			/****************************RECEIVING ANDROID COMMANDS************************************/
			 
			   public void androidCommandServer(){
				   try{
					  server1 = new ServerSocket(6767,3); 
					  server2 = new ServerSocket(6868,3);
					  
					  showCommand("<Receiving Android  Command>>> Activated");
					
						  try{  
							 
							  setUpAndroidCommandStreams(); 
							  receiveAndroidCommands();
							  showCommand("/n Server <ACCEPTING Android  Command>>> !");

						  }catch(EOFException eofException){
							  showCommand("/n Server <Receiving Android  Command>>> ended connection !\n"+eofException.getMessage());
						  }finally{
							// outputStream1.close();
							// inputStream1.close();
							// server1.close();
						  }
					  
				   }catch(IOException ioE){
					   ioE.printStackTrace();
				   }
				   
			   } 
			   
			
			   public void setUpAndroidCommandStreams() throws IOException{ 
				 
				   Socket connection2 = server2.accept();
				   ObjectOutputStream  outputStream2 = new ObjectOutputStream(connection2.getOutputStream()); 	
				   	   
				   String pcName = InetAddress.getLocalHost().getHostName().toString();
				   outputStream2.writeObject(pcName.toUpperCase());
				   outputStream2.flush();
				   
				    connection1 = server1.accept();
				   outputStream1 = new ObjectOutputStream(connection1.getOutputStream()); 	
				   inputStream1 = new ObjectInputStream(connection1.getInputStream());
				
				   setupComplete = true;
				  
				   showCommand("\n Streams are now setup \n ");		  		   
				   
			   }
			   
		   /**This method is for recieving android commands to the player controls ***/
			   
			   public void receiveAndroidCommands() throws IOException{
				 		 		   
				   do{
					   try{
						   androidcommand = (String)inputStream1.readObject();
						   switch(androidcommand){
						      case "next":
						    	  mediaListPlayer1.playNext();
							    break;
						      case "prev":
						    	  mediaListPlayer1.playPrevious();
								 break;
						      case "stop":
						    	  mediaPlayer1.stop();
									 break;	 
						      case "play":
						    	  mediaPlayer1.pause();
									 break;
									 
						      case "fullscreen":
						    	  if(mediaPlayer1.isFullScreen()){
							    	  mediaPlayer1.setFullScreen(false);

						    	  }else if(!mediaPlayer1.isFullScreen()){
							    	  mediaPlayer1.setFullScreen(true);

						    	  }
								 break;
								 
							  case "forward":
								  mediaPlayer1.skip(15000);
									 break;
							  case "backward":
								  mediaPlayer1.skip(-15000);
								break;
								default:
									mediaPlayer1.setPosition(Float.parseFloat(androidcommand)/1000.0f);
									break;
								
						   }
						
						   showCommand("\n"+androidcommand);
					   }catch(ClassNotFoundException cnf){cnf.printStackTrace();}
						 
					   
				   }while(!androidcommand.equals("END"));
				   
			   }
			   private void showCommand(final String text){	 
				   System.out.println(text);									  
			   }
			public void startTimer(){
				
					panel.add(controls,BorderLayout.SOUTH);
				   panel.revalidate();
			
			}
			public void stopTimer(){
				if(mediaPlayer1.isFullScreen()){
				panel.remove(controls);
			    panel.revalidate();
				}
			
			}
			 private void updatePosition(int value) {
		 		 positionSlider.setValue(value);
		 			 		  
		 	   }
			 private void setSliderBasedPosition() {
			 	   if(!mediaPlayer1.isSeekable()) {
			 	         return;
			 	   }

				 	   float positionValue = positionSlider.getValue() / 1000.0f;
					 	   if(positionValue > 0.99f) {
					 	   positionValue = 0.99f;
					 	   }
					 	
					 	  
				 	  mediaPlayer1.setPosition(positionValue);
			 	 
				 }
			   private void updateUIState() {
			
				  
					long time = mediaPlayer1.getTime();
					int position = (int)(mediaPlayer1.getPosition() * 1000.0f);

		      		updateTime(time);
					updatePosition(position);
			 	  
			 	   }
			  private void updateTime(long millis) {
			 	  
	 		        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
	 		        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	 		        long hours = TimeUnit.MILLISECONDS.toHours(millis);
	 		        if(minutes < 60){
	 		       String s = String.format("%02d:%02d",((minutes >=0 && minutes <60) ? minutes :minutes%60),((seconds >=0 && seconds<60) ? seconds:seconds%60));
	 		    //   timeLabel.setText(s);	
	 		     //  timeLabel.setBounds(50, 910,  50,  20);
	 		        }else{
	 		        	 String s = String.format("%02d:%02d:%02d", hours,((minutes >=0 && minutes <60) ? minutes :minutes%60),((seconds >=0 && seconds<60) ? seconds:seconds%60));
	 		        	 // timeLabel.setText(s);
	 		        	//timeLabel.setBounds(50, 910,  75,  20);
	 		        }
    
	 	   }
			  private final class UpdateRunnable implements Runnable {

				  private final EmbeddedMediaPlayer mediaPlayer;

			 	 private UpdateRunnable(EmbeddedMediaPlayer mediaPlayer1) {
			 	   this.mediaPlayer = mediaPlayer1;
			 	   }

			 	  public void run() {
				 	   final long time = mediaPlayer.getTime();
				 	   final int position = (int)(mediaPlayer.getPosition()*1000.0f);
				 	  
				 	   // Updates to user interface components must be executed // Dispatch Thread
				 	   SwingUtilities.invokeLater(new Runnable() {
				 	   @Override
				 	   public void run() {
				 		  if(mediaPlayer.isPlaying()) {
				 			  try {
				 				  if(position>0 && setupComplete==true){
				 					  System.out.println(position);
								outputStream1.writeObject(position);
								 outputStream1.flush();
				 				  }
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				 			 updateTime(time);
				 			updatePosition(position);
				 			
				 		  }
				 	   }
				 	   });
			 	   }
			 	   }
			  @Override
			 	 public void windowActivated(WindowEvent e) {
			 	 	// TODO Auto-generated method stub
			 	 	  eventStatus = true;
			 	 }


			 	 @Override
			 	 public void windowClosed(WindowEvent e) {
			 	 	// TODO Auto-generated method stub

			 	 }


			 	 @Override
			 	 public void windowClosing(WindowEvent e) {

			 	 }


			 	 @Override
			 	 public void windowDeactivated(WindowEvent e) {
			 	 	// TODO Auto-generated method stub
			 	 	eventStatus = false;
			 	 }


			 	 @Override
			 	 public void windowDeiconified(WindowEvent e) {
			 	 	  eventStatus = true;

			 	 }


			 	 @Override
			 	 public void windowIconified(WindowEvent e ) {
			 	 	eventStatus = false;

			 	 }


			 	 @Override
			 	 public void windowOpened(WindowEvent e) {
			 	 	try {
			 	 		GlobalScreen.registerNativeHook();
			 	 	} catch (NativeHookException e1) {
			 	 		// TODO Auto-generated catch block
			 	 		e1.printStackTrace();
			 	 	}
			 	 	GlobalScreen.addNativeKeyListener(new SinglePlayer());

			 	 }




				@Override
				public void nativeKeyPressed(NativeKeyEvent event) {

				switch(event.getKeyCode() ){

				case NativeKeyEvent.VC_SHIFT_L:
				isShiftPressed = true;
				break;
				case NativeKeyEvent.VC_SHIFT_R:
				isShiftPressed = true;
				break;
				}


				if(event.getKeyCode() ==NativeKeyEvent.VC_SPACE){
					if(mediaPlayer1.isPlaying()){
						 mediaPlayer1.pause();
					}else{
						mediaPlayer1.play();
					}

				} else if( event.getKeyCode() == NativeKeyEvent.VC_LEFT ){
					  mediaPlayer1.skip(-15000);


				}else if( (event.getKeyCode() == NativeKeyEvent.VC_RIGHT)){
					  mediaPlayer1.skip(15000);



				}else if(( event.getKeyCode() == NativeKeyEvent.VC_F)){
					  if(mediaPlayer1.isFullScreen()){
				    	  mediaPlayer1.setFullScreen(false);

					  }else if(!mediaPlayer1.isFullScreen()){
				    	  mediaPlayer1.setFullScreen(true);

					  }


				}else if(( event.getKeyCode() == NativeKeyEvent.VC_P)){
					  mediaListPlayer1.playPrevious();


				}else if(( event.getKeyCode() == NativeKeyEvent.VC_S)){
					  mediaPlayer1.stop();
				}else if(( event.getKeyCode() == NativeKeyEvent.VC_N)){
					 mediaListPlayer1.playNext();

				}


				}


				@Override
				public void nativeKeyReleased(NativeKeyEvent event) {
				switch(event.getKeyCode() ){

				case NativeKeyEvent.VC_SHIFT_L:
				isShiftPressed = false;
				break;
				case NativeKeyEvent.VC_SHIFT_R:
				isShiftPressed = false;
				break;
				}

				}


				@Override
				public void nativeKeyTyped(NativeKeyEvent arg0) {
				// TODO Auto-generated method stub

				}

				
				
				}
