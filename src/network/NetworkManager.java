package network;

import database.DatabaseDriver;
import graphic.MainWindow;
import localSystem.*;

import java.net.*;
import java.util.*;

public class NetworkManager {
	
	//Attributes
	public static int portServer = 6969;
	public static InetAddress localIpAddress;
	
	/**
	  * class constructor, TCP protocol used
	  * @param : mode (client or server)
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
			        	localIpAddress = address;
			        }
			    }
			}
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	  * start a thread server bind to the server port 
	 **/
	private void startServer() {
		Server server = new Server();
		server.start();
	}
	
	/**
	 * use to connect to the network
	 */
	public void connectToNetwork ( ) {
		try {
			
			//add our user name to onlineUsers file and refresh the main window
			LocalFilesManager onlineUsers = new LocalFilesManager("onlineUsers.txt", LocalFilesManager.getPath());
			onlineUsers.write(User.localUserName, "|");
			
			//add our ip to contact.txt file matching our user name
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			contact.write(User.localUserName + ":" + localIpAddress.toString(), "|"); 
			
			DatabaseDriver database = new DatabaseDriver();
			String ipToConnect = database.getIpToConnect();
			String localIp = localIpAddress.toString().substring(1);
			if (ipToConnect == null) { //nobody is connected
				startServer();
				database.updateIpToConnect(localIp);
			}
			else { //someone is connected
				startServer();
				Client client = new Client(InetAddress.getByName(ipToConnect),"-c:");
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
