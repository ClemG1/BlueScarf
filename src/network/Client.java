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
	  * class constructor
	  * @param : ip to connect to, port to connect to 
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
	
	/**
	 * initialize a conversation between two users
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
	
	/**
	 * send a message to a user
	 * @param the message
	 */
	public static void sendMessage(String toSend) {
		try {
			String messageToSend = "-s:" + User.localUserName + ":" + toSend;
				
			bufferOut.write(messageToSend);
			bufferOut.newLine();
			bufferOut.flush();
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * use to to connect to the network
	 */
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
	
	/**
	 * use to response to a newcomer
	 */
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
	
	/**
	 * use to send an update message to all the other users
	 */
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
	
	/**
	 * use to say that we are now deconnected
	 */
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
	  * run function of the thread
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
