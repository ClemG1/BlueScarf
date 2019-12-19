package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread {

	//Attributes
	private Socket socket;
	
	/**
	  * @brief : class constructor
	  * @param : ip to connect to, port to connect to 
	  * @returns : none
	 **/
	public Client(InetAddress ipAddress, int portServer) {
		try {
			this.socket = new Socket(ipAddress,portServer);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/*
	 * @brief : send a message written by the user by the socket
	 * @param : none
	 * @return : none
	 * @note : print the message which has been send
	 */
	private void send() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter your message : ");
			String msg = scanner.next();
			while (msg.compareToIgnoreCase("exit") != 0) {
				bufferOut.write(msg);
				bufferOut.newLine();
				bufferOut.flush();
				System.out.println("Send : " + msg);
				System.out.println("Please enter your message : ");
				msg = scanner.next();
			}
			scanner.close();
			bufferOut.close();
		} 
		catch (IOException ioe) {
			System.out.println("Client : " + ioe);
		}
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		
	}
	
}
