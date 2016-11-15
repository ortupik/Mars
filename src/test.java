import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class test {
	
	public static void main(String args[]) {


        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        //JOptionPane.showMessageDialog(null,"Screen resolution is "+screenRes);
        JOptionPane.showMessageDialog(null, getCurrentPath());

	}
        public static String  getCurrentPath(){
    		java.io.File file = new java.io.File("lib");
    		String path = file.getAbsolutePath();
    		String only_path  = path.substring(0,path.lastIndexOf('\\'));
    		System.out.println("complete absolute path is "+path);
    		System.out.println("absolute path is "+only_path);
    		return path;
    	}


public void actionPerformed(ActionEvent event){

	Platform.runLater(new Runnable(){
		public void run(){
			 try {
				 Stage primaryStage = new Stage();
				  Parent root = FXMLLoader.load(getClass().getResource("Ledg.fxml"));
				  Scene scene = new Scene(root, 405, 532 );
				  primaryStage.setScene(scene);
				  primaryStage.setTitle("Walgotech ...");
				  primaryStage.show();
			 } catch (IOException e1) {
				  e1.printStackTrace();
			 }
		
		 }
		});
    }
}






		
	/*//	ArrayList<String> ip
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)){
        	
        	if(netint.getName().equals("wlan0")){
        		System.out.printf("Name: %s\n", netint.getName());
        		  Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        	        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	        	
        	        	 System.out.printf("InetAddress: %s\n", inetAddress);
        	        }
        	}
        }
    }

    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	
        	 System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");*/
     

