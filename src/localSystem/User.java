package localSystem;

import network.NetworkManager;

import java.net.InetAddress;

import database.DatabaseDriver;
import graphic.InterfaceHM;

public class User extends Thread{
	
	//Attributes
	private String name; //name of the user
	private int id; //id of the user, use to find the user on the network
	
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public User (String name, int id) {
		this.name = name;
		this.id = id;
	}

	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run () {
		try {
			/*NetworkManager networkDriver = new NetworkManager();
			networkDriver.connectToNetwork();*/
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
