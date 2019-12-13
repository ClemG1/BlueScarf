package network;

import java.net.InetAddress;
import localSystem.LocalFilesManager;

public class NetworkTreatment extends Thread{
	
	/**
	 * 
	 * This is a typical connection message :
	 * c:id:10:name:MorvanNozahic
	 * 
	 * this is a typical disconnection message :
	 * d:id:10:name:GehinClement
	 * 
	 * this is a typical chat message
	 * m:id:10:name:PelousThomas:mes:Tihs is the message
	 * 
	 */

	//Atributes
	private String type; //use to know what to do as treatment
	private String message; //the real message
	private InetAddress clientAddress; //address of the user who sent the message
	private int clientPort; //port of the user who sent the message
	
	/**
	  * @brief : class constructor
	  * @param : message received from the network, address and port of the user who sent the message 
	  * @returns : none
	  * @note : the type of message are : "c:" for connection, "m:" for a chat message, "f:" for file, "d:" for disconnection
	 **/
	public NetworkTreatment(String message, InetAddress clientAddress, int clientPort) {
		this.type = message.substring(0, 2);
		this.message = message.substring(2);
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}
	
	public void run() {
		switch(this.type) {
		
		case "c:" :
			String messagePart[] = this.message.split(":");
			String name = messagePart[3];
			LocalFilesManager onlineUserNameWritter = new LocalFilesManager("onlineUser.txt", LocalFilesManager.getPath(), name, '-', "w");
			onlineUserNameWritter.start();
			String onlineUserData = "id:" + messagePart[1] + "address:" + this.clientAddress.toString() + "port:" + String.valueOf(this.clientPort);
			LocalFilesManager onlineUserDataWritter = new LocalFilesManager("onlineUserData.txt", LocalFilesManager.getPath(), onlineUserData, '-', "w");
			onlineUserDataWritter.start();
			break;
			
		case "d:" :
			break;
			
		case "m:" :
			break;
		case "f:" :
			break;
			
		default :
			System.out.println("Invalide packet received");
			
		}
	}
	
}
