package components;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Formatter;

import javax.swing.JFrame;
import javax.swing.UIManager;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

import effects.video.VideoAdjustPanel;


public class alpha extends JFrame{

	private JFrame v =  new JFrame ();
	private static EmbeddedMediaPlayerComponent ourMediaPlayer ;
	String mediaPath = "D:\\Music\\Alesso - Cool ft. Roy English.mp4";
	 
	
	
	
	alpha (){
		
		String	vlcPath = "lib";
        File f = new File(vlcPath);
        System.out.println(f.getAbsolutePath());
        
		//this.mediaPath = mediaURL;
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"C:\\lib");

		
		ourMediaPlayer = new EmbeddedMediaPlayerComponent () ;
		//System.setProperty("jna.library.path", "D://java workspaces//workspace1//workspace//folder//src//folder//lib");
		
   	    //"D://java workspaces//workspace1//workspace//folder//src//folder//lib"
		
	      
	        
	}
	
	public void run(){
		 String options = formatStreamOptions("192.168.173.252","8554","");
	//	ourMediaPlayer.getMediaPlayer().playMedia(mediaPath,options,":no-sout-rtp-sap,:no-sout-standard-sap,:sout:all,:sout-keep");
	
			try {
				ourMediaPlayer.getMediaPlayer().playMedia("rtsp://"+InetAddress.getLocalHost().getHostAddress().trim()+":8050/1");
				System.out.println(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		ourMediaPlayer.getMediaPlayer().setFullScreen(true);
		//ourMediaPlayer.getMediaPlayer().setPosition(0.5f);
	//	ourMediaPlayer.setCursorEnabled(false);
	
    	
	
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
	public static void main(String[] args)  {

		alpha v =  new alpha ();
		v.setContentPane(ourMediaPlayer);
		 v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 v.setTitle("CHRIS X Universal Player ");
		 v.setSize(1200, 800);
		 v.setLocation(100, 100);
		 v.setVisible(true); 
		
		 v.run ();
		 
		 System.out.println(System.getProperty("user.home"));
		 
	}
}
