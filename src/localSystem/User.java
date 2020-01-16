package localSystem;

import network.NetworkManager;

import java.net.InetAddress;

import database.DatabaseDriver;

public class User extends Thread{
	
	//Attributes
	public static String localUserName; //name of the user
	public static boolean isAdmin;
	private int id; //id of the user, use to find the user on the network
	
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public User (String name, int id, boolean adminRight) {
		localUserName = name;
		this.id = id;
		isAdmin = adminRight;
	}

	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run () {
		try {
			NetworkManager networkDriver = new NetworkManager();
			networkDriver.connectToNetwork();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
