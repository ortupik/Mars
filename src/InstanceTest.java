import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.*;
public class InstanceTest extends JFrame{
	private static InstanceTest it;
	
	public static void main(String[] args) {
		 it = new InstanceTest();
		 it.setAlwaysOnTop(true);
		 it.setSize(500, 450);
		
		startServer();
	}
	public static void startServer() {
		  ServerSocket server;
		try {
			server = new ServerSocket(7078);
			System.out.println("Server started ...");
			it.setVisible(true);
			while(true){
				server.accept();
				System.out.println("Server acceped ..."); 
				
			} 
		  	
		} catch (IOException e) {
			System.out.println("Already started");
			System.exit(0);
		
		}
		 
	}

}
