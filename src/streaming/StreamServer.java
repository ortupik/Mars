package streaming;

import com.sun.jna.NativeLibrary;

import components.MediaShare;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chris on 1/10/2016.
 */
public class StreamServer extends MediaShare{
    private static  float pos;



    private static Boolean isRunning = false;
    private static String mediaPath;
    
	public String  getCurrentPath(){
		java.io.File file = new java.io.File("lib");
		String path = file.getAbsolutePath();
		String only_path  = path.substring(0,path.lastIndexOf('\\'));
		System.out.println("complete absolute path is "+path);
		System.out.println("absolute path is "+only_path);
		return path;
	}

    public void receiveDetails( String ipaddress, String port, String screen){
        mediaPath = new MediaShare().getCurrentMediaPath();
       new MediaShare().addStreamcapabilities(ipaddress);


        if(isRunning==false) {
            Thread streamPlayer = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRunning = true;

  		  		  NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"C:\\lib");  


                    String options = formatStreamOptions(ipaddress, port, screen);
                    System.out.println("streaming " + mediaPath + " to " + options);

                    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory("");
                    HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
                    mediaPlayer.playMedia(mediaPath, options, ":no-sout-rtp-sap,:no-sout-standard-sap,:sout:all,:sout-keep");
                    Timer playlistTimer = new Timer(3000, new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            pos = mediaPlayerX.getPosition();
                            mediaPlayer.setPosition(pos);
                            mediaPlayer.skip(2000);
                            if(!mediaPlayer.isPlaying()){
                                isRunning = false;
                            }
                        }
                    });
                    playlistTimer.setRepeats(false);
                    playlistTimer.start();




                }
            });
         //  streamPlayer.start();
        }else{
        }
    }

    private static String formatStreamOptions(String serverAddress, String serverPort,String id) {
        StringBuilder sb = new StringBuilder(60);
       sb.append(":sout=#rtp{sdp=rtsp://@");
       sb.append(serverAddress);
       sb.append(":");
       sb.append(serverPort);
       sb.append("/");
       sb.append(id);
       sb.append("}");
        return sb.toString();
    }

}
