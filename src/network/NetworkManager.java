package network;

import database.DatabaseDriver;
import localSystem.LocalFilesManager;

import java.net.*;
import java.util.*;

public class NetworkManager {
	
	//Attributes
	public static int portServer = 6969;
	public static InetAddress ipAddress;
	
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
			        if ((addressPart.length == 4) && (!addressPart[0].equals("127")) /*&& (!addressPart[0].equals("192"))*/) { //a suitable address has been found
			        	found = true;
			        	ipAddress = address;
			        }
			    }
			}
			System.out.println("my ip is : " + ipAddress);
			
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
	private void startServer() {
		Server server = new Server();
		server.start();
	}
	
	public void connectToNetwork ( ) {
		try {
			
			//add our ip to contact.txt file
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			contact.write(ipAddress.toString(), '-');
			
			DatabaseDriver database = new DatabaseDriver();
			String ipToConnect = database.getIpToConnect();
			String localIp = ipAddress.toString().substring(1);
			if (ipToConnect == null) { //nobody is connected
				startServer();
				database.updateIpToConnect(localIp);
			}
			else { //someone is connected
				startServer();
				Client client = new Client(InetAddress.getByName(ipToConnect),"-c");
				client.start();
				database.updateIpToConnect(localIp);
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
