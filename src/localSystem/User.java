package localSystem;

import network.NetworkManager;

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
		LocalFilesManager profilFile = new LocalFilesManager(".profil",LocalFilesManager.getPath(),this.firstName + " " + this.lastName,'-',"w");
		profilFile.start();
	}
	
}
