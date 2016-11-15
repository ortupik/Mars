package share;
/*
 * This File is part of MediaShare
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Share {
	
	private ServerSocket socketServer;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private ExecutorService executor;
	private FileInputStream	fis ;
	private ObjectOutputStream oos;
	private Thread onStartThread = null ;

	private  ServerSocket socketServer2;
	private  Socket socket2;

	private static String fileSizeValue;
	
	private static String path;
	private  Thread detailsThread;




	public void onStart(String mediapath) {


		 detailsThread = new Thread(new Runnable() {
			public void run() {
				try {

					 socketServer2 = new ServerSocket(7878,1);

					System.out.println("  Details Thread started..");

                   while(true){
						 socket2 = socketServer2.accept();
						System.out.println("Details Thread Accepted....");


						String [] details  = new String [6];
						File file = new File(mediapath);
						if (file.exists() && file.isFile()) {
							String filename = file.getName();
							double size = ((double) file.length() / 1000000);
							String fileSize = String.valueOf(size + " MB");
							String host = socket2.getInetAddress().getHostName();
							String ip = socket2.getInetAddress().getHostAddress();


							details[0] = filename;
							details[1] = fileSize;
							details[2] = host;
							details[3] = ip;
							details[4] = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3, file.getAbsolutePath().length());
							details[5]=mediapath;

							ObjectOutputStream oos = new ObjectOutputStream(socket2.getOutputStream());
							oos.writeObject(details);
						}

						}



				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						socketServer2.close();
						socket2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
		});
		detailsThread.start();

	}
	public void afterStart(){
		onStartThread = new Thread(new Runnable(){
			public void run(){
				try {
					path = new PcReceiveController().getPath();
				 socketServer = new ServerSocket(8000);
            		System.out.println(" Android onStart Started...");
					
					while(true){
					    socket = socketServer.accept();
						System.out.println("Android onStart Accepted....");
						
						ArrayList<String> files = new ArrayList<String> ();
						files.add(path);
						//files.add("E:\\Clarity.VOB");
						//files.add("E:\\Carly.mp4");
						//files.add("E:\\collabo.mp4");
						//files.add("E:\\madworld.mp3");
						onAcceptStream(files);
	                }
					
				} catch (IOException e) {e.printStackTrace();
				}finally{
					try {
						socketServer2.close();
						socket2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
			}
		});
		onStartThread.start();
	  
	}
	public void onAcceptStream(ArrayList<String> filesToSend){
		int no = filesToSend.size();
		executor = Executors.newFixedThreadPool(no);
		for(String path : filesToSend){
			executor.submit(new SendFile(path));
		}	
		executor.shutdown();
		System.out.println("All Files submitted");
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class SendFile implements Runnable{
	  private String path;
		public SendFile(String path){
			this.path = path;
		}

		@Override
		public void run() {
           
			
			try {
				 fis = new FileInputStream(path);	
			     oos = new ObjectOutputStream(socket.getOutputStream()); 

				setFileSizeValue(path);
                byte [] buffer = new byte [fis.available()];
			    fis.read(buffer);
			    System.out.println("reading "+path);
			    oos.writeObject(buffer);
			    System.out.println("writing"+path);
			    oos.flush();
			    System.out.println(fis.getChannel().position());

			} catch (IOException e) {
				e.printStackTrace();
			}  finally {

			}
			
		}
	}

	public void onExit(){
		

						
	}
	public static String getFileSizeValue() {
		return fileSizeValue;
	}

	public static void setFileSizeValue(String fileSizeValue) {
		Share.fileSizeValue = fileSizeValue;
	}
	


}
