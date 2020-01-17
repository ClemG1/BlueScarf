package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

import localSystem.LocalFilesManager;
import localSystem.User;

public class Client extends Thread {

	//Attributes
	private Socket socket;
	private String messageType;
	
	/**
	  * @brief : class constructor
	  * @param : ip to connect to, port to connect to 
	  * @returns : none
	 **/
	public Client(InetAddress ipAddress, String messageType) {
		try {
			this.socket = new Socket(ipAddress,NetworkManager.portServer);
			this.messageType = messageType;
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
				bufferOut.write("-m:" + msg);
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
	
	private void sendConnectionMsg () {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			
			//add our ip to contact.txt file matching our user name
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			contact.write(User.localUserName + ":" + NetworkManager.localIpAddress.toString(), '-');
			
			String myContact = "-c:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			System.out.println("contact to send : " + myContact);
			bufferOut.write(myContact);
			bufferOut.newLine();
			bufferOut.flush();
			System.out.println("Contact.txt send.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void responseMessage() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			String myContacts = "-u:" + contact.readAllFile();
			bufferOut.write(myContacts);
			bufferOut.newLine();
			bufferOut.flush();
			bufferOut.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void updateUserMessage() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			bufferOut.write("-u:" + ServerThread.newUserData);
			bufferOut.newLine();
			bufferOut.flush();
			bufferOut.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void deconnectionMessage() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			String deconnectionMessage = "-d:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			bufferOut.write(deconnectionMessage);
			bufferOut.newLine();
			bufferOut.flush();
			bufferOut.close();
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
			switch (this.messageType) {
			case "-c:" :
				sendConnectionMsg();
				socket.close();
				break;
			case "-d:" : 
				deconnectionMessage();
				socket.close();
				break;
			case "-m:" :
				send ();
				break;
			case "-u:" :
				updateUserMessage();
				socket.close();
				break;
			case "-r:" :
				responseMessage();
				socket.close();
				break;
			default :
				System.out.println("Message type unkown.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
