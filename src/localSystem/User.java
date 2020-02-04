package localSystem;

import network.NetworkManager;

import java.net.InetAddress;

import database.DatabaseDriver;

public class User extends Thread{
	
	//Attributes
	public static String localUserName; //name of the user
	public static boolean isAdmin;
	
	/**
	  * class constructor
	 **/
	public User (String name, int id, boolean adminRight) {
		localUserName = name;
		isAdmin = adminRight;
	}

	/**
	  * run function of the thread
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
