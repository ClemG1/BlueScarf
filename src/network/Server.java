package network;

import java.net.*;

public class Server extends Thread {
	
	//Attributes
	private ServerSocket serverSocket;

	/**
	  * @brief : class constructor
	  * @param : the port for the server socket
	  * @returns : none
	 **/
	public Server(int portServer) {
		try {
			this.serverSocket = new ServerSocket(portServer);
			System.out.println("server up");
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
				
				Socket socket = this.serverSocket.accept();;
				
				System.out.println("Connection established");
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
