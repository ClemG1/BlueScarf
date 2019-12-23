package network;

import java.net.*;
import java.util.*;

public class NetworkManager {
	
	//Attributes
	private int portServer = 6969;
	private InetAddress ipAddress;
	
	/**
	  * @brief : class constructor
	  * @param : mode (client or server)
	  * @returns : none
	  * @note : TCP protocol used
	 **/
	public NetworkManager() {
		try {
			
			//find the ip address of the computer
			Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces(); //list the interface of the computer
			Boolean found = false; //true when you find a valid ip address
			while((! found) && interfaceList.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) interfaceList.nextElement();
			    Enumeration<InetAddress> addressList = networkInterface.getInetAddresses(); //address list of the interface
			    while ((! found) && addressList.hasMoreElements()) {
			    	InetAddress address = (InetAddress) addressList.nextElement();
			        String addressPart[] = address.getHostAddress().split("\\.");
			        if ((addressPart.length == 4) && (!addressPart[0].equals("127")) && (!addressPart[0].equals("192"))) { //a suitable address has been found
			        	found = true;
			        	this.ipAddress = address;
			        }
			    }
			}
			System.out.println("my ip is : " + this.ipAddress);
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	  * @brief : start a thread server bind to the server port 
	  * @param : none
	  * @returns: none
	 **/
	public void startServer() {
		Server server = new Server(this.portServer);
		server.start();
	}
	
	/**
	  * @brief : start a thread client to a server listening on the server port 
	  * @param : the ip address of the server you want to connect to
	  * @returns: none
	 **/
	public void connectTo(InetAddress ipAddress) {
		Client client = new Client(ipAddress,this.portServer);
		client.start();
	}
	

}
