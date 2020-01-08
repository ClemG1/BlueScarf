package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import localSystem.LocalFilesManager;

public class ServerThread extends Thread{
	
	//Attributes
	private Socket socket;
	
	/**
	  * @brief : class constructor
	  * @param : socket to communicate with
	  * @returns : none
	 **/
	public ServerThread (Socket socket) {
		this.socket = socket;
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		try {
			
			//connection established state
			BufferedReader bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = "";
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			while ((msg = bufferIn.readLine()) != null) { //until the client end the connection
				System.out.println(msg);
				if(msg.subSequence(0, 3).toString().equals("-c:")) {
					System.out.println("Contact received.");
					contact.write(msg.substring(3,msg.length()), '-');
					Client client = new Client(this.socket.getInetAddress(),"-r");
					client.start();
				}
				if(msg.subSequence(0, 3).toString().equals("-r:")) {
					System.out.println("response received.");
					contact.deleteFile();
					contact.write(msg.substring(3, msg.length()), '-');
				}
			}
			
			//connection closed state
			bufferIn.close();
			this.socket.close();
			System.out.println("Connection closed");
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
