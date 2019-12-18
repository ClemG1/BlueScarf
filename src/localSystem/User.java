package localSystem;

import network.NetworkManager;
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
		NetworkManager server = new NetworkManager("-s");
		server.start();
		NetworkManager client = new NetworkManager("-c");
		client.start();
	}
	
}
