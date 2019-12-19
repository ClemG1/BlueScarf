package network;

import java.net.*;
import java.util.*;

public class NetworkManager extends Thread{
	
	//Attributes
	private int portServer = 6666;
	private int portClient = 6969;
	private InetAddress ipAddress;
	
	/**
	  * @brief : class constructor
	  * @param : mode (client or server)
	  * @returns : none
	  * @note : TCP protocol used
	 **/
	public NetworkManager(String mode) {
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
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		Server server = new Server(this.portServer);
		server.start();
		Client client = new Client(this.ipAddress, this.portServer);
		client.start();
	}

}
