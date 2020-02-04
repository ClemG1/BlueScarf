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
	private static BufferedWriter bufferOut;
	public static String speakWith;
	
	/**
	  * @brief : class constructor
	  * @param : ip to connect to, port to connect to 
	  * @returns : none
	 **/
	public Client(InetAddress ipAddress, String messageType) {
		try {
			this.socket = new Socket(ipAddress,NetworkManager.portServer);
			this.messageType = messageType;
			bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
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
			String call = "-m:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			
			bufferOut.write(call);
			bufferOut.newLine();
			bufferOut.flush();
			
		} 
		catch (IOException ioe) {
			System.out.println("Client : " + ioe);
		}
	}
	
	private void startConversation() {
		try {
			System.out.println("conv thread");
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void sendMessage(String toSend) {
		try {
			String messageToSend = "-s:" + User.localUserName + ":" + toSend;
				
			bufferOut.write(messageToSend);
			bufferOut.newLine();
			bufferOut.flush();
			System.out.println("Message sent");
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void sendConnectionMsg () {
		try {
			
			String myContact = "-c:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			bufferOut.write(myContact);
			bufferOut.newLine();
			bufferOut.flush();
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void responseMessage() {
		try {
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			String myContacts = "-u:" + contact.readAllFile();
			bufferOut.write(myContacts);
			bufferOut.newLine();
			bufferOut.flush();

		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void updateUserMessage() {
		try {
			bufferOut.write("-u:" + ServerThread.newUserData);
			bufferOut.newLine();
			bufferOut.flush();

		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private void deconnectionMessage() {
		try {
			String deconnectionMessage = "-d:" + User.localUserName + ":" + NetworkManager.localIpAddress.toString();
			bufferOut.write(deconnectionMessage);
			bufferOut.newLine();
			bufferOut.flush();

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
				startConversation();
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
				startConversation();
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
