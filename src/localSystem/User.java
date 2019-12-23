package localSystem;

import network.NetworkManager;

import java.net.InetAddress;

import database.DatabaseDriver;
import graphic.InterfaceHM;

public class User extends Thread{
	
	//Attributes
	private String firstName; //first name of the user
	private String lastName; //last name of the user
	private int id; //id of the user, use to find the user on the network
	
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public User (String firstName, String lastName, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	public void run () {
		try {
			NetworkManager networkManager = new NetworkManager();
			//networkManager.startServer();
			networkManager.connectTo(InetAddress.getByName("127.0.0.1"));;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
