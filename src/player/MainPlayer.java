package player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.omg.CORBA.Current;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;
import components.*;
import components.MediaShare.UpdateRunnable;



				
	public class MainPlayer extends MediaShare{
			
			
			//MEDIA PLAYER COMPONENTS
				 protected static EmbeddedMediaPlayer  mediaPlayer1;
				 private static Integer cuurentMediaPlayer = 1;
				 protected  EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent1;
				 private static MediaList mediaList1;
			      private   MediaPlayerFactory mediaPlayerFactory1;
				 protected static MediaListPlayer mediaListPlayer1;
				 
				 protected static EmbeddedMediaPlayer  mediaPlayer2;
				 protected  EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent2;
				 private static MediaList mediaList2;
			      private   MediaPlayerFactory mediaPlayerFactory2;
				 protected static MediaListPlayer mediaListPlayer2;
				 
				 protected static EmbeddedMediaPlayer  mediaPlayer3;
				 protected  EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent3;
				 private static MediaList mediaList3;
			      private   MediaPlayerFactory mediaPlayerFactory3;
				 protected static MediaListPlayer mediaListPlayer3;
				 
				 protected static EmbeddedMediaPlayer  mediaPlayer4;
				 protected  EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent4;
				 private static MediaList mediaList4;
			      private   MediaPlayerFactory mediaPlayerFactory4;
				 protected static MediaListPlayer mediaListPlayer4;
				 
				 private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

				
				public static Integer getCurrentMediaPlayer(){
				   return cuurentMediaPlayer;				   
					
				}
				public static Integer getCuurentMediaPlayer() {
					return cuurentMediaPlayer;
				}
				public static void setCuurentMediaPlayer(Integer cuurentMediaPlayer) {
					MainPlayer.cuurentMediaPlayer = cuurentMediaPlayer;
				}
				public EmbeddedMediaPlayer getM (){
				
					if(cuurentMediaPlayer== 1){
						System.out.println("setting media player 1");
				       return mediaPlayer1;
					}else if(cuurentMediaPlayer== 2){
						System.out.println("setting media player 2");
						return mediaPlayer2;
					}else if(cuurentMediaPlayer== 3){
						System.out.println("setting media player 3");
						return mediaPlayer3;
					}else if (cuurentMediaPlayer== 4){
						System.out.println("setting media player 4");
						return mediaPlayer4;
					}else{
						System.out.println("setting media player else");
						return mediaPlayer1;
					}
					
					
				}
				 public void player(int media1){
					 cuurentMediaPlayer = 1;
					    embeddedMediaPlayerComponent1 = new EmbeddedMediaPlayerComponent();		       
					    mediaPlayerFactory1 =  embeddedMediaPlayerComponent1.getMediaPlayerFactory();
					    mediaPlayer1 = mediaPlayerFactory1.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(new MediaShare().mainFrame));
					   mediaPlayer1.setVideoSurface(mediaPlayerFactory1.newVideoSurface(frame1));
					  
					  // embeddedMediaPlayerComponent1.setCursorEnabled(false);			        
					   mediaPlayer1.setEnableMouseInputHandling(false);
					     mediaPlayer1.setEnableKeyInputHandling(false);
					
					//  new    VideoMouseMovementDetector(frame1, 10000, embeddedMediaPlayerComponent1);
					
					mediaListPlayer1 = mediaPlayerFactory1.newMediaListPlayer();
					mediaList1 = mediaPlayerFactory1.newMediaList();
					
					
					mediaListPlayer1.setMediaPlayer(mediaPlayer1);
					mediaListPlayer1.setMediaList(mediaList1);
					mediaListPlayer1.setMode(MediaListPlayerMode.LOOP);

					mediaPath = "D:\\Music\\Celine Dion - It's All Coming Back To Me Now - YouTube.mp4";
					 mediaPlayer1.setVolume(100);
						//mediaPlayer1.playMedia("rtsp://192.168.173.1:8050/1");
					//  options = formatStreamOptions("192.168.43.252", "8050", "1");
					 mediaPlayer1.playMedia(mediaPath);//"rtsp://192.168.43.252:8554/voice"
					 isPlayerEstablished = true;

					//	mainFrame.setSize(	mediaPlayer1.getVideoSurfaceContents().getWidth(),mediaPlayer1.getVideoSurfaceContents().getHeight()-80);
					// mediaListPlayer1.playItem(0);
						
					 executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer1), 0L, 1L, TimeUnit.SECONDS);
					
						
						Timer playlistTimer = new Timer(3000, new ActionListener() {
					        public void actionPerformed(ActionEvent ae) {
					        	initializePlaylist(1);
					       
					        }
					      });
						playlistTimer.setRepeats(false);
						playlistTimer.start();
							
						//	getDisplayMediatitle().setText(.getDuration()+" giu");
					
							//vol.setText("200");
					
						mediaListPlayer1.addMediaListPlayerEventListener(
						    		new MediaListPlayerEventAdapter(){
						    			public void nextItem(MediaListPlayer mlp,libvlc_media_t item, String itemM) {
						    				String rawName = itemM.replaceAll("%20", " ").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%27", "'").replaceAll("%26", "&").replaceAll("/", "\\\\").replaceAll("%5B", "[").replaceAll("%5D", "]").replaceAll("%3F", "강");
						    				final String finalString = rawName.substring(8, rawName.length());
						    				System.out.println(finalString);
						    				  final String work = System.getProperty("user.dir");
						 	              int unwantedString = work.length() +9;
											final String finalStringDisplay = rawName.substring(unwantedString, rawName.length());
											SwingUtilities.invokeLater(
													new Runnable(){
														public void run(){
														//	mediaPathName = finalStringDisplay;
															getDisplayMediatitle().setText(finalStringDisplay);
															setTitle(finalStringDisplay);
															  StringBuilder sb = new StringBuilder();
														       sb.append( finalStringDisplay.toUpperCase().trim());
														       sb.append("     STREAMING - ");
														       String parent = new File(mediaPath).getParentFile().getAbsolutePath();
														       sb.append(parent.toUpperCase().trim());
														       String text = sb.toString();
														      
														     //  setupTrayIcon(mediaPathName);
											
														}
													}
						    		    	);
						    			}
						    			});

					
					// new MainPlayer().player3();
					// new MainPlayer().player4();
					 /*****************************/
					}

		public void player2(){
				 
				 embeddedMediaPlayerComponent2 = new EmbeddedMediaPlayerComponent();		       
				    mediaPlayerFactory2 =  embeddedMediaPlayerComponent2.getMediaPlayerFactory();				 
				    mediaPlayer2 = mediaPlayerFactory2.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(MediaShare.mainFrame));
				   mediaPlayer2.setVideoSurface(mediaPlayerFactory2.newVideoSurface(frame2));
				
				
				  // embeddedMediaPlayerComponent1.setCursorEnabled(false);			        
				   mediaPlayer2.setEnableMouseInputHandling(false);
				     mediaPlayer2.setEnableKeyInputHandling(false);
				
			
				
				mediaListPlayer2 = mediaPlayerFactory2.newMediaListPlayer();
				mediaList2 = mediaPlayerFactory2.newMediaList();
				
				//mediaList2 = MediaShare.mediaList2;
				mediaListPlayer2.setMediaPlayer(mediaPlayer2);
				mediaListPlayer2.setMediaList(mediaList2);
				mediaListPlayer2.setMode(MediaListPlayerMode.LOOP);
				
				// mediaPlayer2.playMedia("rtp://127.0.0.1:8050/1");
				//mediaPath = "E:\\Music\\Vance Joy - 'Riptide' Official Video.mp4";
				mediaPlayer2.setVolume(20);
				// mediaPlayer2.setPosition(MediaShare.mediaPlayer1.getPosition());
				
				 mediaPlayer2.playMedia(mediaPath );
				 initializePlaylist(2);
					}
			
		public void player3(){
				
				 embeddedMediaPlayerComponent3 = new EmbeddedMediaPlayerComponent();		       
				    mediaPlayerFactory3=  embeddedMediaPlayerComponent3.getMediaPlayerFactory();				 
				    mediaPlayer3 = mediaPlayerFactory3.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(MediaShare.mainFrame));
				   mediaPlayer3.setVideoSurface(mediaPlayerFactory3.newVideoSurface(frame3));
				
				   
				
				  // embeddedMediaPlayerComponent1.setCursorEnabled(false);			        
				   mediaPlayer3.setEnableMouseInputHandling(false);
				     mediaPlayer3.setEnableKeyInputHandling(false);
				
				
				
				mediaListPlayer3 = mediaPlayerFactory3.newMediaListPlayer();
				mediaList3 = mediaPlayerFactory3.newMediaList();
				
				
				mediaListPlayer3.setMediaPlayer(mediaPlayer3);
				mediaListPlayer3.setMediaList(mediaList3);
				mediaListPlayer3.setMode(MediaListPlayerMode.LOOP);
				
				 mediaPlayer2.playMedia(mediaPath );
				 mediaPlayer3.setVolume(20);
				
				 mediaPlayer3.playMedia(mediaPath);
				 initializePlaylist(3);

				 
				 /***************************************/
				 
					
		}
		public void player4(){
				 embeddedMediaPlayerComponent4 = new EmbeddedMediaPlayerComponent();		       
				    mediaPlayerFactory4=  embeddedMediaPlayerComponent4.getMediaPlayerFactory();				 
				    mediaPlayer4 = mediaPlayerFactory4.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(MediaShare.mainFrame));
				   mediaPlayer4.setVideoSurface(mediaPlayerFactory4.newVideoSurface(frame4));
				
				   
				
				  // embeddedMediaPlayerComponent1.setCursorEnabled(false);			        
				   mediaPlayer4.setEnableMouseInputHandling(false);
				     mediaPlayer4.setEnableKeyInputHandling(false);
				
				
				mediaListPlayer4 = mediaPlayerFactory4.newMediaListPlayer();
				mediaList4 = mediaPlayerFactory4.newMediaList();
				
				
				mediaListPlayer4.setMediaPlayer(mediaPlayer4);
				mediaListPlayer4.setMediaList(mediaList4);
				mediaListPlayer4.setMode(MediaListPlayerMode.LOOP);
				
				 mediaPlayer2.playMedia(mediaPath );
				 mediaPlayer4.setVolume(20);
				
				 mediaPlayer4.playMedia(mediaPath );
				 initializePlaylist(4);

				 
				 /**************************************
				
				String[] standardMediaOptions = {"video-filter=logo", "logo-file=xmp.png", "logo-opacity=25"};
				mediaPlayer1.setStandardMediaOptions(standardMediaOptions);
				
				
				 mediaPlayer1.setLogoFile("/res/xmp.png");
				 mediaPlayer1.setVolume(200);
				 mediaPlayer1.setLogoOpacity(25);
				 mediaPlayer1.setLogoLocation(10, 10);
				 mediaPlayer1.enableLogo(true);
				
				 ***************************************/
				
				
				
					}
		}
				
