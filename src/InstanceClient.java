import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class InstanceClient {

	public static void main(String[] args) {
	      
      startClient();
	}
	public static  void startClient(){
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(),4500);
			if(socket.isConnected()){
				System.out.println("It is connected");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
