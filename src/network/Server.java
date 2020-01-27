package network;

import java.net.*;

import localSystem.LocalFilesManager;

public class Server extends Thread {
	
	//Attributes
	private ServerSocket serverSocket;

	/**
	  * @brief : class constructor
	  * @param : the port for the server socket
	  * @returns : none
	 **/
	public Server() {
		try {
			this.serverSocket = new ServerSocket(NetworkManager.portServer);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		
		try {
			while(true) {
				
				Socket socket = this.serverSocket.accept();
				
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();

			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
	
}
