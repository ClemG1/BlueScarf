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
	public static boolean newMessage = false;
	
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
	private void initConversation() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			
			String call = "-m:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			
			bufferOut.write(call);
			bufferOut.newLine();
			bufferOut.flush();
			
			bufferOut.close();
		} 
		catch (IOException ioe) {
			System.out.println("Client : " + ioe);
		}
	}
	
	private void conversation() {
		//sending protocol
		if(newMessage) { //update by the watchdog
			
		}
	}
	
	private void sendConnectionMsg () {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			
			String myContact = "-c:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			bufferOut.write(myContact);
			bufferOut.newLine();
			bufferOut.flush();
			
			bufferOut.close();
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
				initConversation ();
				conversation();
				break;
			case "-u:" :
				updateUserMessage();
				socket.close();
				break;
			case "-r:" :
				responseMessage();
				socket.close();
				break;
			case "-s:" :
				conversation();
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
